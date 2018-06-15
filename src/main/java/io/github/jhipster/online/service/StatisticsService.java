package io.github.jhipster.online.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.jhipster.online.domain.*;
import io.github.jhipster.online.repository.YoRCRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.List;

@Service
public class StatisticsService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    private final YoRCRepository yoRCRepository;

    private final YoRCService yoRCService;
    private LanguageService languageService;
    private GeneratorIdentityService generatorIdentityService;
    private SubGenEventService subGenEventService;
    private EntityStatsService entityStatsService;

    public StatisticsService(YoRCService yoRCService,
                             YoRCRepository yoRCRepository,
                             LanguageService languageService,
                             GeneratorIdentityService generatorIdentityService,
                             SubGenEventService subGenEventService,
                             EntityStatsService entityStatsService) {
        this.yoRCService = yoRCService;
        this.yoRCRepository = yoRCRepository;
        this.languageService = languageService;
        this.generatorIdentityService = generatorIdentityService;
        this.subGenEventService = subGenEventService;
        this.entityStatsService = entityStatsService;
    }

    public long getYoRCCount() {
        return yoRCRepository.count();
    }

    public List<Long> getYoRCCountForEachUser() {
        return yoRCRepository.findYoRCCountForEachUser();
    }

    public void addEntry(String entry, String host) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNodeRoot = mapper.readTree(entry);
        JsonNode jsonNodeGeneratorJHipster = jsonNodeRoot.get("generator-jhipster");
        String generatorGuid = jsonNodeRoot.get("generator-id").asText();
        String generatorVersion = jsonNodeRoot.get("generator-version").asText();
        String gitProvider = jsonNodeRoot.get("git-provider").asText();
        String nodeVersion = jsonNodeRoot.get("node-version").asText();
        String os = jsonNodeRoot.get("os").asText();
        String arch = jsonNodeRoot.get("arch").asText();
        String cpu = jsonNodeRoot.get("cpu").asText();
        String cores = jsonNodeRoot.get("cores").asText();
        String memory = jsonNodeRoot.get("memory").asText();
        String userLanguage = jsonNodeRoot.get("user-language").asText();
        log.info("Adding an entry for generator {}.", generatorGuid);

        GeneratorIdentity generatorIdentity = generatorIdentityService.findOrCreateOneByGuid(generatorGuid);
        generatorIdentityService.save(generatorIdentity.host(host));
        OwnerIdentity owner = generatorIdentity.getOwner();

        YoRC yorc = mapper.treeToValue(jsonNodeGeneratorJHipster, YoRC.class);
        yorc.jhipsterVersion(generatorVersion)
            .gitProvider(gitProvider)
            .nodeVersion(nodeVersion)
            .os(os)
            .arch(arch)
            .cpu(cpu)
            .cores(cores)
            .memory(memory)
            .userLanguage(userLanguage)
            .owner(owner)
            .creationDate(ZonedDateTime.now());
        yoRCService.save(yorc);
        yorc.getSelectedLanguages().forEach(languageService::save);
    }

    public void addSubGenEvent(SubGenEvent subGenEvent, String generatorId)  {
        subGenEvent.date(ZonedDateTime.now()).owner(generatorIdentityService.findOrCreateOneByGuid(generatorId).getOwner());
        subGenEventService.save(subGenEvent);
    }

    public void addEntityStats(EntityStats entityStats, String generatorId)  {
        entityStats.date(ZonedDateTime.now()).owner(generatorIdentityService.findOrCreateOneByGuid(generatorId).getOwner());
        entityStatsService.save(entityStats);
    }
}
