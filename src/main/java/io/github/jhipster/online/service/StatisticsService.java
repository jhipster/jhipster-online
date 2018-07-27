package io.github.jhipster.online.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.jhipster.online.domain.*;
import io.github.jhipster.online.service.util.StatisticsUtil;
import org.joda.time.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Instant;

@Service
public class StatisticsService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    private final YoRCService yoRCService;


    private final GeneratorIdentityService generatorIdentityService;

    private final SubGenEventService subGenEventService;

    private final EntityStatsService entityStatsService;

    private final OwnerIdentityService ownerIdentityService;

    public StatisticsService(YoRCService yoRCService,
                             GeneratorIdentityService generatorIdentityService,
                             SubGenEventService subGenEventService,
                             EntityStatsService entityStatsService,
                             OwnerIdentityService ownerIdentityService) {
        this.yoRCService = yoRCService;
        this.generatorIdentityService = generatorIdentityService;
        this.subGenEventService = subGenEventService;
        this.entityStatsService = entityStatsService;
        this.ownerIdentityService = ownerIdentityService;
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

        DateTime now = DateTime.now();

        GeneratorIdentity generatorIdentity = generatorIdentityService.findOrCreateOneByGuid(generatorGuid);
        generatorIdentityService.save(generatorIdentity.host(host));

        OwnerIdentity owner = generatorIdentity.getOwner();
        YoRC yorc = mapper.treeToValue(jsonNodeGeneratorJHipster, YoRC.class);

        StatisticsUtil.setAbsoluteDate(yorc, now);

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
            .creationDate(Instant.ofEpochMilli(now.getMillis()));
        yoRCService.save(yorc);
    }

    public void addSubGenEvent(SubGenEvent subGenEvent, String generatorId)  {
        DateTime now = DateTime.now();
        StatisticsUtil.setAbsoluteDate(subGenEvent, now);

        subGenEvent
            .date(Instant.ofEpochMilli(now.getMillis()))
            .owner(generatorIdentityService.findOrCreateOneByGuid(generatorId).getOwner());
        subGenEventService.save(subGenEvent);
    }

    public void addEntityStats(EntityStats entityStats, String generatorId)  {
        DateTime now = DateTime.now();
        StatisticsUtil.setAbsoluteDate(entityStats, now);

        entityStats
            .date(Instant.now())
            .owner(generatorIdentityService.findOrCreateOneByGuid(generatorId).getOwner());
        entityStatsService.save(entityStats);
    }

    public void deleteStatistics(User owner) {
        OwnerIdentity ownerIdentity = ownerIdentityService.findOrCreateUser(owner);
        log.debug("Statistics data deletion requested for : {} ({}) ", owner.getLogin(), ownerIdentity.getId());

        log.debug("Deleting yos");
        yoRCService.deleteByOwnerIdentity(ownerIdentity);

        log.debug("Deleting sub generator events");
        subGenEventService.deleteByOwner(ownerIdentity);

        log.debug("Deleting entity statistics");
        entityStatsService.deleteByOwner(ownerIdentity);

    }
}
