package io.github.jhipster.online.service;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.github.jhipster.online.domain.YoRC;
import io.github.jhipster.online.domain.deserializer.YoRCDeserializer;
import io.github.jhipster.online.repository.YoRCRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
/**
 * Service Implementation for managing YoRC.
 */
@Service
@Transactional
@JsonDeserialize(using = YoRCDeserializer.class)
public class YoRCService {

    private final Logger log = LoggerFactory.getLogger(YoRCService.class);

    private final YoRCRepository yoRCRepository;

    private final OwnerIdentityService ownerIdentityService;

    private final UserService userService;

    public YoRCService(YoRCRepository yoRCRepository, OwnerIdentityService ownerIdentityService, UserService userService) {
        this.yoRCRepository = yoRCRepository;
        this.ownerIdentityService = ownerIdentityService;
        this.userService = userService;
        // this.addFakeData();
    }

    /**
     * Save a yoRC.
     *
     * @param yoRC the entity to save
     * @return the persisted entity
     */
    public YoRC save(YoRC yoRC) {
        log.debug("Request to save YoRC : {}", yoRC);        return yoRCRepository.save(yoRC);
    }

    /**
     * Get all the yoRCS.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<YoRC> findAll() {
        log.debug("Request to get all YoRCS");
        return yoRCRepository.findAll();
    }


    /**
     * Get one yoRC by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<YoRC> findOne(Long id) {
        log.debug("Request to get YoRC : {}", id);
        return yoRCRepository.findById(id);
    }

    /**
     * Delete the yoRC by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete YoRC : {}", id);
        yoRCRepository.deleteById(id);
    }

    public void addFakeData() {
        Instant now = Instant.now();
        Instant start = now.minus(Duration.ofDays(365));

        String jhipsterVersion = "5.0.2";
        String gitProvider = "";
        String nodeVersion = "";
        String os = "";
        String arch = "";
        String cpu = "";
        String cores = "";
        String memory = "16";
        String userLanguage = "";

        String applicationType = "";
        String authenticationType = "";
        String serverPort = "8080";
        String cacheProvider = "";
        boolean enableHibernateCache;
        boolean webSocket;
        String databaseType = "";
        String devDatabaseType = "";
        String prodDatabaseType = "";
        boolean searchEngine;
        boolean messageBroker;
        boolean serviceDiscoveryType;
        String buildTool = "";
        boolean enableSwaggerCodegen;
        String clientFramework = "";
        boolean useSass;
        String clientPackageManager = "";
        boolean enableTranslation;
        String nativeLanguage = "";
        boolean hasProtractor;
        boolean hasGatling;
        boolean hasCucumber;

        for(int i = 0; i < 3000; i++) {
            YoRC yorc = new YoRC();

            Duration between = Duration.between(start, now);
            Instant createdDate = now.minus(Duration.ofSeconds((int)(Math.random() * between.getSeconds())));

            int randomUserLang = (int)(Math.random() * 3);
            if (randomUserLang == 0) {
                userLanguage = "en-gb";
            } else if (randomUserLang == 1) {
                userLanguage = "fr-fr";
            } else if (randomUserLang == 2) {
                userLanguage = "pt-pt";
            }

            int randomVersionJhip = (int)(Math.random() * 3);
            if (randomVersionJhip == 0) {
                jhipsterVersion = "beta-5.0.2";
            } else if (randomVersionJhip == 1) {
                jhipsterVersion = "beta-5.0.1";
            } else if (randomVersionJhip == 2) {
                jhipsterVersion = "4.14.4";
            }

            int randomArch = (int) (Math.random() * 2);
            arch = randomArch == 0 ? "x64" : "x86";

            int randomApplicationType = (int) (Math.random() * 3);
            if (randomApplicationType == 0) {
                applicationType = "monolith";
            } else if (randomApplicationType == 1) {
                applicationType = "microservice";
            } else if (randomApplicationType == 2) {
                applicationType = "gateway";
            } else if (randomApplicationType == 3) {
                applicationType = "uaa";
            }

            int randomAuthenticationType = (int) (Math.random() * 4);
            if (randomAuthenticationType == 0) {
                authenticationType = "jwt";
            } else if (randomAuthenticationType == 1) {
                authenticationType = "oauth2";
            } else if (randomAuthenticationType == 2) {
                authenticationType = "session";
            } else if (randomAuthenticationType == 3) {
                authenticationType = "uaa";
            }

            int randomCacheProvider = (int) (Math.random() * 4);
            if (randomCacheProvider == 0) {
                cacheProvider = "ehcache";
            } else if (randomCacheProvider == 1) {
                cacheProvider = "hazelcast";
            } else if (randomCacheProvider == 2) {
                cacheProvider = "infinispan";
            } else if (randomCacheProvider == 3) {
                cacheProvider = "no";
            }

            int randomEnableHibernateCache = (int) (Math.random() * 2);
            enableHibernateCache = randomEnableHibernateCache == 0;

            int randomWebSocket = (int) (Math.random() * 2);
            webSocket = randomWebSocket == 0;

            int randomDatabaseType = (int)(Math.random() * 5);
            if (randomDatabaseType == 0) {
                databaseType = "no";
            } else if (randomDatabaseType == 1) {
                databaseType = "sql";
            } else if (randomDatabaseType == 2) {
                databaseType = "mongodb";
            } else if (randomDatabaseType == 3) {
                databaseType = "cassandra";
            } else if (randomDatabaseType == 4) {
                databaseType = "couchbase";
            }

            int randomDevDatabaseType = (int)(Math.random() * 6);
            if (randomDevDatabaseType == 0) {
                devDatabaseType = "h2Disk";
            } else if (randomDevDatabaseType == 1) {
                devDatabaseType = "h2Memory";
            } else if (randomDevDatabaseType == 2) {
                devDatabaseType = "mysql";
            } else if (randomDevDatabaseType == 3) {
                devDatabaseType = "mariadb";
            } else if (randomDevDatabaseType == 4) {
                devDatabaseType = "postgresql";
            } else if (randomDevDatabaseType == 5) {
                devDatabaseType = "oracle";
            }

            int randomProdDatabaseType = (int)(Math.random() * 4);
            if (randomProdDatabaseType == 0) {
                prodDatabaseType = "mysql";
            } else if (randomProdDatabaseType == 1) {
                prodDatabaseType = "mariadb";
            } else if (randomProdDatabaseType == 2) {
                prodDatabaseType = "postgresql";
            } else if (randomProdDatabaseType == 3) {
                prodDatabaseType = "oracle";
            }

            int randomSearchEngine = (int) (Math.random() * 2);
            searchEngine = randomSearchEngine == 0;

            int randomMessageBroker = (int) (Math.random() * 2);
            messageBroker = randomMessageBroker == 0;

            int randomServiceDiscoveryType = (int) (Math.random() * 2);
            serviceDiscoveryType = randomServiceDiscoveryType == 0;

            int randomBuildTool = (int) (Math.random() * 2);
            buildTool = randomBuildTool == 0 ? "maven" : "gradle";

            int randomEnableSwaggerCodegen = (int) (Math.random() * 2);
            enableSwaggerCodegen = randomEnableSwaggerCodegen == 0;

            int randomFront = (int) (Math.random() * 2);
            clientFramework = randomFront == 0 ? "react" : "angularX";

            int randomClientPackageManager = (int) (Math.random() * 2);
            clientPackageManager = randomClientPackageManager == 0 ? "yarn" : "npm";

            int randomUseSass = (int) (Math.random() * 2);
            useSass = randomUseSass == 0;

            int randomEnableTranslation = (int) (Math.random() * 2);
            enableTranslation = randomEnableTranslation == 0;

            int randomHasProtractor = (int) (Math.random() * 2);
            hasProtractor = randomHasProtractor == 0;

            int randomHasCum = (int) (Math.random() * 2);
            hasCucumber = randomHasCum == 0;

            int randomHasGatling = (int) (Math.random() * 2);
            hasGatling = randomHasGatling == 0;

            String[] languages = { "en", "pt-pt", "pt-br", "fr", "ar", "or", "ir", "ur", "erre", "lol", "okk", "min", "max", "upmc", "p7", "21milton", "pg" };
            int randomLanguage = (int) (Math.random() * languages.length);
            nativeLanguage = languages[randomLanguage];

            yorc.serverPort(serverPort)
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
                .creationDate(createdDate)
                .arch(arch)
                .gitProvider(gitProvider)
                .nodeVersion(nodeVersion)
                .os(os)
                .arch(arch)
                .cpu(cpu)
                .cores(cores)
                .memory(memory)
                .userLanguage(userLanguage)
                .jhipsterVersion(jhipsterVersion);

            yorc.setOwner(ownerIdentityService.findOrCreateUser(userService.getUserWithAuthoritiesByLogin("admin").get()));
            yoRCRepository.save(yorc);
        }
    }
}
