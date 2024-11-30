package run.halo.app.theme.router.factories;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static run.halo.app.theme.router.PageUrlUtils.totalPage;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.i18n.LocaleContextResolver;
import reactor.core.publisher.Mono;
import run.halo.app.infra.SystemConfigurableEnvironmentFetcher;
import run.halo.app.infra.SystemSetting;
import run.halo.app.theme.DefaultTemplateEnum;
import run.halo.app.theme.finders.PostFinder;
import run.halo.app.theme.finders.vo.ListedPostVo;
import run.halo.app.theme.router.ModelConst;
import run.halo.app.theme.router.PageUrlUtils;
import run.halo.app.theme.router.TitleVisibilityIdentifyCalculator;
import run.halo.app.theme.router.UrlContextListResult;
import jakarta.annotation.PostConstruct;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
@AllArgsConstructor
public class IndexRouteFactory implements RouteFactory {
    private static final Logger log = LoggerFactory.getLogger(IndexRouteFactory.class);

    private final PostFinder postFinder;
    private final SystemConfigurableEnvironmentFetcher environmentFetcher;
    private final TitleVisibilityIdentifyCalculator titleVisibilityIdentifyCalculator;
    private final LocaleContextResolver localeContextResolver;
    private final RedisTemplate<String, Object> redisTemplate;
    private final RedisMessageListenerContainer listenerContainer;
    
    private static final String POST_UPDATE_CHANNEL = "halo:post:update";
    private static final String INDEX_CACHE_PREFIX = "halo:index:posts:";

    @PostConstruct
    public void init() {
        MessageListenerAdapter messageListener = new MessageListenerAdapter(new PostUpdateMessageListener());
        messageListener.setSerializer(redisTemplate.getValueSerializer());
        listenerContainer.addMessageListener(messageListener, new ChannelTopic(POST_UPDATE_CHANNEL));
        log.info("Initialized Redis message listener for post updates");
    }

    private class PostUpdateMessageListener {
        public void handleMessage(String message) {
            log.info("Received post update message: {}", message);
            clearPageCache();
        }
    }

    private void clearPageCache() {
        try {
            Set<String> keys = redisTemplate.keys(INDEX_CACHE_PREFIX + "*");
            if (keys != null && !keys.isEmpty()) {
                redisTemplate.delete(keys);
                log.info("Cleared index page cache, keys count: {}", keys.size());
            }
        } catch (Exception e) {
            log.error("Failed to clear index page cache", e);
        }
    }

    @Override
    public RouterFunction<ServerResponse> create(String pattern) {
        return RouterFunctions
            .route(GET("/").or(GET("/page/{page}")
                .or(GET("/index")).or(GET("/index/page/{page}"))
                .and(accept(MediaType.TEXT_HTML))), handlerFunction());
    }

    HandlerFunction<ServerResponse> handlerFunction() {
        return request -> ServerResponse.ok()
            .render(DefaultTemplateEnum.INDEX.getValue(),
                Map.of("posts", postList(request),
                    ModelConst.TEMPLATE_ID, DefaultTemplateEnum.INDEX.getValue()));
    }

    private Mono<UrlContextListResult<ListedPostVo>> postList(ServerRequest request) {
        String path = request.path();
        int page = pageNumInPathVariable(request);
        String cacheKey = INDEX_CACHE_PREFIX + page;

        Object cachedResult = redisTemplate.opsForValue().get(cacheKey);
        if (cachedResult != null) {
            return Mono.just((ListResult<ListedPostVo>) cachedResult)
                .map(list -> processListResult(list, request, path));
        }

        return configuredPageSize(environmentFetcher, SystemSetting.Post::getPostPageSize)
            .flatMap(pageSize -> postFinder.list(page, pageSize))
            .doOnNext(result -> {
                redisTemplate.opsForValue().set(cacheKey, result, 30, TimeUnit.MINUTES);
                log.debug("Cached post list for page {} with key {}", page, cacheKey);
            })
            .map(list -> processListResult(list, request, path));
    }

    private UrlContextListResult<ListedPostVo> processListResult(
            ListResult<ListedPostVo> list, 
            ServerRequest request,
            String path) {
        list.getItems().forEach(listedPostVo -> 
            listedPostVo.getSpec().setTitle(
                titleVisibilityIdentifyCalculator.calculateTitle(
                    listedPostVo.getSpec().getTitle(),
                    listedPostVo.getSpec().getVisible(),
                    localeContextResolver.resolveLocaleContext(request.exchange())
                        .getLocale()
                )
            )
        );

        return new UrlContextListResult.Builder<ListedPostVo>()
            .listResult(list)
            .nextUrl(PageUrlUtils.nextPageUrl(path, totalPage(list)))
            .prevUrl(PageUrlUtils.prevPageUrl(path))
            .build();
    }
}