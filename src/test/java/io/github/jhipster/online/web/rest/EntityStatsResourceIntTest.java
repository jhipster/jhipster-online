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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.jhipster.online.JhonlineApp;
import io.github.jhipster.online.domain.EntityStats;
import io.github.jhipster.online.repository.EntityStatsRepository;
import io.github.jhipster.online.service.EntityStatsService;
import io.github.jhipster.online.service.dto.EntityStatsDTO;
import io.github.jhipster.online.service.mapper.EntityStatsMapper;
import io.github.jhipster.online.web.rest.errors.ExceptionTranslator;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import javax.persistence.EntityManager;
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
 * Test class for the EntityStatsResource REST controller.
 *
 * @see EntityStatsResource
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = JhonlineApp.class)
class EntityStatsResourceIntTest {

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

    private static final Integer DEFAULT_FIELDS = 1;
    private static final Integer UPDATED_FIELDS = 2;

    private static final Integer DEFAULT_RELATIONSHIPS = 1;
    private static final Integer UPDATED_RELATIONSHIPS = 2;

    private static final String DEFAULT_PAGINATION = "AAAAAAAAAA";
    private static final String UPDATED_PAGINATION = "BBBBBBBBBB";

    private static final String DEFAULT_DTO = "AAAAAAAAAA";
    private static final String UPDATED_DTO = "BBBBBBBBBB";

    private static final String DEFAULT_SERVICE = "AAAAAAAAAA";
    private static final String UPDATED_SERVICE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_FLUENT_METHODS = false;
    private static final Boolean UPDATED_FLUENT_METHODS = true;

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(1000L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private EntityStatsRepository entityStatsRepository;

    @Autowired
    private EntityStatsService entityStatsService;

    @Autowired
    private EntityStatsMapper entityStatsMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEntityStatsMockMvc;

    private EntityStats entityStats;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EntityStatsResource entityStatsResource = new EntityStatsResource(entityStatsService);
        this.restEntityStatsMockMvc =
            MockMvcBuilders
                .standaloneSetup(entityStatsResource)
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
    public static EntityStats createEntity(EntityManager em) {
        return new EntityStats()
            .year(DEFAULT_YEAR)
            .month(DEFAULT_MONTH)
            .week(DEFAULT_WEEK)
            .day(DEFAULT_DAY)
            .hour(DEFAULT_HOUR)
            .fields(DEFAULT_FIELDS)
            .relationships(DEFAULT_RELATIONSHIPS)
            .pagination(DEFAULT_PAGINATION)
            .dto(DEFAULT_DTO)
            .service(DEFAULT_SERVICE)
            .fluentMethods(DEFAULT_FLUENT_METHODS)
            .date(DEFAULT_DATE);
    }

    @BeforeEach
    public void initTest() {
        entityStats = createEntity(em);
    }

    @Test
    @Transactional
    void createEntityStats() throws Exception {
        int databaseSizeBeforeCreate = entityStatsRepository.findAll().size();

        // Create the EntityStats
        restEntityStatsMockMvc
            .perform(
                post("/api/entity-stats").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(entityStats))
            )
            .andExpect(status().isCreated());

        // Validate the EntityStats in the database
        List<EntityStats> entityStatsList = entityStatsRepository.findAll();
        assertThat(entityStatsList).hasSize(databaseSizeBeforeCreate + 1);
        EntityStats testEntityStats = entityStatsList.get(entityStatsList.size() - 1);
        assertThat(testEntityStats.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testEntityStats.getMonth()).isEqualTo(DEFAULT_MONTH);
        assertThat(testEntityStats.getWeek()).isEqualTo(DEFAULT_WEEK);
        assertThat(testEntityStats.getDay()).isEqualTo(DEFAULT_DAY);
        assertThat(testEntityStats.getHour()).isEqualTo(DEFAULT_HOUR);
        assertThat(testEntityStats.getFields()).isEqualTo(DEFAULT_FIELDS);
        assertThat(testEntityStats.getRelationships()).isEqualTo(DEFAULT_RELATIONSHIPS);
        assertThat(testEntityStats.getPagination()).isEqualTo(DEFAULT_PAGINATION);
        assertThat(testEntityStats.getDto()).isEqualTo(DEFAULT_DTO);
        assertThat(testEntityStats.getService()).isEqualTo(DEFAULT_SERVICE);
        assertThat(testEntityStats.isFluentMethods()).isEqualTo(DEFAULT_FLUENT_METHODS);
        assertThat(testEntityStats.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    void createEntityStatsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = entityStatsRepository.findAll().size();

        // Create the EntityStats with an existing ID
        entityStats.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEntityStatsMockMvc
            .perform(
                post("/api/entity-stats").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(entityStats))
            )
            .andExpect(status().isBadRequest());

        // Validate the EntityStats in the database
        List<EntityStats> entityStatsList = entityStatsRepository.findAll();
        assertThat(entityStatsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEntityStats() throws Exception {
        // Initialize the database
        entityStatsRepository.saveAndFlush(entityStats);

        // Get all the entityStatsList
        restEntityStatsMockMvc
            .perform(get("/api/entity-stats?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[*].id").value(hasItem(entityStats.getId().intValue())))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].month").value(hasItem(DEFAULT_MONTH)))
            .andExpect(jsonPath("$.[*].week").value(hasItem(DEFAULT_WEEK)))
            .andExpect(jsonPath("$.[*].day").value(hasItem(DEFAULT_DAY)))
            .andExpect(jsonPath("$.[*].hour").value(hasItem(DEFAULT_HOUR)))
            .andExpect(jsonPath("$.[*].fields").value(hasItem(DEFAULT_FIELDS)))
            .andExpect(jsonPath("$.[*].relationships").value(hasItem(DEFAULT_RELATIONSHIPS)))
            .andExpect(jsonPath("$.[*].pagination").value(hasItem(DEFAULT_PAGINATION)))
            .andExpect(jsonPath("$.[*].dto").value(hasItem(DEFAULT_DTO)))
            .andExpect(jsonPath("$.[*].service").value(hasItem(DEFAULT_SERVICE)))
            .andExpect(jsonPath("$.[*].fluentMethods").value(hasItem(DEFAULT_FLUENT_METHODS)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }

    @Test
    @Transactional
    void getEntityStats() throws Exception {
        // Initialize the database
        entityStatsRepository.saveAndFlush(entityStats);

        // Get the entityStats
        restEntityStatsMockMvc
            .perform(get("/api/entity-stats/{id}", entityStats.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(entityStats.getId().intValue()))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR))
            .andExpect(jsonPath("$.month").value(DEFAULT_MONTH))
            .andExpect(jsonPath("$.week").value(DEFAULT_WEEK))
            .andExpect(jsonPath("$.day").value(DEFAULT_DAY))
            .andExpect(jsonPath("$.hour").value(DEFAULT_HOUR))
            .andExpect(jsonPath("$.fields").value(DEFAULT_FIELDS))
            .andExpect(jsonPath("$.relationships").value(DEFAULT_RELATIONSHIPS))
            .andExpect(jsonPath("$.pagination").value(DEFAULT_PAGINATION))
            .andExpect(jsonPath("$.dto").value(DEFAULT_DTO))
            .andExpect(jsonPath("$.service").value(DEFAULT_SERVICE))
            .andExpect(jsonPath("$.fluentMethods").value(DEFAULT_FLUENT_METHODS))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingEntityStats() throws Exception {
        // Get the entityStats
        restEntityStatsMockMvc.perform(get("/api/entity-stats/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void updateEntityStats() throws Exception {
        // Initialize the database
        EntityStatsDTO entityStatsDTO = entityStatsService.save(entityStatsMapper.toDto(entityStats));

        int databaseSizeBeforeUpdate = entityStatsRepository.findAll().size();

        // Update the entityStats
        EntityStats updatedEntityStats = entityStatsRepository.findById(entityStatsDTO.getId()).get();
        // Disconnect from session so that the updates on updatedEntityStats are not directly saved in db
        em.detach(updatedEntityStats);
        updatedEntityStats
            .year(UPDATED_YEAR)
            .month(UPDATED_MONTH)
            .week(UPDATED_WEEK)
            .day(UPDATED_DAY)
            .hour(UPDATED_HOUR)
            .fields(UPDATED_FIELDS)
            .relationships(UPDATED_RELATIONSHIPS)
            .pagination(UPDATED_PAGINATION)
            .dto(UPDATED_DTO)
            .service(UPDATED_SERVICE)
            .fluentMethods(UPDATED_FLUENT_METHODS)
            .date(UPDATED_DATE);

        restEntityStatsMockMvc
            .perform(
                put("/api/entity-stats")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEntityStats))
            )
            .andExpect(status().isOk());

        // Validate the EntityStats in the database
        List<EntityStats> entityStatsList = entityStatsRepository.findAll();
        assertThat(entityStatsList).hasSize(databaseSizeBeforeUpdate);
        EntityStats testEntityStats = entityStatsList.get(entityStatsList.size() - 1);
        assertThat(testEntityStats.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testEntityStats.getMonth()).isEqualTo(UPDATED_MONTH);
        assertThat(testEntityStats.getWeek()).isEqualTo(UPDATED_WEEK);
        assertThat(testEntityStats.getDay()).isEqualTo(UPDATED_DAY);
        assertThat(testEntityStats.getHour()).isEqualTo(UPDATED_HOUR);
        assertThat(testEntityStats.getFields()).isEqualTo(UPDATED_FIELDS);
        assertThat(testEntityStats.getRelationships()).isEqualTo(UPDATED_RELATIONSHIPS);
        assertThat(testEntityStats.getPagination()).isEqualTo(UPDATED_PAGINATION);
        assertThat(testEntityStats.getDto()).isEqualTo(UPDATED_DTO);
        assertThat(testEntityStats.getService()).isEqualTo(UPDATED_SERVICE);
        assertThat(testEntityStats.isFluentMethods()).isEqualTo(UPDATED_FLUENT_METHODS);
        assertThat(testEntityStats.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    void updateNonExistingEntityStats() throws Exception {
        int databaseSizeBeforeUpdate = entityStatsRepository.findAll().size();

        // Create the EntityStats

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEntityStatsMockMvc
            .perform(
                put("/api/entity-stats").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(entityStats))
            )
            .andExpect(status().isBadRequest());

        // Validate the EntityStats in the database
        List<EntityStats> entityStatsList = entityStatsRepository.findAll();
        assertThat(entityStatsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEntityStats() throws Exception {
        // Initialize the database
        EntityStatsDTO entityStatsDTO = entityStatsService.save(entityStatsMapper.toDto(entityStats));

        int databaseSizeBeforeDelete = entityStatsRepository.findAll().size();

        // Get the entityStats
        restEntityStatsMockMvc
            .perform(delete("/api/entity-stats/{id}", entityStatsDTO.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<EntityStats> entityStatsList = entityStatsRepository.findAll();
        assertThat(entityStatsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EntityStats.class);
        EntityStats entityStats1 = new EntityStats();
        entityStats1.setId(1L);
        EntityStats entityStats2 = new EntityStats();
        entityStats2.setId(entityStats1.getId());
        assertThat(entityStats1).isEqualTo(entityStats2);
        entityStats2.setId(2L);
        assertThat(entityStats1).isNotEqualTo(entityStats2);
        entityStats1.setId(null);
        assertThat(entityStats1).isNotEqualTo(entityStats2);
    }
}
