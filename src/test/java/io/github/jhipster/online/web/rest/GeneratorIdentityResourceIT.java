package io.github.jhipster.online.web.rest;

import io.github.jhipster.online.JhonlineApp;
import io.github.jhipster.online.domain.GeneratorIdentity;
import io.github.jhipster.online.repository.GeneratorIdentityRepository;
import io.github.jhipster.online.service.GeneratorIdentityService;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link GeneratorIdentityResource} REST controller.
 */
@SpringBootTest(classes = JhonlineApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class GeneratorIdentityResourceIT {

    private static final String DEFAULT_HOST = "AAAAAAAAAA";
    private static final String UPDATED_HOST = "BBBBBBBBBB";

    private static final String DEFAULT_GUID = "AAAAAAAAAA";
    private static final String UPDATED_GUID = "BBBBBBBBBB";

    @Autowired
    private GeneratorIdentityRepository generatorIdentityRepository;

    @Autowired
    private GeneratorIdentityService generatorIdentityService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGeneratorIdentityMockMvc;

    private GeneratorIdentity generatorIdentity;

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
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GeneratorIdentity createUpdatedEntity(EntityManager em) {
        GeneratorIdentity generatorIdentity = new GeneratorIdentity()
            .host(UPDATED_HOST)
            .guid(UPDATED_GUID);
        return generatorIdentity;
    }

    @BeforeEach
    public void initTest() {
        generatorIdentity = createEntity(em);
    }

    @Test
    @Transactional
    public void createGeneratorIdentity() throws Exception {
        int databaseSizeBeforeCreate = generatorIdentityRepository.findAll().size();

        // Create the GeneratorIdentity
        restGeneratorIdentityMockMvc.perform(post("/api/generator-identities")
            .contentType(MediaType.APPLICATION_JSON)
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
            .contentType(MediaType.APPLICATION_JSON)
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
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(generatorIdentity.getId().intValue())))
            .andExpect(jsonPath("$.[*].host").value(hasItem(DEFAULT_HOST)))
            .andExpect(jsonPath("$.[*].guid").value(hasItem(DEFAULT_GUID)));
    }
    
    @Test
    @Transactional
    public void getGeneratorIdentity() throws Exception {
        // Initialize the database
        generatorIdentityRepository.saveAndFlush(generatorIdentity);

        // Get the generatorIdentity
        restGeneratorIdentityMockMvc.perform(get("/api/generator-identities/{id}", generatorIdentity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(generatorIdentity.getId().intValue()))
            .andExpect(jsonPath("$.host").value(DEFAULT_HOST))
            .andExpect(jsonPath("$.guid").value(DEFAULT_GUID));
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
            .contentType(MediaType.APPLICATION_JSON)
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

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGeneratorIdentityMockMvc.perform(put("/api/generator-identities")
            .contentType(MediaType.APPLICATION_JSON)
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

        // Delete the generatorIdentity
        restGeneratorIdentityMockMvc.perform(delete("/api/generator-identities/{id}", generatorIdentity.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GeneratorIdentity> generatorIdentityList = generatorIdentityRepository.findAll();
        assertThat(generatorIdentityList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
