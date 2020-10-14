package io.github.jhipster.online.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import io.github.jhipster.online.JhonlineApp;
import io.github.jhipster.online.domain.GeneratorIdentity;
import io.github.jhipster.online.domain.YoRC;
import io.github.jhipster.online.repository.GeneratorIdentityRepository;
import io.github.jhipster.online.repository.YoRCRepository;
import io.github.jhipster.online.service.*;
import io.github.jhipster.online.web.rest.errors.ExceptionTranslator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = JhonlineApp.class)
class StatisticsResourceIntTest {

    @Autowired
    private YoRCService yoRCService;

    @Autowired
    private YoRCRepository yoRCRepository;

    @Autowired
    private JdlService jdlService;

    @Autowired
    private SubGenEventService subGenEventService;

    @Autowired
    private UserService userService;

    @Autowired
    private GeneratorIdentityService generatorIdentityService;

    @Autowired
    private GeneratorIdentityRepository generatorIdentityRepository;

    @Autowired
    private EntityStatsService entityStatService;

    @Autowired
    private StatisticsService statisticsService;

    @Autowired
    private HttpMessageConverter<?>[] httpMessageConverters;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restStatiticsMockMvc;

    private YoRC yoRC;

    private String generatorId = "cf51ff78-187a-4554-9b09-8f6f95f1a7a5";

    private String dummyYo =
        "{\n" +
        "        \"generator-jhipster\": {\n" +
        "        \"useYarn\": false,\n" +
        "            \"experimental\": false,\n" +
        "            \"skipI18nQuestion\": true,\n" +
        "            \"logo\": false,\n" +
        "            \"clientPackageManager\": \"npm\",\n" +
        "            \"cacheProvider\": \"ehcache\",\n" +
        "            \"enableHibernateCache\": true,\n" +
        "            \"websocket\": false,\n" +
        "            \"databaseType\": \"sql\",\n" +
        "            \"devDatabaseType\": \"h2Disk\",\n" +
        "            \"prodDatabaseType\": \"mysql\",\n" +
        "            \"searchEngine\": false,\n" +
        "            \"messageBroker\": false,\n" +
        "            \"serviceDiscoveryType\": false,\n" +
        "            \"buildTool\": \"maven\",\n" +
        "            \"enableSwaggerCodegen\": false,\n" +
        "            \"authenticationType\": \"jwt\",\n" +
        "            \"serverPort\": \"8080\",\n" +
        "            \"clientFramework\": \"angularX\",\n" +
        "            \"useSass\": false,\n" +
        "            \"testFrameworks\": [],\n" +
        "        \"enableTranslation\": true,\n" +
        "            \"nativeLanguage\": \"en\",\n" +
        "            \"languages\": [\n" +
        "        \"en\"\n" +
        "      ],\n" +
        "        \"applicationType\": \"monolith\"\n" +
        "    },\n" +
        "        \"generator-id\": \"" +
        generatorId +
        "\",\n" +
        "        \"generator-version\": \"5.1.0\",\n" +
        "        \"git-provider\": \"local\",\n" +
        "        \"node-version\": \"v8.11.1\",\n" +
        "        \"os\": \"linux:4.15.0-29-generic\",\n" +
        "        \"arch\": \"x64\",\n" +
        "        \"cpu\": \"Intel(R) Core(TM) i7-7700K CPU @ 4.20GHz\",\n" +
        "        \"cores\": 8,\n" +
        "        \"memory\": 16776642560,\n" +
        "        \"user-language\": \"en_GB\",\n" +
        "        \"isARegeneration\": true\n" +
        "    }";

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StatisticsResource statisticsResource = new StatisticsResource(
            statisticsService,
            yoRCService,
            jdlService,
            subGenEventService,
            userService,
            generatorIdentityService,
            entityStatService
        );

        this.restStatiticsMockMvc =
            MockMvcBuilders
                .standaloneSetup(statisticsResource)
                .setMessageConverters(httpMessageConverters)
                .setControllerAdvice(exceptionTranslator)
                .build();
    }

    @Test
    @Transactional
    void shouldNotGetCountWithUnknownFrequency() throws Exception {
        restStatiticsMockMvc.perform(get("/api/s/count-yo/{frequency}", "every minutes")).andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    void shouldNotGetFieldCountWithUnknownFieldOrFrequency() throws Exception {
        restStatiticsMockMvc
            .perform(get("/api/s/yo/{field}/{frequency}", "clientFramework", "every minutes"))
            .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    void getYoCount() throws Exception {
        int databaseSizeBeforeAdd = yoRCRepository.findAll().size();

        restStatiticsMockMvc.perform(get("/api/s/count-yo")).andExpect(status().isOk());

        YoRC yorc = new YoRC().owner(new GeneratorIdentity());
        generatorIdentityRepository.saveAndFlush(yorc.getOwner());
        yoRCRepository.saveAndFlush(yorc);

        int databaseSizeAfterAdd = yoRCRepository.findAll().size();

        assertThat(databaseSizeAfterAdd).isEqualTo(databaseSizeBeforeAdd + 1);
    }

    @Test
    @Transactional
    void addEntry() throws Exception {
        int databaseSizeBeforeAdd = yoRCRepository.findAll().size();

        restStatiticsMockMvc.perform(post("/api/s/entry").content(dummyYo)).andExpect(status().isCreated());

        int databaseSizeAfterAdd = yoRCRepository.findAll().size();

        assertThat(databaseSizeAfterAdd).isEqualTo(databaseSizeBeforeAdd + 1);
    }
}
