package io.github.jhipster.online.domain.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ArrayNode;
import io.github.jhipster.online.domain.YoRC;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class YoRCDeserializer extends StdDeserializer<YoRC> {

    public YoRCDeserializer() {
        super(YoRC.class);
    }

    @Override
    public YoRC deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        YoRC result = new YoRC();
        JsonNode node = jp.getCodec().readTree(jp);

        String serverPort = getDefaultIfNull(node.get("serverPort"), "8080");
        String authenticationType = node.get("authenticationType").asText();
        String cacheProvider = getDefaultIfNull(node.get("cacheProvider"), "no");
        boolean enableHibernateCache = getDefaultIfNull(node.get("enableHibernateCache"), false);
        boolean webSocket = node.get("websocket").asBoolean();
        String databaseType = node.get("databaseType").asText();
        String devDatabaseType = node.get("devDatabaseType").asText();
        String prodDatabaseType = node.get("prodDatabaseType").asText();
        boolean searchEngine = getDefaultIfNull(node.get("searchEngine"), false);
        boolean messageBroker = getDefaultIfNull(node.get("messageBroker"), false);
        boolean serviceDiscoveryType = getDefaultIfNull(node.get("serviceDiscoveryType"), false);
        String buildTool = node.get("buildTool").asText();
        boolean enableSwaggerCodegen = getDefaultIfNull(node.get("enableSwaggerCodegen"), false);
        String clientFramework = getDefaultIfNull(node.get("clientFramework"), "none");
        boolean useSass = getDefaultIfNull(node.get("useSass"), false);
        String clientPackageManager = node.get("clientPackageManager").asText();
        String applicationType = node.get("applicationType").asText();
        boolean enableTranslation = node.get("enableTranslation").asBoolean();
        String nativeLanguage = getDefaultIfNull(node.get("nativeLanguage"), "");
        boolean hasProtractor = false;
        boolean hasGatling = false;
        boolean hasCucumber = false;

        if (node.get("testFrameworks") != null) {
            ArrayNode testFrameworks = (ArrayNode) node.get("testFrameworks");
            for (JsonNode testFramework : testFrameworks) {
                switch (testFramework.asText().toLowerCase()) {
                    case "protractor":
                        hasProtractor = true;
                        break;
                    case "gatling":
                        hasGatling = true;
                        break;
                    case "cucumber":
                        hasCucumber = true;
                        break;
                    default:
                        // No action necessary as all possible cases are handled above
                }
            }
        }

        Set<String> languages =  getLanguagesFromArrayNode((ArrayNode)node.get("languages"));

        return result
            .serverPort(serverPort)
            .authenticationType(authenticationType)
            .cacheProvider(cacheProvider)
            .enableHibernateCache(enableHibernateCache)
            .websocket(webSocket)
            .databaseType(databaseType)
            .devDatabaseType(devDatabaseType)
            .prodDatabaseType(prodDatabaseType)
            .searchEngine(searchEngine)
            .messageBroker(messageBroker)
            .serviceDiscoveryType(serviceDiscoveryType)
            .buildTool(buildTool)
            .enableSwaggerCodegen(enableSwaggerCodegen)
            .clientFramework(clientFramework)
            .useSass(useSass)
            .clientPackageManager(clientPackageManager)
            .applicationType(applicationType)
            .enableTranslation(enableTranslation)
            .nativeLanguage(nativeLanguage)
            .hasProtractor(hasProtractor)
            .hasGatling(hasGatling)
            .hasCucumber(hasCucumber)
            .selectedLanguages(languages);
    }

    private String getDefaultIfNull(JsonNode node, String defaultValue) {
        return node == null ? defaultValue : node.asText();
    }

    private boolean getDefaultIfNull(JsonNode node, boolean defaultValue) {
        return node == null ? defaultValue : node.asBoolean();
    }

    private Set<String> getLanguagesFromArrayNode(ArrayNode languagesNode) {
        Set<String> result = new HashSet<>();
        if (languagesNode != null) {
            languagesNode.forEach(e -> result.add(e.asText()));
        }
        return result;
    }
}
