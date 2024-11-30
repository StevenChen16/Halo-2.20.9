package run.halo.app.infra.utils.JsonSerializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import run.halo.app.theme.finders.vo.ContributorVo;

public class ContributorVoSerDe {
    
    public static class Serializer extends StdSerializer<ContributorVo> {
        public Serializer() {
            super(ContributorVo.class);
        }

        @Override
        public void serialize(ContributorVo value, JsonGenerator gen, SerializerProvider provider) throws IOException {
            gen.writeStartObject();
            gen.writeStringField("name", value.getName());
            gen.writeStringField("displayName", value.getDisplayName());
            gen.writeStringField("avatar", value.getAvatar());
            gen.writeStringField("bio", value.getBio());
            gen.writeStringField("permalink", value.getPermalink());
            gen.writeEndObject();
        }
    }
    
    public static class Deserializer extends StdDeserializer<ContributorVo> {
        public Deserializer() {
            super(ContributorVo.class);
        }

        @Override
        public ContributorVo deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            JsonNode node = p.getCodec().readTree(p);
            
            return ContributorVo.builder()
                .name(getTextValue(node, "name"))
                .displayName(getTextValue(node, "displayName"))
                .avatar(getTextValue(node, "avatar"))
                .bio(getTextValue(node, "bio"))
                .permalink(getTextValue(node, "permalink"))
                .build();
        }
        
        private String getTextValue(JsonNode node, String field) {
            return node.has(field) ? node.get(field).asText() : null;
        }
    }
}