package io.github.jhipster.online.domain.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ArrayNode;
import io.github.jhipster.online.domain.Language;
import io.github.jhipster.online.domain.YoRC;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class YoRCDeserializer extends StdDeserializer<YoRC> {

    public YoRCDeserializer() {
        super(YoRC.class);
    }

    @Override
    public YoRC deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        YoRC result = new YoRC();
        JsonNode node = jp.getCodec().readTree(jp);

        // TODO String jhipsterVersion = node.get("jhipsterVersion").asText();
        String serverPort = node.get("serverPort").asText();
        String authenticationType = node.get("authenticationType").asText();
        String cacheProvider = node.get("cacheProvider").asText();
        boolean enableHibernateCache = node.get("enableHibernateCache").asBoolean();
        boolean webSocket = node.get("websocket").asBoolean();
        String databaseType = node.get("databaseType").asText();
        String devDatabaseType = node.get("devDatabaseType").asText();
        String prodDatabaseType = node.get("prodDatabaseType").asText();
        boolean searchEngine = node.get("searchEngine").asBoolean();
        boolean messageBroker = node.get("messageBroker").asBoolean();
        boolean serviceDiscoveryType = node.get("serviceDiscoveryType").asBoolean();
        String buildTool = node.get("buildTool").asText();
        boolean enableSwaggerCodegen = node.get("enableSwaggerCodegen").asBoolean();
        String clientFramework = node.get("clientFramework").asText();
        boolean useSass = node.get("useSass").asBoolean();
        String clientPackageManager = node.get("clientPackageManager").asText();
        String applicationType = node.get("applicationType").asText();
        boolean enableTranslation = node.get("enableTranslation").asBoolean();
        String nativeLanguage = node.get("nativeLanguage").asText();
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

        Set<Language> languages =  getLanguagesFromArrayNode(result, (ArrayNode)node.get("languages"));

        return result
            //.jhipsterVersion(jhipsterVersion)
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


    private Set<Language> getLanguagesFromArrayNode(YoRC yorc, ArrayNode languagesNode) {
        Set<Language> result = new HashSet<>();
        languagesNode.forEach(e -> result.add(new Language().isoCode(e.asText()).yoRC(yorc)));

        return result;
    }
}
