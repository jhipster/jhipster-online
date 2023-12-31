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
import io.github.jhipster.online.domain.SubGenEvent;
import io.github.jhipster.online.repository.SubGenEventRepository;
import io.github.jhipster.online.service.SubGenEventService;
import io.github.jhipster.online.service.dto.SubGenEventDTO;
import io.github.jhipster.online.service.mapper.SubGenEventMapper;
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
 * Test class for the SubGenEventResource REST controller.
 *
 * @see SubGenEventResource
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = JhonlineApp.class)
class SubGenEventResourceIntTest {

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

    private static final String DEFAULT_SOURCE = "AAAAAAAAAA";
    private static final String UPDATED_SOURCE = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_EVENT = "AAAAAAAAAA";
    private static final String UPDATED_EVENT = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(1000L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private SubGenEventRepository subGenEventRepository;

    @Autowired
    private SubGenEventService subGenEventService;

    @Autowired
    private SubGenEventMapper subGenEventMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSubGenEventMockMvc;

    private SubGenEvent subGenEvent;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SubGenEventResource subGenEventResource = new SubGenEventResource(subGenEventService);
        this.restSubGenEventMockMvc =
            MockMvcBuilders
                .standaloneSetup(subGenEventResource)
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
    public static SubGenEvent createEntity(EntityManager em) {
        return new SubGenEvent()
            .year(DEFAULT_YEAR)
            .month(DEFAULT_MONTH)
            .week(DEFAULT_WEEK)
            .day(DEFAULT_DAY)
            .hour(DEFAULT_HOUR)
            .source(DEFAULT_SOURCE)
            .type(DEFAULT_TYPE)
            .event(DEFAULT_EVENT)
            .date(DEFAULT_DATE);
    }

    @BeforeEach
    public void initTest() {
        subGenEvent = createEntity(em);
    }

    @Test
    @Transactional
    void createSubGenEvent() throws Exception {
        int databaseSizeBeforeCreate = subGenEventRepository.findAll().size();

        // Create the SubGenEvent
        restSubGenEventMockMvc
            .perform(
                post("/api/sub-gen-events").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(subGenEvent))
            )
            .andExpect(status().isCreated());

        // Validate the SubGenEvent in the database
        List<SubGenEvent> subGenEventList = subGenEventRepository.findAll();
        assertThat(subGenEventList).hasSize(databaseSizeBeforeCreate + 1);
        SubGenEvent testSubGenEvent = subGenEventList.get(subGenEventList.size() - 1);
        assertThat(testSubGenEvent.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testSubGenEvent.getMonth()).isEqualTo(DEFAULT_MONTH);
        assertThat(testSubGenEvent.getWeek()).isEqualTo(DEFAULT_WEEK);
        assertThat(testSubGenEvent.getDay()).isEqualTo(DEFAULT_DAY);
        assertThat(testSubGenEvent.getHour()).isEqualTo(DEFAULT_HOUR);
        assertThat(testSubGenEvent.getSource()).isEqualTo(DEFAULT_SOURCE);
        assertThat(testSubGenEvent.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testSubGenEvent.getEvent()).isEqualTo(DEFAULT_EVENT);
        assertThat(testSubGenEvent.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    void createSubGenEventWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = subGenEventRepository.findAll().size();

        // Create the SubGenEvent with an existing ID
        subGenEvent.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSubGenEventMockMvc
            .perform(
                post("/api/sub-gen-events").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(subGenEvent))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubGenEvent in the database
        List<SubGenEvent> subGenEventList = subGenEventRepository.findAll();
        assertThat(subGenEventList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSubGenEvents() throws Exception {
        // Initialize the database
        subGenEventRepository.saveAndFlush(subGenEvent);

        // Get all the subGenEventList
        restSubGenEventMockMvc
            .perform(get("/api/sub-gen-events?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subGenEvent.getId().intValue())))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].month").value(hasItem(DEFAULT_MONTH)))
            .andExpect(jsonPath("$.[*].week").value(hasItem(DEFAULT_WEEK)))
            .andExpect(jsonPath("$.[*].day").value(hasItem(DEFAULT_DAY)))
            .andExpect(jsonPath("$.[*].hour").value(hasItem(DEFAULT_HOUR)))
            .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].event").value(hasItem(DEFAULT_EVENT)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }

    @Test
    @Transactional
    void getSubGenEvent() throws Exception {
        // Initialize the database
        subGenEventRepository.saveAndFlush(subGenEvent);

        // Get the subGenEvent
        restSubGenEventMockMvc
            .perform(get("/api/sub-gen-events/{id}", subGenEvent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(subGenEvent.getId().intValue()))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR))
            .andExpect(jsonPath("$.month").value(DEFAULT_MONTH))
            .andExpect(jsonPath("$.week").value(DEFAULT_WEEK))
            .andExpect(jsonPath("$.day").value(DEFAULT_DAY))
            .andExpect(jsonPath("$.hour").value(DEFAULT_HOUR))
            .andExpect(jsonPath("$.source").value(DEFAULT_SOURCE))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.event").value(DEFAULT_EVENT))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingSubGenEvent() throws Exception {
        // Get the subGenEvent
        restSubGenEventMockMvc.perform(get("/api/sub-gen-events/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void updateSubGenEvent() throws Exception {
        // Initialize the database
        SubGenEventDTO subGenEventDTO = subGenEventService.save(subGenEventMapper.toDto(subGenEvent));

        int databaseSizeBeforeUpdate = subGenEventRepository.findAll().size();

        // Update the subGenEvent
        SubGenEvent updatedSubGenEvent = subGenEventRepository.findById(subGenEventDTO.getId()).get();
        // Disconnect from session so that the updates on updatedSubGenEvent are not directly saved in db
        em.detach(updatedSubGenEvent);
        updatedSubGenEvent
            .year(UPDATED_YEAR)
            .month(UPDATED_MONTH)
            .week(UPDATED_WEEK)
            .day(UPDATED_DAY)
            .hour(UPDATED_HOUR)
            .source(UPDATED_SOURCE)
            .type(UPDATED_TYPE)
            .event(UPDATED_EVENT)
            .date(UPDATED_DATE);

        restSubGenEventMockMvc
            .perform(
                put("/api/sub-gen-events")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSubGenEvent))
            )
            .andExpect(status().isOk());

        // Validate the SubGenEvent in the database
        List<SubGenEvent> subGenEventList = subGenEventRepository.findAll();
        assertThat(subGenEventList).hasSize(databaseSizeBeforeUpdate);
        SubGenEvent testSubGenEvent = subGenEventList.get(subGenEventList.size() - 1);
        assertThat(testSubGenEvent.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testSubGenEvent.getMonth()).isEqualTo(UPDATED_MONTH);
        assertThat(testSubGenEvent.getWeek()).isEqualTo(UPDATED_WEEK);
        assertThat(testSubGenEvent.getDay()).isEqualTo(UPDATED_DAY);
        assertThat(testSubGenEvent.getHour()).isEqualTo(UPDATED_HOUR);
        assertThat(testSubGenEvent.getSource()).isEqualTo(UPDATED_SOURCE);
        assertThat(testSubGenEvent.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testSubGenEvent.getEvent()).isEqualTo(UPDATED_EVENT);
        assertThat(testSubGenEvent.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    void updateNonExistingSubGenEvent() throws Exception {
        int databaseSizeBeforeUpdate = subGenEventRepository.findAll().size();

        // Create the SubGenEvent

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSubGenEventMockMvc
            .perform(
                put("/api/sub-gen-events").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(subGenEvent))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubGenEvent in the database
        List<SubGenEvent> subGenEventList = subGenEventRepository.findAll();
        assertThat(subGenEventList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSubGenEvent() throws Exception {
        // Initialize the database
        SubGenEventDTO subGenEventDTO = subGenEventService.save(subGenEventMapper.toDto(subGenEvent));

        int databaseSizeBeforeDelete = subGenEventRepository.findAll().size();

        // Get the subGenEvent
        restSubGenEventMockMvc
            .perform(delete("/api/sub-gen-events/{id}", subGenEventDTO.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SubGenEvent> subGenEventList = subGenEventRepository.findAll();
        assertThat(subGenEventList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SubGenEvent.class);
        SubGenEvent subGenEvent1 = new SubGenEvent();
        subGenEvent1.setId(1L);
        SubGenEvent subGenEvent2 = new SubGenEvent();
        subGenEvent2.setId(subGenEvent1.getId());
        assertThat(subGenEvent1).isEqualTo(subGenEvent2);
        subGenEvent2.setId(2L);
        assertThat(subGenEvent1).isNotEqualTo(subGenEvent2);
        subGenEvent1.setId(null);
        assertThat(subGenEvent1).isNotEqualTo(subGenEvent2);
    }
}
