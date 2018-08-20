package io.github.jhipster.online.domain.deserializer;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ArrayNode;

import io.github.jhipster.online.domain.YoRC;

public class YoRCDeserializer extends StdDeserializer<YoRC> {

    public YoRCDeserializer() {
        super(YoRC.class);
    }

    @Override
    public YoRC deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        YoRC result = new YoRC();
        JsonNode node = jp.getCodec().readTree(jp);

        String serverPort = node.get("serverPort").asText();
        String authenticationType = node.get("authenticationType").asText();
        String cacheProvider = node.get("cacheProvider").asText();
        boolean enableHibernateCache = getDefaultIfNull(node.get("enableHibernateCache"), false);
        boolean webSocket = node.get("websocket").asBoolean();
        String databaseType = node.get("databaseType").asText();
        String devDatabaseType = node.get("devDatabaseType").asText();
        String prodDatabaseType = node.get("prodDatabaseType").asText();
        boolean searchEngine = node.get("searchEngine").asBoolean();
        boolean messageBroker = node.get("messageBroker").asBoolean();
        boolean serviceDiscoveryType = node.get("serviceDiscoveryType").asBoolean();
        String buildTool = node.get("buildTool").asText();
        boolean enableSwaggerCodegen = node.get("enableSwaggerCodegen").asBoolean();
        String clientFramework = getDefaultIfNull(node.get("clientFramework"), "none");
        boolean useSass = getDefaultIfNull(node.get("useSass"), false);
        String clientPackageManager = node.get("clientPackageManager").asText();
        String applicationType = node.get("applicationType").asText();
        boolean enableTranslation = node.get("enableTranslation").asBoolean();
        String nativeLanguage = getDefaultIfNull(node.get("nativeLanguage"), "");
        boolean hasProtractor = false;
        boolean hasGatling = false;
        boolean hasCucumber = false;
        ArrayNode testFrameworks = (ArrayNode)node.get("testFrameworks");
        for (JsonNode testFramework: testFrameworks) {
            switch(testFramework.asText().toLowerCase()) {
                case "protractor":
                    hasProtractor = true;
                    break;
                case "gatling":
                    hasGatling = true;
                    break;
                case "cucumber":
                    hasCucumber = true;
                    break;
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
