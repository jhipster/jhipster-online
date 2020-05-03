package io.github.jhipster.online.web.rest;

import io.github.jhipster.online.JhonlineApp;

import io.github.jhipster.online.domain.JdlMetadata;
import io.github.jhipster.online.domain.User;
import io.github.jhipster.online.repository.JdlMetadataRepository;
import io.github.jhipster.online.service.JdlMetadataService;
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
import java.util.List;


import static io.github.jhipster.online.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the JdlMetadataResource REST controller.
 *
 * @see JdlMetadataResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhonlineApp.class)
public class JdlMetadataResourceIntTest {

    private static final Long DEFAULT_KEY = 1L;
    private static final Long UPDATED_KEY = 2L;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_PUBLIC = false;
    private static final Boolean UPDATED_IS_PUBLIC = true;

    @Autowired
    private JdlMetadataRepository jdlMetadataRepository;

    

    @Autowired
    private JdlMetadataService jdlMetadataService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restJdlMetadataMockMvc;

    private JdlMetadata jdlMetadata;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final JdlMetadataResource jdlMetadataResource = new JdlMetadataResource(jdlMetadataService);
        this.restJdlMetadataMockMvc = MockMvcBuilders.standaloneSetup(jdlMetadataResource)
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
    public static JdlMetadata createEntity(EntityManager em) {
        JdlMetadata jdlMetadata = new JdlMetadata()
            .key(DEFAULT_KEY)
            .name(DEFAULT_NAME)
            .isPublic(DEFAULT_IS_PUBLIC);
        // Add required entity
        User user = UserResourceIntTest.createEntity(em);
        em.persist(user);
        em.flush();
        jdlMetadata.setUser(user);
        return jdlMetadata;
    }

    @Before
    public void initTest() {
        jdlMetadata = createEntity(em);
    }

    @Test
    @Transactional
    public void createJdlMetadata() throws Exception {
        int databaseSizeBeforeCreate = jdlMetadataRepository.findAll().size();

        // Create the JdlMetadata
        restJdlMetadataMockMvc.perform(post("/api/jdl-metadata")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jdlMetadata)))
            .andExpect(status().isCreated());

        // Validate the JdlMetadata in the database
        List<JdlMetadata> jdlMetadataList = jdlMetadataRepository.findAll();
        assertThat(jdlMetadataList).hasSize(databaseSizeBeforeCreate + 1);
        JdlMetadata testJdlMetadata = jdlMetadataList.get(jdlMetadataList.size() - 1);
        assertThat(testJdlMetadata.getKey()).isEqualTo(DEFAULT_KEY);
        assertThat(testJdlMetadata.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testJdlMetadata.isIsPublic()).isEqualTo(DEFAULT_IS_PUBLIC);
    }

    @Test
    @Transactional
    public void createJdlMetadataWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = jdlMetadataRepository.findAll().size();

        // Create the JdlMetadata with an existing ID
        jdlMetadata.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restJdlMetadataMockMvc.perform(post("/api/jdl-metadata")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jdlMetadata)))
            .andExpect(status().isBadRequest());

        // Validate the JdlMetadata in the database
        List<JdlMetadata> jdlMetadataList = jdlMetadataRepository.findAll();
        assertThat(jdlMetadataList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkKeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = jdlMetadataRepository.findAll().size();
        // set the field null
        jdlMetadata.setKey(null);

        // Create the JdlMetadata, which fails.

        restJdlMetadataMockMvc.perform(post("/api/jdl-metadata")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jdlMetadata)))
            .andExpect(status().isBadRequest());

        List<JdlMetadata> jdlMetadataList = jdlMetadataRepository.findAll();
        assertThat(jdlMetadataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllJdlMetadata() throws Exception {
        // Initialize the database
        jdlMetadataRepository.saveAndFlush(jdlMetadata);

        // Get all the jdlMetadataList
        restJdlMetadataMockMvc.perform(get("/api/jdl-metadata?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jdlMetadata.getId().intValue())))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY.intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].isPublic").value(hasItem(DEFAULT_IS_PUBLIC.booleanValue())));
    }
    

    @Test
    @Transactional
    public void getJdlMetadata() throws Exception {
        // Initialize the database
        jdlMetadataRepository.saveAndFlush(jdlMetadata);

        // Get the jdlMetadata
        restJdlMetadataMockMvc.perform(get("/api/jdl-metadata/{id}", jdlMetadata.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(jdlMetadata.getId().intValue()))
            .andExpect(jsonPath("$.key").value(DEFAULT_KEY.intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.isPublic").value(DEFAULT_IS_PUBLIC.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingJdlMetadata() throws Exception {
        // Get the jdlMetadata
        restJdlMetadataMockMvc.perform(get("/api/jdl-metadata/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJdlMetadata() throws Exception {
        // Initialize the database
        jdlMetadataService.save(jdlMetadata);

        int databaseSizeBeforeUpdate = jdlMetadataRepository.findAll().size();

        // Update the jdlMetadata
        JdlMetadata updatedJdlMetadata = jdlMetadataRepository.findById(jdlMetadata.getId()).get();
        // Disconnect from session so that the updates on updatedJdlMetadata are not directly saved in db
        em.detach(updatedJdlMetadata);
        updatedJdlMetadata
            .key(UPDATED_KEY)
            .name(UPDATED_NAME)
            .isPublic(UPDATED_IS_PUBLIC);

        restJdlMetadataMockMvc.perform(put("/api/jdl-metadata")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedJdlMetadata)))
            .andExpect(status().isOk());

        // Validate the JdlMetadata in the database
        List<JdlMetadata> jdlMetadataList = jdlMetadataRepository.findAll();
        assertThat(jdlMetadataList).hasSize(databaseSizeBeforeUpdate);
        JdlMetadata testJdlMetadata = jdlMetadataList.get(jdlMetadataList.size() - 1);
        assertThat(testJdlMetadata.getKey()).isEqualTo(UPDATED_KEY);
        assertThat(testJdlMetadata.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testJdlMetadata.isIsPublic()).isEqualTo(UPDATED_IS_PUBLIC);
    }

    @Test
    @Transactional
    public void updateNonExistingJdlMetadata() throws Exception {
        int databaseSizeBeforeUpdate = jdlMetadataRepository.findAll().size();

        // Create the JdlMetadata

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restJdlMetadataMockMvc.perform(put("/api/jdl-metadata")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jdlMetadata)))
            .andExpect(status().isBadRequest());

        // Validate the JdlMetadata in the database
        List<JdlMetadata> jdlMetadataList = jdlMetadataRepository.findAll();
        assertThat(jdlMetadataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteJdlMetadata() throws Exception {
        // Initialize the database
        jdlMetadataService.save(jdlMetadata);

        int databaseSizeBeforeDelete = jdlMetadataRepository.findAll().size();

        // Get the jdlMetadata
        restJdlMetadataMockMvc.perform(delete("/api/jdl-metadata/{id}", jdlMetadata.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<JdlMetadata> jdlMetadataList = jdlMetadataRepository.findAll();
        assertThat(jdlMetadataList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(JdlMetadata.class);
        JdlMetadata jdlMetadata1 = new JdlMetadata();
        jdlMetadata1.setId(1L);
        JdlMetadata jdlMetadata2 = new JdlMetadata();
        jdlMetadata2.setId(jdlMetadata1.getId());
        assertThat(jdlMetadata1).isEqualTo(jdlMetadata2);
        jdlMetadata2.setId(2L);
        assertThat(jdlMetadata1).isNotEqualTo(jdlMetadata2);
        jdlMetadata1.setId(null);
        assertThat(jdlMetadata1).isNotEqualTo(jdlMetadata2);
    }
}
