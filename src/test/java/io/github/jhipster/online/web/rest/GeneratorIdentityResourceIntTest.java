package io.github.jhipster.online.web.rest;

import static io.github.jhipster.online.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import javax.persistence.EntityManager;

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

import io.github.jhipster.online.JhonlineApp;
import io.github.jhipster.online.domain.GeneratorIdentity;
import io.github.jhipster.online.repository.GeneratorIdentityRepository;
import io.github.jhipster.online.service.GeneratorIdentityService;
import io.github.jhipster.online.service.UserService;
import io.github.jhipster.online.web.rest.errors.ExceptionTranslator;

/**
 * Test class for the GeneratorIdentityResource REST controller.
 *
 * @see GeneratorIdentityResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhonlineApp.class)
public class GeneratorIdentityResourceIntTest {

    private static final String DEFAULT_HOST = "AAAAAAAAAA";
    private static final String UPDATED_HOST = "BBBBBBBBBB";

    private static final String DEFAULT_GUID = "AAAAAAAAAA";
    private static final String UPDATED_GUID = "BBBBBBBBBB";

    @Autowired
    private GeneratorIdentityRepository generatorIdentityRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private GeneratorIdentityService generatorIdentityService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restGeneratorIdentityMockMvc;

    private GeneratorIdentity generatorIdentity;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GeneratorIdentityResource generatorIdentityResource = new GeneratorIdentityResource(generatorIdentityService, userService);
        this.restGeneratorIdentityMockMvc = MockMvcBuilders.standaloneSetup(generatorIdentityResource)
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
    public static GeneratorIdentity createEntity(EntityManager em) {
        GeneratorIdentity generatorIdentity = new GeneratorIdentity()
            .host(DEFAULT_HOST)
            .guid(DEFAULT_GUID);
        return generatorIdentity;
    }

    @Before
    public void initTest() {
        generatorIdentity = createEntity(em);
    }

    @Test
    @Transactional
    public void createGeneratorIdentity() throws Exception {
        int databaseSizeBeforeCreate = generatorIdentityRepository.findAll().size();

        // Create the GeneratorIdentity
        restGeneratorIdentityMockMvc.perform(post("/api/generator-identities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(generatorIdentity)))
            .andExpect(status().isCreated());

        // Validate the GeneratorIdentity in the database
        List<GeneratorIdentity> generatorIdentityList = generatorIdentityRepository.findAll();
        assertThat(generatorIdentityList).hasSize(databaseSizeBeforeCreate + 1);
        GeneratorIdentity testGeneratorIdentity = generatorIdentityList.get(generatorIdentityList.size() - 1);
        assertThat(testGeneratorIdentity.getHost()).isEqualTo(DEFAULT_HOST);
        assertThat(testGeneratorIdentity.getGuid()).isEqualTo(DEFAULT_GUID);
    }

    @Test
    @Transactional
    public void createGeneratorIdentityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = generatorIdentityRepository.findAll().size();

        // Create the GeneratorIdentity with an existing ID
        generatorIdentity.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGeneratorIdentityMockMvc.perform(post("/api/generator-identities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(generatorIdentity)))
            .andExpect(status().isBadRequest());

        // Validate the GeneratorIdentity in the database
        List<GeneratorIdentity> generatorIdentityList = generatorIdentityRepository.findAll();
        assertThat(generatorIdentityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllGeneratorIdentities() throws Exception {
        // Initialize the database
        generatorIdentityRepository.saveAndFlush(generatorIdentity);

        // Get all the generatorIdentityList
        restGeneratorIdentityMockMvc.perform(get("/api/generator-identities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(generatorIdentity.getId().intValue())))
            .andExpect(jsonPath("$.[*].host").value(hasItem(DEFAULT_HOST.toString())))
            .andExpect(jsonPath("$.[*].guid").value(hasItem(DEFAULT_GUID.toString())));
    }


    @Test
    @Transactional
    public void getGeneratorIdentity() throws Exception {
        // Initialize the database
        generatorIdentityRepository.saveAndFlush(generatorIdentity);

        // Get the generatorIdentity
        restGeneratorIdentityMockMvc.perform(get("/api/generator-identities/{id}", generatorIdentity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(generatorIdentity.getId().intValue()))
            .andExpect(jsonPath("$.host").value(DEFAULT_HOST.toString()))
            .andExpect(jsonPath("$.guid").value(DEFAULT_GUID.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingGeneratorIdentity() throws Exception {
        // Get the generatorIdentity
        restGeneratorIdentityMockMvc.perform(get("/api/generator-identities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGeneratorIdentity() throws Exception {
        // Initialize the database
        generatorIdentityService.save(generatorIdentity);

        int databaseSizeBeforeUpdate = generatorIdentityRepository.findAll().size();

        // Update the generatorIdentity
        GeneratorIdentity updatedGeneratorIdentity = generatorIdentityRepository.findById(generatorIdentity.getId()).get();
        // Disconnect from session so that the updates on updatedGeneratorIdentity are not directly saved in db
        em.detach(updatedGeneratorIdentity);
        updatedGeneratorIdentity
            .host(UPDATED_HOST)
            .guid(UPDATED_GUID);

        restGeneratorIdentityMockMvc.perform(put("/api/generator-identities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedGeneratorIdentity)))
            .andExpect(status().isOk());

        // Validate the GeneratorIdentity in the database
        List<GeneratorIdentity> generatorIdentityList = generatorIdentityRepository.findAll();
        assertThat(generatorIdentityList).hasSize(databaseSizeBeforeUpdate);
        GeneratorIdentity testGeneratorIdentity = generatorIdentityList.get(generatorIdentityList.size() - 1);
        assertThat(testGeneratorIdentity.getHost()).isEqualTo(UPDATED_HOST);
        assertThat(testGeneratorIdentity.getGuid()).isEqualTo(UPDATED_GUID);
    }

    @Test
    @Transactional
    public void updateNonExistingGeneratorIdentity() throws Exception {
        int databaseSizeBeforeUpdate = generatorIdentityRepository.findAll().size();

        // Create the GeneratorIdentity

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restGeneratorIdentityMockMvc.perform(put("/api/generator-identities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(generatorIdentity)))
            .andExpect(status().isBadRequest());

        // Validate the GeneratorIdentity in the database
        List<GeneratorIdentity> generatorIdentityList = generatorIdentityRepository.findAll();
        assertThat(generatorIdentityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGeneratorIdentity() throws Exception {
        // Initialize the database
        generatorIdentityService.save(generatorIdentity);

        int databaseSizeBeforeDelete = generatorIdentityRepository.findAll().size();

        // Get the generatorIdentity
        restGeneratorIdentityMockMvc.perform(delete("/api/generator-identities/{id}", generatorIdentity.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<GeneratorIdentity> generatorIdentityList = generatorIdentityRepository.findAll();
        assertThat(generatorIdentityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GeneratorIdentity.class);
        GeneratorIdentity generatorIdentity1 = new GeneratorIdentity();
        generatorIdentity1.setId(1L);
        GeneratorIdentity generatorIdentity2 = new GeneratorIdentity();
        generatorIdentity2.setId(generatorIdentity1.getId());
        assertThat(generatorIdentity1).isEqualTo(generatorIdentity2);
        generatorIdentity2.setId(2L);
        assertThat(generatorIdentity1).isNotEqualTo(generatorIdentity2);
        generatorIdentity1.setId(null);
        assertThat(generatorIdentity1).isNotEqualTo(generatorIdentity2);
    }
}
