package io.github.jhipster.online.web.rest;

import io.github.jhipster.online.JhonlineApp;

import io.github.jhipster.online.domain.OwnerIdentity;
import io.github.jhipster.online.repository.OwnerIdentityRepository;
import io.github.jhipster.online.service.OwnerIdentityService;
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
 * Test class for the OwnerIdentityResource REST controller.
 *
 * @see OwnerIdentityResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhonlineApp.class)
public class OwnerIdentityResourceIntTest {

    @Autowired
    private OwnerIdentityRepository ownerIdentityRepository;

    

    @Autowired
    private OwnerIdentityService ownerIdentityService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOwnerIdentityMockMvc;

    private OwnerIdentity ownerIdentity;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OwnerIdentityResource ownerIdentityResource = new OwnerIdentityResource(ownerIdentityService);
        this.restOwnerIdentityMockMvc = MockMvcBuilders.standaloneSetup(ownerIdentityResource)
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
    public static OwnerIdentity createEntity(EntityManager em) {
        OwnerIdentity ownerIdentity = new OwnerIdentity();
        return ownerIdentity;
    }

    @Before
    public void initTest() {
        ownerIdentity = createEntity(em);
    }

    @Test
    @Transactional
    public void createOwnerIdentity() throws Exception {
        int databaseSizeBeforeCreate = ownerIdentityRepository.findAll().size();

        // Create the OwnerIdentity
        restOwnerIdentityMockMvc.perform(post("/api/owner-identities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ownerIdentity)))
            .andExpect(status().isCreated());

        // Validate the OwnerIdentity in the database
        List<OwnerIdentity> ownerIdentityList = ownerIdentityRepository.findAll();
        assertThat(ownerIdentityList).hasSize(databaseSizeBeforeCreate + 1);
        OwnerIdentity testOwnerIdentity = ownerIdentityList.get(ownerIdentityList.size() - 1);
    }

    @Test
    @Transactional
    public void createOwnerIdentityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ownerIdentityRepository.findAll().size();

        // Create the OwnerIdentity with an existing ID
        ownerIdentity.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOwnerIdentityMockMvc.perform(post("/api/owner-identities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ownerIdentity)))
            .andExpect(status().isBadRequest());

        // Validate the OwnerIdentity in the database
        List<OwnerIdentity> ownerIdentityList = ownerIdentityRepository.findAll();
        assertThat(ownerIdentityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllOwnerIdentities() throws Exception {
        // Initialize the database
        ownerIdentityRepository.saveAndFlush(ownerIdentity);

        // Get all the ownerIdentityList
        restOwnerIdentityMockMvc.perform(get("/api/owner-identities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ownerIdentity.getId().intValue())));
    }
    

    @Test
    @Transactional
    public void getOwnerIdentity() throws Exception {
        // Initialize the database
        ownerIdentityRepository.saveAndFlush(ownerIdentity);

        // Get the ownerIdentity
        restOwnerIdentityMockMvc.perform(get("/api/owner-identities/{id}", ownerIdentity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ownerIdentity.getId().intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingOwnerIdentity() throws Exception {
        // Get the ownerIdentity
        restOwnerIdentityMockMvc.perform(get("/api/owner-identities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOwnerIdentity() throws Exception {
        // Initialize the database
        ownerIdentityService.save(ownerIdentity);

        int databaseSizeBeforeUpdate = ownerIdentityRepository.findAll().size();

        // Update the ownerIdentity
        OwnerIdentity updatedOwnerIdentity = ownerIdentityRepository.findById(ownerIdentity.getId()).get();
        // Disconnect from session so that the updates on updatedOwnerIdentity are not directly saved in db
        em.detach(updatedOwnerIdentity);

        restOwnerIdentityMockMvc.perform(put("/api/owner-identities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOwnerIdentity)))
            .andExpect(status().isOk());

        // Validate the OwnerIdentity in the database
        List<OwnerIdentity> ownerIdentityList = ownerIdentityRepository.findAll();
        assertThat(ownerIdentityList).hasSize(databaseSizeBeforeUpdate);
        OwnerIdentity testOwnerIdentity = ownerIdentityList.get(ownerIdentityList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingOwnerIdentity() throws Exception {
        int databaseSizeBeforeUpdate = ownerIdentityRepository.findAll().size();

        // Create the OwnerIdentity

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restOwnerIdentityMockMvc.perform(put("/api/owner-identities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ownerIdentity)))
            .andExpect(status().isBadRequest());

        // Validate the OwnerIdentity in the database
        List<OwnerIdentity> ownerIdentityList = ownerIdentityRepository.findAll();
        assertThat(ownerIdentityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOwnerIdentity() throws Exception {
        // Initialize the database
        ownerIdentityService.save(ownerIdentity);

        int databaseSizeBeforeDelete = ownerIdentityRepository.findAll().size();

        // Get the ownerIdentity
        restOwnerIdentityMockMvc.perform(delete("/api/owner-identities/{id}", ownerIdentity.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<OwnerIdentity> ownerIdentityList = ownerIdentityRepository.findAll();
        assertThat(ownerIdentityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OwnerIdentity.class);
        OwnerIdentity ownerIdentity1 = new OwnerIdentity();
        ownerIdentity1.setId(1L);
        OwnerIdentity ownerIdentity2 = new OwnerIdentity();
        ownerIdentity2.setId(ownerIdentity1.getId());
        assertThat(ownerIdentity1).isEqualTo(ownerIdentity2);
        ownerIdentity2.setId(2L);
        assertThat(ownerIdentity1).isNotEqualTo(ownerIdentity2);
        ownerIdentity1.setId(null);
        assertThat(ownerIdentity1).isNotEqualTo(ownerIdentity2);
    }
}
