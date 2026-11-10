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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import io.github.jhipster.online.service.*;
import io.github.jhipster.online.service.dto.EntityStatsDTO;
import io.github.jhipster.online.service.dto.SubGenEventDTO;
import io.github.jhipster.online.service.mapper.EntityStatsMapper;
import io.github.jhipster.online.service.mapper.SubGenEventMapper;
import io.github.jhipster.online.service.mapper.YoRCMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.task.TaskRejectedException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * Unit tests for the {@link StatisticsResource} REST controller.
 */
@ExtendWith(MockitoExtension.class)
class StatisticsResourceTest {

    @Mock
    private YoRCService yoRCService;

    @Mock
    private JdlService jdlService;

    @Mock
    private SubGenEventService subGenEventService;

    @Mock
    private UserService userService;

    @Mock
    private GeneratorIdentityService generatorIdentityService;

    @Mock
    private EntityStatsService entityStatsService;

    @Mock
    private SubGenEventMapper subGenEventMapper;

    @Mock
    private EntityStatsMapper entityStatsMapper;

    @Mock
    private YoRCMapper yoRCMapper;

    private StatisticsService statisticsService;

    private MockMvc restStatisticsMockMvc;

    @BeforeEach
    void setup() {
        statisticsService =
            spy(
                new StatisticsService(
                    yoRCService,
                    generatorIdentityService,
                    subGenEventService,
                    entityStatsService,
                    subGenEventMapper,
                    entityStatsMapper,
                    yoRCMapper
                )
            );
        StatisticsResource statisticsResource = new StatisticsResource(
            statisticsService,
            yoRCService,
            jdlService,
            subGenEventService,
            userService,
            generatorIdentityService,
            entityStatsService
        );
        restStatisticsMockMvc = MockMvcBuilders.standaloneSetup(statisticsResource).build();
    }

    @Test
    void shouldToggleStatisticsStatus() throws Exception {
        restStatisticsMockMvc.perform(get("/api/s/status")).andExpect(status().isOk()).andExpect(content().string("true"));

        restStatisticsMockMvc.perform(put("/api/s/status/false")).andExpect(status().isOk());
        assertThat(statisticsService.isStatisticsEnabled()).isFalse();

        restStatisticsMockMvc.perform(get("/api/s/status")).andExpect(status().isOk()).andExpect(content().string("false"));

        restStatisticsMockMvc.perform(put("/api/s/status/true")).andExpect(status().isOk());
        assertThat(statisticsService.isStatisticsEnabled()).isTrue();
    }

    @Test
    void shouldSkipSubmissionsWhenStatisticsAreDisabled() throws Exception {
        statisticsService.setStatisticsEnabled(false);

        restStatisticsMockMvc
            .perform(post("/api/s/entry").contentType(MediaType.APPLICATION_JSON).content("{}"))
            .andExpect(status().isNoContent());

        restStatisticsMockMvc
            .perform(post("/api/s/event/gen-1").contentType(MediaType.APPLICATION_JSON).content("{\"type\":\"entity\"}"))
            .andExpect(status().isNoContent());

        restStatisticsMockMvc
            .perform(post("/api/s/entity/gen-1").contentType(MediaType.APPLICATION_JSON).content("{\"fields\":2}"))
            .andExpect(status().isNoContent());

        verify(statisticsService, never()).addEntry(anyString(), anyString());
        verify(statisticsService, never()).addSubGenEvent(any(SubGenEventDTO.class), anyString());
        verify(statisticsService, never()).addEntityStats(any(EntityStatsDTO.class), anyString());
    }

    @Test
    void shouldHandleTaskRejectedExceptionGracefully() throws Exception {
        doThrow(new TaskRejectedException("Statistics queue full")).when(statisticsService).addEntry(anyString(), any());
        doThrow(new TaskRejectedException("Statistics queue full"))
            .when(statisticsService)
            .addSubGenEvent(any(SubGenEventDTO.class), anyString());
        doThrow(new TaskRejectedException("Statistics queue full"))
            .when(statisticsService)
            .addEntityStats(any(EntityStatsDTO.class), anyString());

        restStatisticsMockMvc
            .perform(post("/api/s/entry").contentType(MediaType.APPLICATION_JSON).content("{}"))
            .andExpect(status().isNoContent());

        restStatisticsMockMvc
            .perform(post("/api/s/event/gen-1").contentType(MediaType.APPLICATION_JSON).content("{\"type\":\"entity\"}"))
            .andExpect(status().isNoContent());

        restStatisticsMockMvc
            .perform(post("/api/s/entity/gen-1").contentType(MediaType.APPLICATION_JSON).content("{\"fields\":2}"))
            .andExpect(status().isNoContent());
    }
}
