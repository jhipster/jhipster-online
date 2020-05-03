package io.github.jhipster.online.web.rest;

import io.github.jhipster.online.JhonlineApp;

import io.github.jhipster.online.domain.UserApplication;
import io.github.jhipster.online.repository.UserApplicationRepository;
import io.github.jhipster.online.service.UserApplicationService;
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
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.util.List;


import static io.github.jhipster.online.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the UserApplicationResource REST controller.
 *
 * @see UserApplicationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhonlineApp.class)
public class UserApplicationResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_SHARED = false;
    private static final Boolean UPDATED_SHARED = true;

    private static final String DEFAULT_SHARED_LINK = "AAAAAAAAAA";
    private static final String UPDATED_SHARED_LINK = "BBBBBBBBBB";

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_CONFIGURATION = "AAAAAAAAAA";
    private static final String UPDATED_CONFIGURATION = "BBBBBBBBBB";

    @Autowired
    private UserApplicationRepository userApplicationRepository;

    

    @Autowired
    private UserApplicationService userApplicationService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUserApplicationMockMvc;

    private UserApplication userApplication;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UserApplicationResource userApplicationResource = new UserApplicationResource(userApplicationService);
        this.restUserApplicationMockMvc = MockMvcBuilders.standaloneSetup(userApplicationResource)
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
    public static UserApplication createEntity(EntityManager em) {
        UserApplication userApplication = new UserApplication()
            .name(DEFAULT_NAME)
            .shared(DEFAULT_SHARED)
            .sharedLink(DEFAULT_SHARED_LINK)
            .userId(DEFAULT_USER_ID)
            .configuration(DEFAULT_CONFIGURATION);
        return userApplication;
    }

    @Before
    public void initTest() {
        userApplication = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserApplication() throws Exception {
        int databaseSizeBeforeCreate = userApplicationRepository.findAll().size();

        // Create the UserApplication
        restUserApplicationMockMvc.perform(post("/api/user-applications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userApplication)))
            .andExpect(status().isCreated());

        // Validate the UserApplication in the database
        List<UserApplication> userApplicationList = userApplicationRepository.findAll();
        assertThat(userApplicationList).hasSize(databaseSizeBeforeCreate + 1);
        UserApplication testUserApplication = userApplicationList.get(userApplicationList.size() - 1);
        assertThat(testUserApplication.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testUserApplication.isShared()).isEqualTo(DEFAULT_SHARED);
        assertThat(testUserApplication.getSharedLink()).isEqualTo(DEFAULT_SHARED_LINK);
        assertThat(testUserApplication.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testUserApplication.getConfiguration()).isEqualTo(DEFAULT_CONFIGURATION);
    }

    @Test
    @Transactional
    public void createUserApplicationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userApplicationRepository.findAll().size();

        // Create the UserApplication with an existing ID
        userApplication.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserApplicationMockMvc.perform(post("/api/user-applications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userApplication)))
            .andExpect(status().isBadRequest());

        // Validate the UserApplication in the database
        List<UserApplication> userApplicationList = userApplicationRepository.findAll();
        assertThat(userApplicationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllUserApplications() throws Exception {
        // Initialize the database
        userApplicationRepository.saveAndFlush(userApplication);

        // Get all the userApplicationList
        restUserApplicationMockMvc.perform(get("/api/user-applications?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userApplication.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].shared").value(hasItem(DEFAULT_SHARED.booleanValue())))
            .andExpect(jsonPath("$.[*].sharedLink").value(hasItem(DEFAULT_SHARED_LINK.toString())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.toString())))
            .andExpect(jsonPath("$.[*].configuration").value(hasItem(DEFAULT_CONFIGURATION.toString())));
    }
    

    @Test
    @Transactional
    public void getUserApplication() throws Exception {
        // Initialize the database
        userApplicationRepository.saveAndFlush(userApplication);

        // Get the userApplication
        restUserApplicationMockMvc.perform(get("/api/user-applications/{id}", userApplication.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userApplication.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.shared").value(DEFAULT_SHARED.booleanValue()))
            .andExpect(jsonPath("$.sharedLink").value(DEFAULT_SHARED_LINK.toString()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.toString()))
            .andExpect(jsonPath("$.configuration").value(DEFAULT_CONFIGURATION.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingUserApplication() throws Exception {
        // Get the userApplication
        restUserApplicationMockMvc.perform(get("/api/user-applications/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserApplication() throws Exception {
        // Initialize the database
        userApplicationService.save(userApplication);

        int databaseSizeBeforeUpdate = userApplicationRepository.findAll().size();

        // Update the userApplication
        UserApplication updatedUserApplication = userApplicationRepository.findById(userApplication.getId()).get();
        // Disconnect from session so that the updates on updatedUserApplication are not directly saved in db
        em.detach(updatedUserApplication);
        updatedUserApplication
            .name(UPDATED_NAME)
            .shared(UPDATED_SHARED)
            .sharedLink(UPDATED_SHARED_LINK)
            .userId(UPDATED_USER_ID)
            .configuration(UPDATED_CONFIGURATION);

        restUserApplicationMockMvc.perform(put("/api/user-applications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUserApplication)))
            .andExpect(status().isOk());

        // Validate the UserApplication in the database
        List<UserApplication> userApplicationList = userApplicationRepository.findAll();
        assertThat(userApplicationList).hasSize(databaseSizeBeforeUpdate);
        UserApplication testUserApplication = userApplicationList.get(userApplicationList.size() - 1);
        assertThat(testUserApplication.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testUserApplication.isShared()).isEqualTo(UPDATED_SHARED);
        assertThat(testUserApplication.getSharedLink()).isEqualTo(UPDATED_SHARED_LINK);
        assertThat(testUserApplication.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testUserApplication.getConfiguration()).isEqualTo(UPDATED_CONFIGURATION);
    }

    @Test
    @Transactional
    public void updateNonExistingUserApplication() throws Exception {
        int databaseSizeBeforeUpdate = userApplicationRepository.findAll().size();

        // Create the UserApplication

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUserApplicationMockMvc.perform(put("/api/user-applications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userApplication)))
            .andExpect(status().isBadRequest());

        // Validate the UserApplication in the database
        List<UserApplication> userApplicationList = userApplicationRepository.findAll();
        assertThat(userApplicationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserApplication() throws Exception {
        // Initialize the database
        userApplicationService.save(userApplication);

        int databaseSizeBeforeDelete = userApplicationRepository.findAll().size();

        // Get the userApplication
        restUserApplicationMockMvc.perform(delete("/api/user-applications/{id}", userApplication.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UserApplication> userApplicationList = userApplicationRepository.findAll();
        assertThat(userApplicationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserApplication.class);
        UserApplication userApplication1 = new UserApplication();
        userApplication1.setId(1L);
        UserApplication userApplication2 = new UserApplication();
        userApplication2.setId(userApplication1.getId());
        assertThat(userApplication1).isEqualTo(userApplication2);
        userApplication2.setId(2L);
        assertThat(userApplication1).isNotEqualTo(userApplication2);
        userApplication1.setId(null);
        assertThat(userApplication1).isNotEqualTo(userApplication2);
    }
}
