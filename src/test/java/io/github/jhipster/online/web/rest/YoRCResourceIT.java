package io.github.jhipster.online.web.rest;

import io.github.jhipster.online.JhonlineApp;
import io.github.jhipster.online.domain.YoRC;
import io.github.jhipster.online.repository.YoRCRepository;
import io.github.jhipster.online.service.YoRCService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link YoRCResource} REST controller.
 */
@SpringBootTest(classes = JhonlineApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class YoRCResourceIT {

    private static final String DEFAULT_JHIPSTER_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_JHIPSTER_VERSION = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_GIT_PROVIDER = "AAAAAAAAAA";
    private static final String UPDATED_GIT_PROVIDER = "BBBBBBBBBB";

    private static final String DEFAULT_NODE_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_NODE_VERSION = "BBBBBBBBBB";

    private static final String DEFAULT_OS = "AAAAAAAAAA";
    private static final String UPDATED_OS = "BBBBBBBBBB";

    private static final String DEFAULT_ARCH = "AAAAAAAAAA";
    private static final String UPDATED_ARCH = "BBBBBBBBBB";

    private static final String DEFAULT_CPU = "AAAAAAAAAA";
    private static final String UPDATED_CPU = "BBBBBBBBBB";

    private static final String DEFAULT_CORES = "AAAAAAAAAA";
    private static final String UPDATED_CORES = "BBBBBBBBBB";

    private static final String DEFAULT_MEMORY = "AAAAAAAAAA";
    private static final String UPDATED_MEMORY = "BBBBBBBBBB";

    private static final String DEFAULT_USER_LANGUAGE = "AAAAAAAAAA";
    private static final String UPDATED_USER_LANGUAGE = "BBBBBBBBBB";

    private static final Integer DEFAULT_YEAR = 1;
    private static final Integer UPDATED_YEAR = 2;

    private static final Integer DEFAULT_MONTH = 1;
    private static final Integer UPDATED_MONTH = 2;

    private static final Integer DEFAULT_WEEK = 1;
    private static final Integer UPDATED_WEEK = 2;

    private static final Integer DEFAULT_DAY = 1;
    private static final Integer UPDATED_DAY = 2;

    private static final Integer DEFAULT_HOUR = 1;
    private static final Integer UPDATED_HOUR = 2;

    private static final String DEFAULT_SERVER_PORT = "AAAAAAAAAA";
    private static final String UPDATED_SERVER_PORT = "BBBBBBBBBB";

    private static final String DEFAULT_AUTHENTICATION_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_AUTHENTICATION_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_CACHE_PROVIDER = "AAAAAAAAAA";
    private static final String UPDATED_CACHE_PROVIDER = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ENABLE_HIBERNATE_CACHE = false;
    private static final Boolean UPDATED_ENABLE_HIBERNATE_CACHE = true;

    private static final Boolean DEFAULT_WEBSOCKET = false;
    private static final Boolean UPDATED_WEBSOCKET = true;

    private static final String DEFAULT_DATABASE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_DATABASE_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_DEV_DATABASE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_DEV_DATABASE_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_PROD_DATABASE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_PROD_DATABASE_TYPE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_SEARCH_ENGINE = false;
    private static final Boolean UPDATED_SEARCH_ENGINE = true;

    private static final Boolean DEFAULT_MESSAGE_BROKER = false;
    private static final Boolean UPDATED_MESSAGE_BROKER = true;

    private static final Boolean DEFAULT_SERVICE_DISCOVERY_TYPE = false;
    private static final Boolean UPDATED_SERVICE_DISCOVERY_TYPE = true;

    private static final String DEFAULT_BUILD_TOOL = "AAAAAAAAAA";
    private static final String UPDATED_BUILD_TOOL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ENABLE_SWAGGER_CODEGEN = false;
    private static final Boolean UPDATED_ENABLE_SWAGGER_CODEGEN = true;

    private static final String DEFAULT_CLIENT_FRAMEWORK = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT_FRAMEWORK = "BBBBBBBBBB";

    private static final Boolean DEFAULT_USE_SASS = false;
    private static final Boolean UPDATED_USE_SASS = true;

    private static final String DEFAULT_CLIENT_PACKAGE_MANAGER = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT_PACKAGE_MANAGER = "BBBBBBBBBB";

    private static final String DEFAULT_APPLICATION_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_APPLICATION_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_JHI_PREFIX = "AAAAAAAAAA";
    private static final String UPDATED_JHI_PREFIX = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ENABLE_TRANSLATION = false;
    private static final Boolean UPDATED_ENABLE_TRANSLATION = true;

    private static final String DEFAULT_NATIVE_LANGUAGE = "AAAAAAAAAA";
    private static final String UPDATED_NATIVE_LANGUAGE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_HAS_PROTRACTOR = false;
    private static final Boolean UPDATED_HAS_PROTRACTOR = true;

    private static final Boolean DEFAULT_HAS_GATLING = false;
    private static final Boolean UPDATED_HAS_GATLING = true;

    private static final Boolean DEFAULT_HAS_CUCUMBER = false;
    private static final Boolean UPDATED_HAS_CUCUMBER = true;

    @Autowired
    private YoRCRepository yoRCRepository;

    @Autowired
    private YoRCService yoRCService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restYoRCMockMvc;

    private YoRC yoRC;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static YoRC createEntity(EntityManager em) {
        YoRC yoRC = new YoRC()
            .jhipsterVersion(DEFAULT_JHIPSTER_VERSION)
            .creationDate(DEFAULT_CREATION_DATE)
            .gitProvider(DEFAULT_GIT_PROVIDER)
            .nodeVersion(DEFAULT_NODE_VERSION)
            .os(DEFAULT_OS)
            .arch(DEFAULT_ARCH)
            .cpu(DEFAULT_CPU)
            .cores(DEFAULT_CORES)
            .memory(DEFAULT_MEMORY)
            .userLanguage(DEFAULT_USER_LANGUAGE)
            .year(DEFAULT_YEAR)
            .month(DEFAULT_MONTH)
            .week(DEFAULT_WEEK)
            .day(DEFAULT_DAY)
            .hour(DEFAULT_HOUR)
            .serverPort(DEFAULT_SERVER_PORT)
            .authenticationType(DEFAULT_AUTHENTICATION_TYPE)
            .cacheProvider(DEFAULT_CACHE_PROVIDER)
            .enableHibernateCache(DEFAULT_ENABLE_HIBERNATE_CACHE)
            .websocket(DEFAULT_WEBSOCKET)
            .databaseType(DEFAULT_DATABASE_TYPE)
            .devDatabaseType(DEFAULT_DEV_DATABASE_TYPE)
            .prodDatabaseType(DEFAULT_PROD_DATABASE_TYPE)
            .searchEngine(DEFAULT_SEARCH_ENGINE)
            .messageBroker(DEFAULT_MESSAGE_BROKER)
            .serviceDiscoveryType(DEFAULT_SERVICE_DISCOVERY_TYPE)
            .buildTool(DEFAULT_BUILD_TOOL)
            .enableSwaggerCodegen(DEFAULT_ENABLE_SWAGGER_CODEGEN)
            .clientFramework(DEFAULT_CLIENT_FRAMEWORK)
            .useSass(DEFAULT_USE_SASS)
            .clientPackageManager(DEFAULT_CLIENT_PACKAGE_MANAGER)
            .applicationType(DEFAULT_APPLICATION_TYPE)
            .jhiPrefix(DEFAULT_JHI_PREFIX)
            .enableTranslation(DEFAULT_ENABLE_TRANSLATION)
            .nativeLanguage(DEFAULT_NATIVE_LANGUAGE)
            .hasProtractor(DEFAULT_HAS_PROTRACTOR)
            .hasGatling(DEFAULT_HAS_GATLING)
            .hasCucumber(DEFAULT_HAS_CUCUMBER);
        return yoRC;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static YoRC createUpdatedEntity(EntityManager em) {
        YoRC yoRC = new YoRC()
            .jhipsterVersion(UPDATED_JHIPSTER_VERSION)
            .creationDate(UPDATED_CREATION_DATE)
            .gitProvider(UPDATED_GIT_PROVIDER)
            .nodeVersion(UPDATED_NODE_VERSION)
            .os(UPDATED_OS)
            .arch(UPDATED_ARCH)
            .cpu(UPDATED_CPU)
            .cores(UPDATED_CORES)
            .memory(UPDATED_MEMORY)
            .userLanguage(UPDATED_USER_LANGUAGE)
            .year(UPDATED_YEAR)
            .month(UPDATED_MONTH)
            .week(UPDATED_WEEK)
            .day(UPDATED_DAY)
            .hour(UPDATED_HOUR)
            .serverPort(UPDATED_SERVER_PORT)
            .authenticationType(UPDATED_AUTHENTICATION_TYPE)
            .cacheProvider(UPDATED_CACHE_PROVIDER)
            .enableHibernateCache(UPDATED_ENABLE_HIBERNATE_CACHE)
            .websocket(UPDATED_WEBSOCKET)
            .databaseType(UPDATED_DATABASE_TYPE)
            .devDatabaseType(UPDATED_DEV_DATABASE_TYPE)
            .prodDatabaseType(UPDATED_PROD_DATABASE_TYPE)
            .searchEngine(UPDATED_SEARCH_ENGINE)
            .messageBroker(UPDATED_MESSAGE_BROKER)
            .serviceDiscoveryType(UPDATED_SERVICE_DISCOVERY_TYPE)
            .buildTool(UPDATED_BUILD_TOOL)
            .enableSwaggerCodegen(UPDATED_ENABLE_SWAGGER_CODEGEN)
            .clientFramework(UPDATED_CLIENT_FRAMEWORK)
            .useSass(UPDATED_USE_SASS)
            .clientPackageManager(UPDATED_CLIENT_PACKAGE_MANAGER)
            .applicationType(UPDATED_APPLICATION_TYPE)
            .jhiPrefix(UPDATED_JHI_PREFIX)
            .enableTranslation(UPDATED_ENABLE_TRANSLATION)
            .nativeLanguage(UPDATED_NATIVE_LANGUAGE)
            .hasProtractor(UPDATED_HAS_PROTRACTOR)
            .hasGatling(UPDATED_HAS_GATLING)
            .hasCucumber(UPDATED_HAS_CUCUMBER);
        return yoRC;
    }

    @BeforeEach
    public void initTest() {
        yoRC = createEntity(em);
    }

    @Test
    @Transactional
    public void createYoRC() throws Exception {
        int databaseSizeBeforeCreate = yoRCRepository.findAll().size();

        // Create the YoRC
        restYoRCMockMvc.perform(post("/api/yo-rcs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(yoRC)))
            .andExpect(status().isCreated());

        // Validate the YoRC in the database
        List<YoRC> yoRCList = yoRCRepository.findAll();
        assertThat(yoRCList).hasSize(databaseSizeBeforeCreate + 1);
        YoRC testYoRC = yoRCList.get(yoRCList.size() - 1);
        assertThat(testYoRC.getJhipsterVersion()).isEqualTo(DEFAULT_JHIPSTER_VERSION);
        assertThat(testYoRC.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testYoRC.getGitProvider()).isEqualTo(DEFAULT_GIT_PROVIDER);
        assertThat(testYoRC.getNodeVersion()).isEqualTo(DEFAULT_NODE_VERSION);
        assertThat(testYoRC.getOs()).isEqualTo(DEFAULT_OS);
        assertThat(testYoRC.getArch()).isEqualTo(DEFAULT_ARCH);
        assertThat(testYoRC.getCpu()).isEqualTo(DEFAULT_CPU);
        assertThat(testYoRC.getCores()).isEqualTo(DEFAULT_CORES);
        assertThat(testYoRC.getMemory()).isEqualTo(DEFAULT_MEMORY);
        assertThat(testYoRC.getUserLanguage()).isEqualTo(DEFAULT_USER_LANGUAGE);
        assertThat(testYoRC.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testYoRC.getMonth()).isEqualTo(DEFAULT_MONTH);
        assertThat(testYoRC.getWeek()).isEqualTo(DEFAULT_WEEK);
        assertThat(testYoRC.getDay()).isEqualTo(DEFAULT_DAY);
        assertThat(testYoRC.getHour()).isEqualTo(DEFAULT_HOUR);
        assertThat(testYoRC.getServerPort()).isEqualTo(DEFAULT_SERVER_PORT);
        assertThat(testYoRC.getAuthenticationType()).isEqualTo(DEFAULT_AUTHENTICATION_TYPE);
        assertThat(testYoRC.getCacheProvider()).isEqualTo(DEFAULT_CACHE_PROVIDER);
        assertThat(testYoRC.isEnableHibernateCache()).isEqualTo(DEFAULT_ENABLE_HIBERNATE_CACHE);
        assertThat(testYoRC.isWebsocket()).isEqualTo(DEFAULT_WEBSOCKET);
        assertThat(testYoRC.getDatabaseType()).isEqualTo(DEFAULT_DATABASE_TYPE);
        assertThat(testYoRC.getDevDatabaseType()).isEqualTo(DEFAULT_DEV_DATABASE_TYPE);
        assertThat(testYoRC.getProdDatabaseType()).isEqualTo(DEFAULT_PROD_DATABASE_TYPE);
        assertThat(testYoRC.isSearchEngine()).isEqualTo(DEFAULT_SEARCH_ENGINE);
        assertThat(testYoRC.isMessageBroker()).isEqualTo(DEFAULT_MESSAGE_BROKER);
        assertThat(testYoRC.isServiceDiscoveryType()).isEqualTo(DEFAULT_SERVICE_DISCOVERY_TYPE);
        assertThat(testYoRC.getBuildTool()).isEqualTo(DEFAULT_BUILD_TOOL);
        assertThat(testYoRC.isEnableSwaggerCodegen()).isEqualTo(DEFAULT_ENABLE_SWAGGER_CODEGEN);
        assertThat(testYoRC.getClientFramework()).isEqualTo(DEFAULT_CLIENT_FRAMEWORK);
        assertThat(testYoRC.isUseSass()).isEqualTo(DEFAULT_USE_SASS);
        assertThat(testYoRC.getClientPackageManager()).isEqualTo(DEFAULT_CLIENT_PACKAGE_MANAGER);
        assertThat(testYoRC.getApplicationType()).isEqualTo(DEFAULT_APPLICATION_TYPE);
        assertThat(testYoRC.getJhiPrefix()).isEqualTo(DEFAULT_JHI_PREFIX);
        assertThat(testYoRC.isEnableTranslation()).isEqualTo(DEFAULT_ENABLE_TRANSLATION);
        assertThat(testYoRC.getNativeLanguage()).isEqualTo(DEFAULT_NATIVE_LANGUAGE);
        assertThat(testYoRC.isHasProtractor()).isEqualTo(DEFAULT_HAS_PROTRACTOR);
        assertThat(testYoRC.isHasGatling()).isEqualTo(DEFAULT_HAS_GATLING);
        assertThat(testYoRC.isHasCucumber()).isEqualTo(DEFAULT_HAS_CUCUMBER);
    }

    @Test
    @Transactional
    public void createYoRCWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = yoRCRepository.findAll().size();

        // Create the YoRC with an existing ID
        yoRC.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restYoRCMockMvc.perform(post("/api/yo-rcs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(yoRC)))
            .andExpect(status().isBadRequest());

        // Validate the YoRC in the database
        List<YoRC> yoRCList = yoRCRepository.findAll();
        assertThat(yoRCList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllYoRCS() throws Exception {
        // Initialize the database
        yoRCRepository.saveAndFlush(yoRC);

        // Get all the yoRCList
        restYoRCMockMvc.perform(get("/api/yo-rcs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(yoRC.getId().intValue())))
            .andExpect(jsonPath("$.[*].jhipsterVersion").value(hasItem(DEFAULT_JHIPSTER_VERSION)))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].gitProvider").value(hasItem(DEFAULT_GIT_PROVIDER)))
            .andExpect(jsonPath("$.[*].nodeVersion").value(hasItem(DEFAULT_NODE_VERSION)))
            .andExpect(jsonPath("$.[*].os").value(hasItem(DEFAULT_OS)))
            .andExpect(jsonPath("$.[*].arch").value(hasItem(DEFAULT_ARCH)))
            .andExpect(jsonPath("$.[*].cpu").value(hasItem(DEFAULT_CPU)))
            .andExpect(jsonPath("$.[*].cores").value(hasItem(DEFAULT_CORES)))
            .andExpect(jsonPath("$.[*].memory").value(hasItem(DEFAULT_MEMORY)))
            .andExpect(jsonPath("$.[*].userLanguage").value(hasItem(DEFAULT_USER_LANGUAGE)))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].month").value(hasItem(DEFAULT_MONTH)))
            .andExpect(jsonPath("$.[*].week").value(hasItem(DEFAULT_WEEK)))
            .andExpect(jsonPath("$.[*].day").value(hasItem(DEFAULT_DAY)))
            .andExpect(jsonPath("$.[*].hour").value(hasItem(DEFAULT_HOUR)))
            .andExpect(jsonPath("$.[*].serverPort").value(hasItem(DEFAULT_SERVER_PORT)))
            .andExpect(jsonPath("$.[*].authenticationType").value(hasItem(DEFAULT_AUTHENTICATION_TYPE)))
            .andExpect(jsonPath("$.[*].cacheProvider").value(hasItem(DEFAULT_CACHE_PROVIDER)))
            .andExpect(jsonPath("$.[*].enableHibernateCache").value(hasItem(DEFAULT_ENABLE_HIBERNATE_CACHE.booleanValue())))
            .andExpect(jsonPath("$.[*].websocket").value(hasItem(DEFAULT_WEBSOCKET.booleanValue())))
            .andExpect(jsonPath("$.[*].databaseType").value(hasItem(DEFAULT_DATABASE_TYPE)))
            .andExpect(jsonPath("$.[*].devDatabaseType").value(hasItem(DEFAULT_DEV_DATABASE_TYPE)))
            .andExpect(jsonPath("$.[*].prodDatabaseType").value(hasItem(DEFAULT_PROD_DATABASE_TYPE)))
            .andExpect(jsonPath("$.[*].searchEngine").value(hasItem(DEFAULT_SEARCH_ENGINE.booleanValue())))
            .andExpect(jsonPath("$.[*].messageBroker").value(hasItem(DEFAULT_MESSAGE_BROKER.booleanValue())))
            .andExpect(jsonPath("$.[*].serviceDiscoveryType").value(hasItem(DEFAULT_SERVICE_DISCOVERY_TYPE.booleanValue())))
            .andExpect(jsonPath("$.[*].buildTool").value(hasItem(DEFAULT_BUILD_TOOL)))
            .andExpect(jsonPath("$.[*].enableSwaggerCodegen").value(hasItem(DEFAULT_ENABLE_SWAGGER_CODEGEN.booleanValue())))
            .andExpect(jsonPath("$.[*].clientFramework").value(hasItem(DEFAULT_CLIENT_FRAMEWORK)))
            .andExpect(jsonPath("$.[*].useSass").value(hasItem(DEFAULT_USE_SASS.booleanValue())))
            .andExpect(jsonPath("$.[*].clientPackageManager").value(hasItem(DEFAULT_CLIENT_PACKAGE_MANAGER)))
            .andExpect(jsonPath("$.[*].applicationType").value(hasItem(DEFAULT_APPLICATION_TYPE)))
            .andExpect(jsonPath("$.[*].jhiPrefix").value(hasItem(DEFAULT_JHI_PREFIX)))
            .andExpect(jsonPath("$.[*].enableTranslation").value(hasItem(DEFAULT_ENABLE_TRANSLATION.booleanValue())))
            .andExpect(jsonPath("$.[*].nativeLanguage").value(hasItem(DEFAULT_NATIVE_LANGUAGE)))
            .andExpect(jsonPath("$.[*].hasProtractor").value(hasItem(DEFAULT_HAS_PROTRACTOR.booleanValue())))
            .andExpect(jsonPath("$.[*].hasGatling").value(hasItem(DEFAULT_HAS_GATLING.booleanValue())))
            .andExpect(jsonPath("$.[*].hasCucumber").value(hasItem(DEFAULT_HAS_CUCUMBER.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getYoRC() throws Exception {
        // Initialize the database
        yoRCRepository.saveAndFlush(yoRC);

        // Get the yoRC
        restYoRCMockMvc.perform(get("/api/yo-rcs/{id}", yoRC.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(yoRC.getId().intValue()))
            .andExpect(jsonPath("$.jhipsterVersion").value(DEFAULT_JHIPSTER_VERSION))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.gitProvider").value(DEFAULT_GIT_PROVIDER))
            .andExpect(jsonPath("$.nodeVersion").value(DEFAULT_NODE_VERSION))
            .andExpect(jsonPath("$.os").value(DEFAULT_OS))
            .andExpect(jsonPath("$.arch").value(DEFAULT_ARCH))
            .andExpect(jsonPath("$.cpu").value(DEFAULT_CPU))
            .andExpect(jsonPath("$.cores").value(DEFAULT_CORES))
            .andExpect(jsonPath("$.memory").value(DEFAULT_MEMORY))
            .andExpect(jsonPath("$.userLanguage").value(DEFAULT_USER_LANGUAGE))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR))
            .andExpect(jsonPath("$.month").value(DEFAULT_MONTH))
            .andExpect(jsonPath("$.week").value(DEFAULT_WEEK))
            .andExpect(jsonPath("$.day").value(DEFAULT_DAY))
            .andExpect(jsonPath("$.hour").value(DEFAULT_HOUR))
            .andExpect(jsonPath("$.serverPort").value(DEFAULT_SERVER_PORT))
            .andExpect(jsonPath("$.authenticationType").value(DEFAULT_AUTHENTICATION_TYPE))
            .andExpect(jsonPath("$.cacheProvider").value(DEFAULT_CACHE_PROVIDER))
            .andExpect(jsonPath("$.enableHibernateCache").value(DEFAULT_ENABLE_HIBERNATE_CACHE.booleanValue()))
            .andExpect(jsonPath("$.websocket").value(DEFAULT_WEBSOCKET.booleanValue()))
            .andExpect(jsonPath("$.databaseType").value(DEFAULT_DATABASE_TYPE))
            .andExpect(jsonPath("$.devDatabaseType").value(DEFAULT_DEV_DATABASE_TYPE))
            .andExpect(jsonPath("$.prodDatabaseType").value(DEFAULT_PROD_DATABASE_TYPE))
            .andExpect(jsonPath("$.searchEngine").value(DEFAULT_SEARCH_ENGINE.booleanValue()))
            .andExpect(jsonPath("$.messageBroker").value(DEFAULT_MESSAGE_BROKER.booleanValue()))
            .andExpect(jsonPath("$.serviceDiscoveryType").value(DEFAULT_SERVICE_DISCOVERY_TYPE.booleanValue()))
            .andExpect(jsonPath("$.buildTool").value(DEFAULT_BUILD_TOOL))
            .andExpect(jsonPath("$.enableSwaggerCodegen").value(DEFAULT_ENABLE_SWAGGER_CODEGEN.booleanValue()))
            .andExpect(jsonPath("$.clientFramework").value(DEFAULT_CLIENT_FRAMEWORK))
            .andExpect(jsonPath("$.useSass").value(DEFAULT_USE_SASS.booleanValue()))
            .andExpect(jsonPath("$.clientPackageManager").value(DEFAULT_CLIENT_PACKAGE_MANAGER))
            .andExpect(jsonPath("$.applicationType").value(DEFAULT_APPLICATION_TYPE))
            .andExpect(jsonPath("$.jhiPrefix").value(DEFAULT_JHI_PREFIX))
            .andExpect(jsonPath("$.enableTranslation").value(DEFAULT_ENABLE_TRANSLATION.booleanValue()))
            .andExpect(jsonPath("$.nativeLanguage").value(DEFAULT_NATIVE_LANGUAGE))
            .andExpect(jsonPath("$.hasProtractor").value(DEFAULT_HAS_PROTRACTOR.booleanValue()))
            .andExpect(jsonPath("$.hasGatling").value(DEFAULT_HAS_GATLING.booleanValue()))
            .andExpect(jsonPath("$.hasCucumber").value(DEFAULT_HAS_CUCUMBER.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingYoRC() throws Exception {
        // Get the yoRC
        restYoRCMockMvc.perform(get("/api/yo-rcs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateYoRC() throws Exception {
        // Initialize the database
        yoRCService.save(yoRC);

        int databaseSizeBeforeUpdate = yoRCRepository.findAll().size();

        // Update the yoRC
        YoRC updatedYoRC = yoRCRepository.findById(yoRC.getId()).get();
        // Disconnect from session so that the updates on updatedYoRC are not directly saved in db
        em.detach(updatedYoRC);
        updatedYoRC
            .jhipsterVersion(UPDATED_JHIPSTER_VERSION)
            .creationDate(UPDATED_CREATION_DATE)
            .gitProvider(UPDATED_GIT_PROVIDER)
            .nodeVersion(UPDATED_NODE_VERSION)
            .os(UPDATED_OS)
            .arch(UPDATED_ARCH)
            .cpu(UPDATED_CPU)
            .cores(UPDATED_CORES)
            .memory(UPDATED_MEMORY)
            .userLanguage(UPDATED_USER_LANGUAGE)
            .year(UPDATED_YEAR)
            .month(UPDATED_MONTH)
            .week(UPDATED_WEEK)
            .day(UPDATED_DAY)
            .hour(UPDATED_HOUR)
            .serverPort(UPDATED_SERVER_PORT)
            .authenticationType(UPDATED_AUTHENTICATION_TYPE)
            .cacheProvider(UPDATED_CACHE_PROVIDER)
            .enableHibernateCache(UPDATED_ENABLE_HIBERNATE_CACHE)
            .websocket(UPDATED_WEBSOCKET)
            .databaseType(UPDATED_DATABASE_TYPE)
            .devDatabaseType(UPDATED_DEV_DATABASE_TYPE)
            .prodDatabaseType(UPDATED_PROD_DATABASE_TYPE)
            .searchEngine(UPDATED_SEARCH_ENGINE)
            .messageBroker(UPDATED_MESSAGE_BROKER)
            .serviceDiscoveryType(UPDATED_SERVICE_DISCOVERY_TYPE)
            .buildTool(UPDATED_BUILD_TOOL)
            .enableSwaggerCodegen(UPDATED_ENABLE_SWAGGER_CODEGEN)
            .clientFramework(UPDATED_CLIENT_FRAMEWORK)
            .useSass(UPDATED_USE_SASS)
            .clientPackageManager(UPDATED_CLIENT_PACKAGE_MANAGER)
            .applicationType(UPDATED_APPLICATION_TYPE)
            .jhiPrefix(UPDATED_JHI_PREFIX)
            .enableTranslation(UPDATED_ENABLE_TRANSLATION)
            .nativeLanguage(UPDATED_NATIVE_LANGUAGE)
            .hasProtractor(UPDATED_HAS_PROTRACTOR)
            .hasGatling(UPDATED_HAS_GATLING)
            .hasCucumber(UPDATED_HAS_CUCUMBER);

        restYoRCMockMvc.perform(put("/api/yo-rcs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedYoRC)))
            .andExpect(status().isOk());

        // Validate the YoRC in the database
        List<YoRC> yoRCList = yoRCRepository.findAll();
        assertThat(yoRCList).hasSize(databaseSizeBeforeUpdate);
        YoRC testYoRC = yoRCList.get(yoRCList.size() - 1);
        assertThat(testYoRC.getJhipsterVersion()).isEqualTo(UPDATED_JHIPSTER_VERSION);
        assertThat(testYoRC.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testYoRC.getGitProvider()).isEqualTo(UPDATED_GIT_PROVIDER);
        assertThat(testYoRC.getNodeVersion()).isEqualTo(UPDATED_NODE_VERSION);
        assertThat(testYoRC.getOs()).isEqualTo(UPDATED_OS);
        assertThat(testYoRC.getArch()).isEqualTo(UPDATED_ARCH);
        assertThat(testYoRC.getCpu()).isEqualTo(UPDATED_CPU);
        assertThat(testYoRC.getCores()).isEqualTo(UPDATED_CORES);
        assertThat(testYoRC.getMemory()).isEqualTo(UPDATED_MEMORY);
        assertThat(testYoRC.getUserLanguage()).isEqualTo(UPDATED_USER_LANGUAGE);
        assertThat(testYoRC.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testYoRC.getMonth()).isEqualTo(UPDATED_MONTH);
        assertThat(testYoRC.getWeek()).isEqualTo(UPDATED_WEEK);
        assertThat(testYoRC.getDay()).isEqualTo(UPDATED_DAY);
        assertThat(testYoRC.getHour()).isEqualTo(UPDATED_HOUR);
        assertThat(testYoRC.getServerPort()).isEqualTo(UPDATED_SERVER_PORT);
        assertThat(testYoRC.getAuthenticationType()).isEqualTo(UPDATED_AUTHENTICATION_TYPE);
        assertThat(testYoRC.getCacheProvider()).isEqualTo(UPDATED_CACHE_PROVIDER);
        assertThat(testYoRC.isEnableHibernateCache()).isEqualTo(UPDATED_ENABLE_HIBERNATE_CACHE);
        assertThat(testYoRC.isWebsocket()).isEqualTo(UPDATED_WEBSOCKET);
        assertThat(testYoRC.getDatabaseType()).isEqualTo(UPDATED_DATABASE_TYPE);
        assertThat(testYoRC.getDevDatabaseType()).isEqualTo(UPDATED_DEV_DATABASE_TYPE);
        assertThat(testYoRC.getProdDatabaseType()).isEqualTo(UPDATED_PROD_DATABASE_TYPE);
        assertThat(testYoRC.isSearchEngine()).isEqualTo(UPDATED_SEARCH_ENGINE);
        assertThat(testYoRC.isMessageBroker()).isEqualTo(UPDATED_MESSAGE_BROKER);
        assertThat(testYoRC.isServiceDiscoveryType()).isEqualTo(UPDATED_SERVICE_DISCOVERY_TYPE);
        assertThat(testYoRC.getBuildTool()).isEqualTo(UPDATED_BUILD_TOOL);
        assertThat(testYoRC.isEnableSwaggerCodegen()).isEqualTo(UPDATED_ENABLE_SWAGGER_CODEGEN);
        assertThat(testYoRC.getClientFramework()).isEqualTo(UPDATED_CLIENT_FRAMEWORK);
        assertThat(testYoRC.isUseSass()).isEqualTo(UPDATED_USE_SASS);
        assertThat(testYoRC.getClientPackageManager()).isEqualTo(UPDATED_CLIENT_PACKAGE_MANAGER);
        assertThat(testYoRC.getApplicationType()).isEqualTo(UPDATED_APPLICATION_TYPE);
        assertThat(testYoRC.getJhiPrefix()).isEqualTo(UPDATED_JHI_PREFIX);
        assertThat(testYoRC.isEnableTranslation()).isEqualTo(UPDATED_ENABLE_TRANSLATION);
        assertThat(testYoRC.getNativeLanguage()).isEqualTo(UPDATED_NATIVE_LANGUAGE);
        assertThat(testYoRC.isHasProtractor()).isEqualTo(UPDATED_HAS_PROTRACTOR);
        assertThat(testYoRC.isHasGatling()).isEqualTo(UPDATED_HAS_GATLING);
        assertThat(testYoRC.isHasCucumber()).isEqualTo(UPDATED_HAS_CUCUMBER);
    }

    @Test
    @Transactional
    public void updateNonExistingYoRC() throws Exception {
        int databaseSizeBeforeUpdate = yoRCRepository.findAll().size();

        // Create the YoRC

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restYoRCMockMvc.perform(put("/api/yo-rcs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(yoRC)))
            .andExpect(status().isBadRequest());

        // Validate the YoRC in the database
        List<YoRC> yoRCList = yoRCRepository.findAll();
        assertThat(yoRCList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteYoRC() throws Exception {
        // Initialize the database
        yoRCService.save(yoRC);

        int databaseSizeBeforeDelete = yoRCRepository.findAll().size();

        // Delete the yoRC
        restYoRCMockMvc.perform(delete("/api/yo-rcs/{id}", yoRC.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<YoRC> yoRCList = yoRCRepository.findAll();
        assertThat(yoRCList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
