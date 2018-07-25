package io.github.jhipster.online.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.jhipster.online.domain.*;
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

    private final LanguageService languageService;

    private final GeneratorIdentityService generatorIdentityService;

    private final SubGenEventService subGenEventService;

    private final EntityStatsService entityStatsService;

    public StatisticsService(YoRCService yoRCService,
                             LanguageService languageService,
                             GeneratorIdentityService generatorIdentityService,
                             SubGenEventService subGenEventService,
                             EntityStatsService entityStatsService) {
        this.yoRCService = yoRCService;
        this.languageService = languageService;
        this.generatorIdentityService = generatorIdentityService;
        this.subGenEventService = subGenEventService;
        this.entityStatsService = entityStatsService;
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
        DateTime epoch = new DateTime(1970, 1, 1, 0, 0);
        int year = now.getYear();
        int month = Months.monthsBetween(epoch.toInstant(), now.toInstant()).getMonths();
        int week = Weeks.weeksBetween(epoch.toInstant(), now.toInstant()).getWeeks();
        int day = Days.daysBetween(epoch.toInstant(), now.toInstant()).getDays();
        int hour = Hours.hoursBetween(epoch.toInstant(), now.toInstant()).getHours();

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
            .year(year)
            .month(month)
            .week(week)
            .day(day)
            .hour(hour)
            .creationDate(Instant.ofEpochMilli(now.getMillis()));
        yoRCService.save(yorc);
        yorc.getSelectedLanguages().forEach(languageService::save);
    }

    public void addSubGenEvent(SubGenEvent subGenEvent, String generatorId)  {
        subGenEvent.date(Instant.now()).owner(generatorIdentityService.findOrCreateOneByGuid(generatorId).getOwner());
        subGenEventService.save(subGenEvent);
    }

    public void addEntityStats(EntityStats entityStats, String generatorId)  {
        entityStats.date(Instant.now()).owner(generatorIdentityService.findOrCreateOneByGuid(generatorId).getOwner());
        entityStatsService.save(entityStats);
    }
}
