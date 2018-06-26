package io.github.jhipster.online.web.rest;

import io.github.jhipster.online.JhonlineApp;

import io.github.jhipster.online.domain.CrashReport;
import io.github.jhipster.online.repository.CrashReportRepository;
import io.github.jhipster.online.service.CrashReportService;
import io.github.jhipster.online.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;


import static io.github.jhipster.online.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CrashReportResource REST controller.
 *
 * @see CrashReportResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhonlineApp.class)
public class CrashReportResourceIntTest {

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_SOURCE = "AAAAAAAAAA";
    private static final String UPDATED_SOURCE = "BBBBBBBBBB";

    private static final String DEFAULT_ENV = "AAAAAAAAAA";
    private static final String UPDATED_ENV = "BBBBBBBBBB";

    private static final String DEFAULT_STACK = "AAAAAAAAAA";
    private static final String UPDATED_STACK = "BBBBBBBBBB";

    private static final String DEFAULT_YORC = "AAAAAAAAAA";
    private static final String UPDATED_YORC = "BBBBBBBBBB";

    private static final String DEFAULT_JDL = "AAAAAAAAAA";
    private static final String UPDATED_JDL = "BBBBBBBBBB";

    @Autowired
    private CrashReportRepository crashReportRepository;

    

    @Autowired
    private CrashReportService crashReportService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCrashReportMockMvc;

    private CrashReport crashReport;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CrashReportResource crashReportResource = new CrashReportResource(crashReportService);
        this.restCrashReportMockMvc = MockMvcBuilders.standaloneSetup(crashReportResource)
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
    public static CrashReport createEntity(EntityManager em) {
        CrashReport crashReport = new CrashReport()
            .date(DEFAULT_DATE)
            .source(DEFAULT_SOURCE)
            .env(DEFAULT_ENV)
            .stack(DEFAULT_STACK)
            .yorc(DEFAULT_YORC)
            .jdl(DEFAULT_JDL);
        return crashReport;
    }

    @Before
    public void initTest() {
        crashReport = createEntity(em);
    }

    @Test
    @Transactional
    public void createCrashReport() throws Exception {
        int databaseSizeBeforeCreate = crashReportRepository.findAll().size();

        // Create the CrashReport
        restCrashReportMockMvc.perform(post("/api/crash-reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(crashReport)))
            .andExpect(status().isCreated());

        // Validate the CrashReport in the database
        List<CrashReport> crashReportList = crashReportRepository.findAll();
        assertThat(crashReportList).hasSize(databaseSizeBeforeCreate + 1);
        CrashReport testCrashReport = crashReportList.get(crashReportList.size() - 1);
        assertThat(testCrashReport.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testCrashReport.getSource()).isEqualTo(DEFAULT_SOURCE);
        assertThat(testCrashReport.getEnv()).isEqualTo(DEFAULT_ENV);
        assertThat(testCrashReport.getStack()).isEqualTo(DEFAULT_STACK);
        assertThat(testCrashReport.getYorc()).isEqualTo(DEFAULT_YORC);
        assertThat(testCrashReport.getJdl()).isEqualTo(DEFAULT_JDL);
    }

    @Test
    @Transactional
    public void createCrashReportWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = crashReportRepository.findAll().size();

        // Create the CrashReport with an existing ID
        crashReport.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCrashReportMockMvc.perform(post("/api/crash-reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(crashReport)))
            .andExpect(status().isBadRequest());

        // Validate the CrashReport in the database
        List<CrashReport> crashReportList = crashReportRepository.findAll();
        assertThat(crashReportList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCrashReports() throws Exception {
        // Initialize the database
        crashReportRepository.saveAndFlush(crashReport);

        // Get all the crashReportList
        restCrashReportMockMvc.perform(get("/api/crash-reports?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(crashReport.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE.toString())))
            .andExpect(jsonPath("$.[*].env").value(hasItem(DEFAULT_ENV.toString())))
            .andExpect(jsonPath("$.[*].stack").value(hasItem(DEFAULT_STACK.toString())))
            .andExpect(jsonPath("$.[*].yorc").value(hasItem(DEFAULT_YORC.toString())))
            .andExpect(jsonPath("$.[*].jdl").value(hasItem(DEFAULT_JDL.toString())));
    }
    

    @Test
    @Transactional
    public void getCrashReport() throws Exception {
        // Initialize the database
        crashReportRepository.saveAndFlush(crashReport);

        // Get the crashReport
        restCrashReportMockMvc.perform(get("/api/crash-reports/{id}", crashReport.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(crashReport.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.source").value(DEFAULT_SOURCE.toString()))
            .andExpect(jsonPath("$.env").value(DEFAULT_ENV.toString()))
            .andExpect(jsonPath("$.stack").value(DEFAULT_STACK.toString()))
            .andExpect(jsonPath("$.yorc").value(DEFAULT_YORC.toString()))
            .andExpect(jsonPath("$.jdl").value(DEFAULT_JDL.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingCrashReport() throws Exception {
        // Get the crashReport
        restCrashReportMockMvc.perform(get("/api/crash-reports/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCrashReport() throws Exception {
        // Initialize the database
        crashReportService.save(crashReport);

        int databaseSizeBeforeUpdate = crashReportRepository.findAll().size();

        // Update the crashReport
        CrashReport updatedCrashReport = crashReportRepository.findById(crashReport.getId()).get();
        // Disconnect from session so that the updates on updatedCrashReport are not directly saved in db
        em.detach(updatedCrashReport);
        updatedCrashReport
            .date(UPDATED_DATE)
            .source(UPDATED_SOURCE)
            .env(UPDATED_ENV)
            .stack(UPDATED_STACK)
            .yorc(UPDATED_YORC)
            .jdl(UPDATED_JDL);

        restCrashReportMockMvc.perform(put("/api/crash-reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCrashReport)))
            .andExpect(status().isOk());

        // Validate the CrashReport in the database
        List<CrashReport> crashReportList = crashReportRepository.findAll();
        assertThat(crashReportList).hasSize(databaseSizeBeforeUpdate);
        CrashReport testCrashReport = crashReportList.get(crashReportList.size() - 1);
        assertThat(testCrashReport.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testCrashReport.getSource()).isEqualTo(UPDATED_SOURCE);
        assertThat(testCrashReport.getEnv()).isEqualTo(UPDATED_ENV);
        assertThat(testCrashReport.getStack()).isEqualTo(UPDATED_STACK);
        assertThat(testCrashReport.getYorc()).isEqualTo(UPDATED_YORC);
        assertThat(testCrashReport.getJdl()).isEqualTo(UPDATED_JDL);
    }

    @Test
    @Transactional
    public void updateNonExistingCrashReport() throws Exception {
        int databaseSizeBeforeUpdate = crashReportRepository.findAll().size();

        // Create the CrashReport

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCrashReportMockMvc.perform(put("/api/crash-reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(crashReport)))
            .andExpect(status().isBadRequest());

        // Validate the CrashReport in the database
        List<CrashReport> crashReportList = crashReportRepository.findAll();
        assertThat(crashReportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCrashReport() throws Exception {
        // Initialize the database
        crashReportService.save(crashReport);

        int databaseSizeBeforeDelete = crashReportRepository.findAll().size();

        // Get the crashReport
        restCrashReportMockMvc.perform(delete("/api/crash-reports/{id}", crashReport.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CrashReport> crashReportList = crashReportRepository.findAll();
        assertThat(crashReportList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CrashReport.class);
        CrashReport crashReport1 = new CrashReport();
        crashReport1.setId(1L);
        CrashReport crashReport2 = new CrashReport();
        crashReport2.setId(crashReport1.getId());
        assertThat(crashReport1).isEqualTo(crashReport2);
        crashReport2.setId(2L);
        assertThat(crashReport1).isNotEqualTo(crashReport2);
        crashReport1.setId(null);
        assertThat(crashReport1).isNotEqualTo(crashReport2);
    }
}
