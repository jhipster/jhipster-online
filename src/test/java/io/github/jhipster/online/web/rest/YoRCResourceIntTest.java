package io.github.jhipster.online.web.rest;

import io.github.jhipster.online.JhonlineApp;

import io.github.jhipster.online.domain.YoRC;
import io.github.jhipster.online.repository.YoRCRepository;
import io.github.jhipster.online.service.YoRCService;
import io.github.jhipster.online.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.ArrayList;

import static io.github.jhipster.online.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the YoRCResource REST controller.
 *
 * @see YoRCResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhonlineApp.class)
public class YoRCResourceIntTest {

    private static final String DEFAULT_JHIPSTER_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_JHIPSTER_VERSION = "BBBBBBBBBB";

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

    private static final String DEFAULT_GIT_PROVIDER = "AAAAAAAAAA";
    private static final String UPDATED_GIT_PROVIDER = "BBBBBBBBBB";

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
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restYoRCMockMvc;

    private YoRC yoRC;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final YoRCResource yoRCResource = new YoRCResource(yoRCService);
        this.restYoRCMockMvc = MockMvcBuilders.standaloneSetup(yoRCResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static YoRC createEntity(EntityManager em) {
        YoRC yoRC = new YoRC()
            .jhipsterVersion(DEFAULT_JHIPSTER_VERSION)
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
            .gitProvider(DEFAULT_GIT_PROVIDER)
            .hasProtractor(DEFAULT_HAS_PROTRACTOR)
            .hasGatling(DEFAULT_HAS_GATLING)
            .hasCucumber(DEFAULT_HAS_CUCUMBER);
        return yoRC;
    }

    @Before
    public void initTest() {
        yoRC = createEntity(em);
    }

    @Test
    @Transactional
    public void createYoRC() throws Exception {
        int databaseSizeBeforeCreate = yoRCRepository.findAll().size();

        // Create the YoRC
        restYoRCMockMvc.perform(post("/api/yo-rcs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(yoRC)))
            .andExpect(status().isCreated());

        // Validate the YoRC in the database
        List<YoRC> yoRCList = yoRCRepository.findAll();
        assertThat(yoRCList).hasSize(databaseSizeBeforeCreate + 1);
        YoRC testYoRC = yoRCList.get(yoRCList.size() - 1);
        assertThat(testYoRC.getJhipsterVersion()).isEqualTo(DEFAULT_JHIPSTER_VERSION);
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
        assertThat(testYoRC.getGitProvider()).isEqualTo(DEFAULT_GIT_PROVIDER);
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
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
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
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(yoRC.getId().intValue())))
            .andExpect(jsonPath("$.[*].jhipsterVersion").value(hasItem(DEFAULT_JHIPSTER_VERSION.toString())))
            .andExpect(jsonPath("$.[*].serverPort").value(hasItem(DEFAULT_SERVER_PORT.toString())))
            .andExpect(jsonPath("$.[*].authenticationType").value(hasItem(DEFAULT_AUTHENTICATION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].cacheProvider").value(hasItem(DEFAULT_CACHE_PROVIDER.toString())))
            .andExpect(jsonPath("$.[*].enableHibernateCache").value(hasItem(DEFAULT_ENABLE_HIBERNATE_CACHE.booleanValue())))
            .andExpect(jsonPath("$.[*].websocket").value(hasItem(DEFAULT_WEBSOCKET.booleanValue())))
            .andExpect(jsonPath("$.[*].databaseType").value(hasItem(DEFAULT_DATABASE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].devDatabaseType").value(hasItem(DEFAULT_DEV_DATABASE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].prodDatabaseType").value(hasItem(DEFAULT_PROD_DATABASE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].searchEngine").value(hasItem(DEFAULT_SEARCH_ENGINE.booleanValue())))
            .andExpect(jsonPath("$.[*].messageBroker").value(hasItem(DEFAULT_MESSAGE_BROKER.booleanValue())))
            .andExpect(jsonPath("$.[*].serviceDiscoveryType").value(hasItem(DEFAULT_SERVICE_DISCOVERY_TYPE.booleanValue())))
            .andExpect(jsonPath("$.[*].buildTool").value(hasItem(DEFAULT_BUILD_TOOL.toString())))
            .andExpect(jsonPath("$.[*].enableSwaggerCodegen").value(hasItem(DEFAULT_ENABLE_SWAGGER_CODEGEN.booleanValue())))
            .andExpect(jsonPath("$.[*].clientFramework").value(hasItem(DEFAULT_CLIENT_FRAMEWORK.toString())))
            .andExpect(jsonPath("$.[*].useSass").value(hasItem(DEFAULT_USE_SASS.booleanValue())))
            .andExpect(jsonPath("$.[*].clientPackageManager").value(hasItem(DEFAULT_CLIENT_PACKAGE_MANAGER.toString())))
            .andExpect(jsonPath("$.[*].applicationType").value(hasItem(DEFAULT_APPLICATION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].jhiPrefix").value(hasItem(DEFAULT_JHI_PREFIX.toString())))
            .andExpect(jsonPath("$.[*].enableTranslation").value(hasItem(DEFAULT_ENABLE_TRANSLATION.booleanValue())))
            .andExpect(jsonPath("$.[*].nativeLanguage").value(hasItem(DEFAULT_NATIVE_LANGUAGE.toString())))
            .andExpect(jsonPath("$.[*].gitProvider").value(hasItem(DEFAULT_GIT_PROVIDER.toString())))
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
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(yoRC.getId().intValue()))
            .andExpect(jsonPath("$.jhipsterVersion").value(DEFAULT_JHIPSTER_VERSION.toString()))
            .andExpect(jsonPath("$.serverPort").value(DEFAULT_SERVER_PORT.toString()))
            .andExpect(jsonPath("$.authenticationType").value(DEFAULT_AUTHENTICATION_TYPE.toString()))
            .andExpect(jsonPath("$.cacheProvider").value(DEFAULT_CACHE_PROVIDER.toString()))
            .andExpect(jsonPath("$.enableHibernateCache").value(DEFAULT_ENABLE_HIBERNATE_CACHE.booleanValue()))
            .andExpect(jsonPath("$.websocket").value(DEFAULT_WEBSOCKET.booleanValue()))
            .andExpect(jsonPath("$.databaseType").value(DEFAULT_DATABASE_TYPE.toString()))
            .andExpect(jsonPath("$.devDatabaseType").value(DEFAULT_DEV_DATABASE_TYPE.toString()))
            .andExpect(jsonPath("$.prodDatabaseType").value(DEFAULT_PROD_DATABASE_TYPE.toString()))
            .andExpect(jsonPath("$.searchEngine").value(DEFAULT_SEARCH_ENGINE.booleanValue()))
            .andExpect(jsonPath("$.messageBroker").value(DEFAULT_MESSAGE_BROKER.booleanValue()))
            .andExpect(jsonPath("$.serviceDiscoveryType").value(DEFAULT_SERVICE_DISCOVERY_TYPE.booleanValue()))
            .andExpect(jsonPath("$.buildTool").value(DEFAULT_BUILD_TOOL.toString()))
            .andExpect(jsonPath("$.enableSwaggerCodegen").value(DEFAULT_ENABLE_SWAGGER_CODEGEN.booleanValue()))
            .andExpect(jsonPath("$.clientFramework").value(DEFAULT_CLIENT_FRAMEWORK.toString()))
            .andExpect(jsonPath("$.useSass").value(DEFAULT_USE_SASS.booleanValue()))
            .andExpect(jsonPath("$.clientPackageManager").value(DEFAULT_CLIENT_PACKAGE_MANAGER.toString()))
            .andExpect(jsonPath("$.applicationType").value(DEFAULT_APPLICATION_TYPE.toString()))
            .andExpect(jsonPath("$.jhiPrefix").value(DEFAULT_JHI_PREFIX.toString()))
            .andExpect(jsonPath("$.enableTranslation").value(DEFAULT_ENABLE_TRANSLATION.booleanValue()))
            .andExpect(jsonPath("$.nativeLanguage").value(DEFAULT_NATIVE_LANGUAGE.toString()))
            .andExpect(jsonPath("$.gitProvider").value(DEFAULT_GIT_PROVIDER.toString()))
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
            .gitProvider(UPDATED_GIT_PROVIDER)
            .hasProtractor(UPDATED_HAS_PROTRACTOR)
            .hasGatling(UPDATED_HAS_GATLING)
            .hasCucumber(UPDATED_HAS_CUCUMBER);

        restYoRCMockMvc.perform(put("/api/yo-rcs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedYoRC)))
            .andExpect(status().isOk());

        // Validate the YoRC in the database
        List<YoRC> yoRCList = yoRCRepository.findAll();
        assertThat(yoRCList).hasSize(databaseSizeBeforeUpdate);
        YoRC testYoRC = yoRCList.get(yoRCList.size() - 1);
        assertThat(testYoRC.getJhipsterVersion()).isEqualTo(UPDATED_JHIPSTER_VERSION);
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
        assertThat(testYoRC.getGitProvider()).isEqualTo(UPDATED_GIT_PROVIDER);
        assertThat(testYoRC.isHasProtractor()).isEqualTo(UPDATED_HAS_PROTRACTOR);
        assertThat(testYoRC.isHasGatling()).isEqualTo(UPDATED_HAS_GATLING);
        assertThat(testYoRC.isHasCucumber()).isEqualTo(UPDATED_HAS_CUCUMBER);
    }

    @Test
    @Transactional
    public void updateNonExistingYoRC() throws Exception {
        int databaseSizeBeforeUpdate = yoRCRepository.findAll().size();

        // Create the YoRC

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restYoRCMockMvc.perform(put("/api/yo-rcs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
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

        // Get the yoRC
        restYoRCMockMvc.perform(delete("/api/yo-rcs/{id}", yoRC.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<YoRC> yoRCList = yoRCRepository.findAll();
        assertThat(yoRCList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
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
