package run.halo.app.infra.utils.JsonSerializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import run.halo.app.theme.finders.vo.TagVo;
import run.halo.app.core.extension.content.Tag;
import run.halo.app.extension.MetadataOperator;
import org.apache.commons.lang3.ObjectUtils;

public class TagVoSerDe {
    
    public static class Serializer extends StdSerializer<TagVo> {
        public Serializer() {
            super(TagVo.class);
        }

        @Override
        public void serialize(TagVo value, JsonGenerator gen, SerializerProvider provider) throws IOException {
            gen.writeStartObject();
            if (value.getMetadata() != null) {
                gen.writeObjectField("metadata", value.getMetadata());
            }
            if (value.getSpec() != null) {
                gen.writeObjectField("spec", value.getSpec());
            }
            if (value.getStatus() != null) {
                gen.writeObjectField("status", value.getStatus());
            }
            gen.writeNumberField("postCount", value.getPostCount());
            gen.writeEndObject();
        }
    }
    
    public static class Deserializer extends StdDeserializer<TagVo> {
        public Deserializer() {
            super(TagVo.class);
        }

        @Override
        public TagVo deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            ObjectMapper mapper = (ObjectMapper) p.getCodec();
            JsonNode node = mapper.readTree(p);
            
            MetadataOperator metadata = node.has("metadata") ? 
                mapper.treeToValue(node.get("metadata"), MetadataOperator.class) : null;
                
            Tag.TagSpec spec = node.has("spec") ? 
                mapper.treeToValue(node.get("spec"), Tag.TagSpec.class) : null;
                
            Tag.TagStatus status = node.has("status") ? 
                mapper.treeToValue(node.get("status"), Tag.TagStatus.class) : null;
                
            int postCount = node.has("postCount") ? node.get("postCount").asInt() : 0;

            return TagVo.builder()
                .metadata(metadata)
                .spec(spec)
                .status(status)
                .postCount(postCount)
                .build();
        }
    }
}