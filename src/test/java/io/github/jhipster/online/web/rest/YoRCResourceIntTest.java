/**
 * Copyright 2017-2024 the original author or authors from the JHipster project.
 *
 * This file is part of the JHipster Online project, see https://github.com/jhipster/jhipster-online
 * for more information.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.jhipster.online.web.rest;

import static io.github.jhipster.online.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.jhipster.online.JhonlineApp;
import io.github.jhipster.online.domain.YoRC;
import io.github.jhipster.online.repository.YoRCRepository;
import io.github.jhipster.online.service.YoRCService;
import io.github.jhipster.online.service.dto.YoRCDTO;
import io.github.jhipster.online.service.mapper.YoRCMapper;
import io.github.jhipster.online.web.rest.errors.ExceptionTranslator;
import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

/**
 * Test class for the YoRCResource REST controller.
 *
 * @see YoRCResource
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = JhonlineApp.class)
class YoRCResourceIntTest {

    private static final String DEFAULT_JHIPSTER_VERSION = "AAAAAAAAAA";

    private static final Instant DEFAULT_CREATION_DATE = Instant.ofEpochMilli(1000L);

    private static final String DEFAULT_GIT_PROVIDER = "AAAAAAAAAA";

    private static final String DEFAULT_NODE_VERSION = "AAAAAAAAAA";

    private static final String DEFAULT_OS = "AAAAAAAAAA";

    private static final String DEFAULT_ARCH = "AAAAAAAAAA";

    private static final String DEFAULT_CPU = "AAAAAAAAAA";

    private static final String DEFAULT_CORES = "AAAAAAAAAA";

    private static final String DEFAULT_MEMORY = "AAAAAAAAAA";

    private static final String DEFAULT_USER_LANGUAGE = "AAAAAAAAAA";

    private static final Integer DEFAULT_YEAR = 1;

    private static final Integer DEFAULT_MONTH = 1;

    private static final Integer DEFAULT_WEEK = 1;

    private static final Integer DEFAULT_DAY = 1;

    private static final Integer DEFAULT_HOUR = 1;

    private static final String DEFAULT_SERVER_PORT = "AAAAAAAAAA";

    private static final String DEFAULT_AUTHENTICATION_TYPE = "AAAAAAAAAA";

    private static final String DEFAULT_CACHE_PROVIDER = "AAAAAAAAAA";

    private static final Boolean DEFAULT_ENABLE_HIBERNATE_CACHE = false;

    private static final Boolean DEFAULT_WEBSOCKET = false;

    private static final String DEFAULT_DATABASE_TYPE = "AAAAAAAAAA";

    private static final String DEFAULT_DEV_DATABASE_TYPE = "AAAAAAAAAA";

    private static final String DEFAULT_PROD_DATABASE_TYPE = "AAAAAAAAAA";

    private static final Boolean DEFAULT_SEARCH_ENGINE = false;

    private static final Boolean DEFAULT_MESSAGE_BROKER = false;

    private static final Boolean DEFAULT_SERVICE_DISCOVERY_TYPE = false;

    private static final String DEFAULT_BUILD_TOOL = "AAAAAAAAAA";

    private static final Boolean DEFAULT_ENABLE_SWAGGER_CODEGEN = false;

    private static final String DEFAULT_CLIENT_FRAMEWORK = "AAAAAAAAAA";

    private static final Boolean DEFAULT_WITH_ADMIN_UI = true;

    private static final Boolean DEFAULT_USE_SASS = false;

    private static final String DEFAULT_CLIENT_PACKAGE_MANAGER = "AAAAAAAAAA";

    private static final String DEFAULT_APPLICATION_TYPE = "AAAAAAAAAA";

    private static final String DEFAULT_JHI_PREFIX = "AAAAAAAAAA";

    private static final Boolean DEFAULT_ENABLE_TRANSLATION = false;

    private static final String DEFAULT_NATIVE_LANGUAGE = "AAAAAAAAAA";

    private static final Boolean DEFAULT_HAS_PROTRACTOR = false;

    private static final Boolean DEFAULT_HAS_GATLING = false;

    private static final Boolean DEFAULT_HAS_CUCUMBER = false;

    @Autowired
    private YoRCRepository yoRCRepository;

    @Autowired
    private YoRCService yoRCService;

    @Autowired
    private YoRCMapper yoRCMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restYoRCMockMvc;

    private YoRC yoRC;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final YoRCResource yoRCResource = new YoRCResource(yoRCService);
        this.restYoRCMockMvc =
            MockMvcBuilders
                .standaloneSetup(yoRCResource)
                .setCustomArgumentResolvers(pageableArgumentResolver)
                .setControllerAdvice(exceptionTranslator)
                .setConversionService(createFormattingConversionService())
                .setMessageConverters(jacksonMessageConverter)
                .build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static YoRC createEntity() {
        return new YoRC()
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
            .withAdminUi(DEFAULT_WITH_ADMIN_UI)
            .useSass(DEFAULT_USE_SASS)
            .clientPackageManager(DEFAULT_CLIENT_PACKAGE_MANAGER)
            .applicationType(DEFAULT_APPLICATION_TYPE)
            .jhiPrefix(DEFAULT_JHI_PREFIX)
            .enableTranslation(DEFAULT_ENABLE_TRANSLATION)
            .nativeLanguage(DEFAULT_NATIVE_LANGUAGE)
            .hasProtractor(DEFAULT_HAS_PROTRACTOR)
            .hasGatling(DEFAULT_HAS_GATLING)
            .hasCucumber(DEFAULT_HAS_CUCUMBER);
    }

    @BeforeEach
    public void initTest() {
        yoRC = createEntity();
    }

    @Test
    @Transactional
    void createYoRC() throws Exception {
        int databaseSizeBeforeCreate = yoRCRepository.findAll().size();

        // Create the EntityStats
        restYoRCMockMvc
            .perform(post("/api/yo-rcs").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(yoRC)))
            .andExpect(status().isCreated());

        // Validate the EntityStats in the database
        List<YoRC> entityStatsList = yoRCRepository.findAll();
        assertThat(entityStatsList).hasSize(databaseSizeBeforeCreate + 1);

        final YoRC persisted = entityStatsList.get(0);
        assertThat(persisted.getJhipsterVersion()).isEqualTo(DEFAULT_JHIPSTER_VERSION);
        assertThat(persisted.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(persisted.getGitProvider()).isEqualTo(DEFAULT_GIT_PROVIDER);
        assertThat(persisted.getNodeVersion()).isEqualTo(DEFAULT_NODE_VERSION);
        assertThat(persisted.getOs()).isEqualTo(DEFAULT_OS);
        assertThat(persisted.getArch()).isEqualTo(DEFAULT_ARCH);
        assertThat(persisted.getCpu()).isEqualTo(DEFAULT_CPU);
        assertThat(persisted.getCores()).isEqualTo(DEFAULT_CORES);
        assertThat(persisted.getMemory()).isEqualTo(DEFAULT_MEMORY);
        assertThat(persisted.getUserLanguage()).isEqualTo(DEFAULT_USER_LANGUAGE);
        assertThat(persisted.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(persisted.getMonth()).isEqualTo(DEFAULT_MONTH);
        assertThat(persisted.getWeek()).isEqualTo(DEFAULT_WEEK);
        assertThat(persisted.getDay()).isEqualTo(DEFAULT_DAY);
        assertThat(persisted.getHour()).isEqualTo(DEFAULT_HOUR);
        assertThat(persisted.getServerPort()).isEqualTo(DEFAULT_SERVER_PORT);
        assertThat(persisted.getAuthenticationType()).isEqualTo(DEFAULT_AUTHENTICATION_TYPE);
        assertThat(persisted.getCacheProvider()).isEqualTo(DEFAULT_CACHE_PROVIDER);
        assertThat(persisted.isEnableHibernateCache()).isEqualTo(DEFAULT_ENABLE_HIBERNATE_CACHE);
        assertThat(persisted.isWebsocket()).isEqualTo(DEFAULT_WEBSOCKET);
        assertThat(persisted.getDatabaseType()).isEqualTo(DEFAULT_DATABASE_TYPE);
        assertThat(persisted.getDevDatabaseType()).isEqualTo(DEFAULT_DEV_DATABASE_TYPE);
        assertThat(persisted.getProdDatabaseType()).isEqualTo(DEFAULT_PROD_DATABASE_TYPE);
        assertThat(persisted.isSearchEngine()).isEqualTo(DEFAULT_SEARCH_ENGINE);
        assertThat(persisted.isMessageBroker()).isEqualTo(DEFAULT_MESSAGE_BROKER);
        assertThat(persisted.isServiceDiscoveryType()).isEqualTo(DEFAULT_SERVICE_DISCOVERY_TYPE);
        assertThat(persisted.getBuildTool()).isEqualTo(DEFAULT_BUILD_TOOL);
        assertThat(persisted.isEnableSwaggerCodegen()).isEqualTo(DEFAULT_ENABLE_SWAGGER_CODEGEN);
        assertThat(persisted.getClientFramework()).isEqualTo(DEFAULT_CLIENT_FRAMEWORK);
        assertThat(persisted.isUseSass()).isEqualTo(DEFAULT_USE_SASS);
        assertThat(persisted.getClientPackageManager()).isEqualTo(DEFAULT_CLIENT_PACKAGE_MANAGER);
        assertThat(persisted.getApplicationType()).isEqualTo(DEFAULT_APPLICATION_TYPE);
        assertThat(persisted.getJhiPrefix()).isEqualTo(DEFAULT_JHI_PREFIX);
        assertThat(persisted.isEnableTranslation()).isEqualTo(DEFAULT_ENABLE_TRANSLATION);
        assertThat(persisted.getNativeLanguage()).isEqualTo(DEFAULT_NATIVE_LANGUAGE);
        assertThat(persisted.isHasProtractor()).isEqualTo(DEFAULT_HAS_PROTRACTOR);
        assertThat(persisted.isHasGatling()).isEqualTo(DEFAULT_HAS_GATLING);
        assertThat(persisted.isHasCucumber()).isEqualTo(DEFAULT_HAS_CUCUMBER);
    }

    @Test
    @Transactional
    void cannotCreateYoRCWithId() throws Exception {
        int databaseSizeBeforeCreate = yoRCRepository.findAll().size();
        yoRC.setId(12345L);
        // Create the EntityStats
        restYoRCMockMvc
            .perform(post("/api/yo-rcs").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(yoRC)))
            .andExpect(status().isBadRequest());

        // Validate the EntityStats in the database
        List<YoRC> entityStatsList = yoRCRepository.findAll();
        assertThat(entityStatsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void updateYoRC() throws Exception {
        // Initialize the database
        yoRCRepository.saveAndFlush(yoRC);

        int databaseSizeBeforeUpdate = yoRCRepository.findAll().size();

        // Create the EntityStats
        restYoRCMockMvc
            .perform(put("/api/yo-rcs").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(yoRC)))
            .andExpect(status().isOk());

        // Validate the EntityStats in the database
        List<YoRC> entityStatsList = yoRCRepository.findAll();
        assertThat(entityStatsList).hasSize(databaseSizeBeforeUpdate);

        final YoRC persisted = entityStatsList.get(0);
        assertThat(persisted.getJhipsterVersion()).isEqualTo(DEFAULT_JHIPSTER_VERSION);
        assertThat(persisted.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(persisted.getGitProvider()).isEqualTo(DEFAULT_GIT_PROVIDER);
        assertThat(persisted.getNodeVersion()).isEqualTo(DEFAULT_NODE_VERSION);
        assertThat(persisted.getOs()).isEqualTo(DEFAULT_OS);
        assertThat(persisted.getArch()).isEqualTo(DEFAULT_ARCH);
        assertThat(persisted.getCpu()).isEqualTo(DEFAULT_CPU);
        assertThat(persisted.getCores()).isEqualTo(DEFAULT_CORES);
        assertThat(persisted.getMemory()).isEqualTo(DEFAULT_MEMORY);
        assertThat(persisted.getUserLanguage()).isEqualTo(DEFAULT_USER_LANGUAGE);
        assertThat(persisted.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(persisted.getMonth()).isEqualTo(DEFAULT_MONTH);
        assertThat(persisted.getWeek()).isEqualTo(DEFAULT_WEEK);
        assertThat(persisted.getDay()).isEqualTo(DEFAULT_DAY);
        assertThat(persisted.getHour()).isEqualTo(DEFAULT_HOUR);
        assertThat(persisted.getServerPort()).isEqualTo(DEFAULT_SERVER_PORT);
        assertThat(persisted.getAuthenticationType()).isEqualTo(DEFAULT_AUTHENTICATION_TYPE);
        assertThat(persisted.getCacheProvider()).isEqualTo(DEFAULT_CACHE_PROVIDER);
        assertThat(persisted.isEnableHibernateCache()).isEqualTo(DEFAULT_ENABLE_HIBERNATE_CACHE);
        assertThat(persisted.isWebsocket()).isEqualTo(DEFAULT_WEBSOCKET);
        assertThat(persisted.getDatabaseType()).isEqualTo(DEFAULT_DATABASE_TYPE);
        assertThat(persisted.getDevDatabaseType()).isEqualTo(DEFAULT_DEV_DATABASE_TYPE);
        assertThat(persisted.getProdDatabaseType()).isEqualTo(DEFAULT_PROD_DATABASE_TYPE);
        assertThat(persisted.isSearchEngine()).isEqualTo(DEFAULT_SEARCH_ENGINE);
        assertThat(persisted.isMessageBroker()).isEqualTo(DEFAULT_MESSAGE_BROKER);
        assertThat(persisted.isServiceDiscoveryType()).isEqualTo(DEFAULT_SERVICE_DISCOVERY_TYPE);
        assertThat(persisted.getBuildTool()).isEqualTo(DEFAULT_BUILD_TOOL);
        assertThat(persisted.isEnableSwaggerCodegen()).isEqualTo(DEFAULT_ENABLE_SWAGGER_CODEGEN);
        assertThat(persisted.getClientFramework()).isEqualTo(DEFAULT_CLIENT_FRAMEWORK);
        assertThat(persisted.isUseSass()).isEqualTo(DEFAULT_USE_SASS);
        assertThat(persisted.getClientPackageManager()).isEqualTo(DEFAULT_CLIENT_PACKAGE_MANAGER);
        assertThat(persisted.getApplicationType()).isEqualTo(DEFAULT_APPLICATION_TYPE);
        assertThat(persisted.getJhiPrefix()).isEqualTo(DEFAULT_JHI_PREFIX);
        assertThat(persisted.isEnableTranslation()).isEqualTo(DEFAULT_ENABLE_TRANSLATION);
        assertThat(persisted.getNativeLanguage()).isEqualTo(DEFAULT_NATIVE_LANGUAGE);
        assertThat(persisted.isHasProtractor()).isEqualTo(DEFAULT_HAS_PROTRACTOR);
        assertThat(persisted.isHasGatling()).isEqualTo(DEFAULT_HAS_GATLING);
        assertThat(persisted.isHasCucumber()).isEqualTo(DEFAULT_HAS_CUCUMBER);
    }

    @Test
    @Transactional
    void cannotUpdateYoRCWithoutId() throws Exception {
        restYoRCMockMvc
            .perform(put("/api/yo-rcs").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(yoRC)))
            .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    void getAllYoRCS() throws Exception {
        // Initialize the database
        yoRCRepository.saveAndFlush(yoRC);

        // Get all the yoRCList
        restYoRCMockMvc
            .perform(get("/api/yo-rcs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
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
            .andExpect(jsonPath("$.[*].enableHibernateCache").value(hasItem(DEFAULT_ENABLE_HIBERNATE_CACHE)))
            .andExpect(jsonPath("$.[*].websocket").value(hasItem(DEFAULT_WEBSOCKET)))
            .andExpect(jsonPath("$.[*].databaseType").value(hasItem(DEFAULT_DATABASE_TYPE)))
            .andExpect(jsonPath("$.[*].devDatabaseType").value(hasItem(DEFAULT_DEV_DATABASE_TYPE)))
            .andExpect(jsonPath("$.[*].prodDatabaseType").value(hasItem(DEFAULT_PROD_DATABASE_TYPE)))
            .andExpect(jsonPath("$.[*].searchEngine").value(hasItem(DEFAULT_SEARCH_ENGINE)))
            .andExpect(jsonPath("$.[*].messageBroker").value(hasItem(DEFAULT_MESSAGE_BROKER)))
            .andExpect(jsonPath("$.[*].serviceDiscoveryType").value(hasItem(DEFAULT_SERVICE_DISCOVERY_TYPE)))
            .andExpect(jsonPath("$.[*].buildTool").value(hasItem(DEFAULT_BUILD_TOOL)))
            .andExpect(jsonPath("$.[*].enableSwaggerCodegen").value(hasItem(DEFAULT_ENABLE_SWAGGER_CODEGEN)))
            .andExpect(jsonPath("$.[*].clientFramework").value(hasItem(DEFAULT_CLIENT_FRAMEWORK)))
            .andExpect(jsonPath("$.[*].withAdminUi").value(hasItem(DEFAULT_WITH_ADMIN_UI)))
            .andExpect(jsonPath("$.[*].useSass").value(hasItem(DEFAULT_USE_SASS)))
            .andExpect(jsonPath("$.[*].clientPackageManager").value(hasItem(DEFAULT_CLIENT_PACKAGE_MANAGER)))
            .andExpect(jsonPath("$.[*].applicationType").value(hasItem(DEFAULT_APPLICATION_TYPE)))
            .andExpect(jsonPath("$.[*].jhiPrefix").value(hasItem(DEFAULT_JHI_PREFIX)))
            .andExpect(jsonPath("$.[*].enableTranslation").value(hasItem(DEFAULT_ENABLE_TRANSLATION)))
            .andExpect(jsonPath("$.[*].nativeLanguage").value(hasItem(DEFAULT_NATIVE_LANGUAGE)))
            .andExpect(jsonPath("$.[*].hasProtractor").value(hasItem(DEFAULT_HAS_PROTRACTOR)))
            .andExpect(jsonPath("$.[*].hasGatling").value(hasItem(DEFAULT_HAS_GATLING)))
            .andExpect(jsonPath("$.[*].hasCucumber").value(hasItem(DEFAULT_HAS_CUCUMBER)));
    }

    @Test
    @Transactional
    void getYoRC() throws Exception {
        // Initialize the database
        yoRCRepository.saveAndFlush(yoRC);

        // Get the yoRC
        restYoRCMockMvc
            .perform(get("/api/yo-rcs/{id}", yoRC.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
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
            .andExpect(jsonPath("$.enableHibernateCache").value(DEFAULT_ENABLE_HIBERNATE_CACHE))
            .andExpect(jsonPath("$.websocket").value(DEFAULT_WEBSOCKET))
            .andExpect(jsonPath("$.databaseType").value(DEFAULT_DATABASE_TYPE))
            .andExpect(jsonPath("$.devDatabaseType").value(DEFAULT_DEV_DATABASE_TYPE))
            .andExpect(jsonPath("$.prodDatabaseType").value(DEFAULT_PROD_DATABASE_TYPE))
            .andExpect(jsonPath("$.searchEngine").value(DEFAULT_SEARCH_ENGINE))
            .andExpect(jsonPath("$.messageBroker").value(DEFAULT_MESSAGE_BROKER))
            .andExpect(jsonPath("$.serviceDiscoveryType").value(DEFAULT_SERVICE_DISCOVERY_TYPE))
            .andExpect(jsonPath("$.buildTool").value(DEFAULT_BUILD_TOOL))
            .andExpect(jsonPath("$.enableSwaggerCodegen").value(DEFAULT_ENABLE_SWAGGER_CODEGEN))
            .andExpect(jsonPath("$.clientFramework").value(DEFAULT_CLIENT_FRAMEWORK))
            .andExpect(jsonPath("$.withAdminUi").value(DEFAULT_WITH_ADMIN_UI))
            .andExpect(jsonPath("$.useSass").value(DEFAULT_USE_SASS))
            .andExpect(jsonPath("$.clientPackageManager").value(DEFAULT_CLIENT_PACKAGE_MANAGER))
            .andExpect(jsonPath("$.applicationType").value(DEFAULT_APPLICATION_TYPE))
            .andExpect(jsonPath("$.jhiPrefix").value(DEFAULT_JHI_PREFIX))
            .andExpect(jsonPath("$.enableTranslation").value(DEFAULT_ENABLE_TRANSLATION))
            .andExpect(jsonPath("$.nativeLanguage").value(DEFAULT_NATIVE_LANGUAGE))
            .andExpect(jsonPath("$.hasProtractor").value(DEFAULT_HAS_PROTRACTOR))
            .andExpect(jsonPath("$.hasGatling").value(DEFAULT_HAS_GATLING))
            .andExpect(jsonPath("$.hasCucumber").value(DEFAULT_HAS_CUCUMBER));
    }

    @Test
    @Transactional
    void getNonExistingYoRC() throws Exception {
        // Get the yoRC
        restYoRCMockMvc.perform(get("/api/yo-rcs/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deleteYoRC() throws Exception {
        // Initialize the database
        YoRCDTO yoRCDTO = yoRCService.save(yoRCMapper.toDto(yoRC));

        int databaseSizeBeforeDelete = yoRCRepository.findAll().size();

        // Get the yoRC
        restYoRCMockMvc.perform(delete("/api/yo-rcs/{id}", yoRCDTO.getId()).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

        // Validate the database is empty
        List<YoRC> yoRCList = yoRCRepository.findAll();
        assertThat(yoRCList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(YoRC.class);
        YoRC yoRC1 = new YoRC();
        yoRC1.setId(1L);
        YoRC yoRC2 = new YoRC();
        yoRC2.setId(yoRC1.getId());
        assertThat(yoRC1).isEqualTo(yoRC2);
        yoRC2.setId(2L);
        assertThat(yoRC1).isNotEqualTo(yoRC2);
        yoRC1.setId(null);
        assertThat(yoRC1).isNotEqualTo(yoRC2);
    }
}
