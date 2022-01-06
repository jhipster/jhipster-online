/**
 * Copyright 2017-2022 the original author or authors from the JHipster project.
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

import io.github.jhipster.online.domain.User;
import io.github.jhipster.online.domain.enums.EntityStatColumn;
import io.github.jhipster.online.domain.enums.YoRCColumn;
import io.github.jhipster.online.security.AuthoritiesConstants;
import io.github.jhipster.online.service.*;
import io.github.jhipster.online.service.dto.EntityStatsDTO;
import io.github.jhipster.online.service.dto.SubGenEventDTO;
import io.github.jhipster.online.service.dto.TemporalCountDTO;
import io.github.jhipster.online.service.dto.TemporalDistributionDTO;
import io.github.jhipster.online.service.enums.TemporalValueType;
import io.github.jhipster.online.util.DateUtil;
import io.github.jhipster.online.util.SanitizeInputs;
import java.io.IOException;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/s")
public class StatisticsResource {

    private final Logger log = LoggerFactory.getLogger(StatisticsResource.class);

    private final StatisticsService statisticsService;

    private final YoRCService yoRCService;

    private final JdlService jdlService;

    private final SubGenEventService subGenEventService;

    private final UserService userService;

    private final GeneratorIdentityService generatorIdentityService;

    private final EntityStatsService entityStatService;

    public StatisticsResource(
        StatisticsService statisticsService,
        YoRCService yoRCService,
        JdlService jdlService,
        SubGenEventService subGenEventService,
        UserService userService,
        GeneratorIdentityService generatorIdentityService,
        EntityStatsService entityStatService
    ) {
        this.statisticsService = statisticsService;
        this.yoRCService = yoRCService;
        this.jdlService = jdlService;
        this.subGenEventService = subGenEventService;
        this.userService = userService;
        this.generatorIdentityService = generatorIdentityService;
        this.entityStatService = entityStatService;
    }

    @GetMapping("/count-yo/{frequency}")
    public ResponseEntity<List<TemporalCountDTO>> getCount(@PathVariable String frequency) {
        Instant frequencyInstant = DateUtil.getFrequencyInstant(ZonedDateTime.now(), frequency);
        TemporalValueType temporalValueType = DateUtil.getTemporalValueTypeFromFrequency(frequency);

        if (frequencyInstant == null || temporalValueType == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(yoRCService.getCount(frequencyInstant, temporalValueType), HttpStatus.OK);
        }
    }

    @GetMapping("/yo/{field}/{frequency}")
    public ResponseEntity<List<TemporalDistributionDTO>> getYoFieldCount(
        @NotNull @PathVariable String field,
        @NotNull @PathVariable String frequency
    ) {
        Instant frequencyInstant = DateUtil.getFrequencyInstant(ZonedDateTime.now(), frequency);
        TemporalValueType temporalValueType = DateUtil.getTemporalValueTypeFromFrequency(frequency);
        YoRCColumn column = DateUtil.getYoColumnFromField(field);

        if (frequencyInstant == null || temporalValueType == null || column == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(yoRCService.getFieldCount(frequencyInstant, column, temporalValueType), HttpStatus.OK);
        }
    }

    @GetMapping("/entity/{field}/{frequency}")
    public ResponseEntity<List<TemporalDistributionDTO>> getEntityFieldCount(
        @NotNull @PathVariable String field,
        @NotNull @PathVariable String frequency
    ) {
        Instant frequencyInstant = DateUtil.getFrequencyInstant(ZonedDateTime.now(), frequency);
        TemporalValueType temporalValueType = DateUtil.getTemporalValueTypeFromFrequency(frequency);
        EntityStatColumn column = DateUtil.getEntityColumnFromField(field);

        if (frequencyInstant == null || temporalValueType == null || column == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(entityStatService.getFieldCount(frequencyInstant, column, temporalValueType), HttpStatus.OK);
        }
    }

    @GetMapping("/sub-gen-event/deployment/{frequency}")
    public ResponseEntity<List<TemporalDistributionDTO>> getDeploymentToolsDistribution(@NotNull @PathVariable String frequency) {
        Instant frequencyInstant = DateUtil.getFrequencyInstant(ZonedDateTime.now(), frequency);
        TemporalValueType temporalValueType = DateUtil.getTemporalValueTypeFromFrequency(frequency);

        if (frequencyInstant == null || temporalValueType == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(
                subGenEventService.getDeploymentToolDistribution(frequencyInstant, temporalValueType),
                HttpStatus.OK
            );
        }
    }

    @GetMapping("/entity/{frequency}")
    public ResponseEntity<List<TemporalCountDTO>> getEntityCount(@NotNull @PathVariable String frequency) {
        Instant frequencyInstant = DateUtil.getFrequencyInstant(ZonedDateTime.now(), frequency);
        TemporalValueType temporalValueType = DateUtil.getTemporalValueTypeFromFrequency(frequency);

        if (frequencyInstant == null || temporalValueType == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(entityStatService.getCount(frequencyInstant, temporalValueType), HttpStatus.OK);
        }
    }

    @GetMapping("/count-yo")
    public long getYoRCCount() {
        return yoRCService.countAll();
    }

    @GetMapping("/count-jdl")
    public long getJdlCount() {
        return jdlService.countAll();
    }

    @GetMapping("/count-user")
    public long getUserCount() {
        return userService.countAll();
    }

    @PostMapping("/entry")
    public ResponseEntity<String> addYoRc(HttpServletRequest req, @RequestBody String entry) {
        try {
            statisticsService.addEntry(entry, req.getRemoteHost());
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/event/{generatorId}")
    public ResponseEntity<String> addSubGenEvent(@RequestBody SubGenEventDTO event, @PathVariable String generatorId) {
        generatorId = SanitizeInputs.sanitizeInput(generatorId);
        statisticsService.addSubGenEvent(event, generatorId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/entity/{generatorId}")
    public ResponseEntity<String> addEntityStats(@RequestBody EntityStatsDTO entity, @PathVariable String generatorId) {
        generatorId = SanitizeInputs.sanitizeInput(generatorId);
        statisticsService.addEntityStats(entity, generatorId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/link/{generatorId}")
    public ResponseEntity<String> linkGeneratorToCurrentUser(@NotNull @PathVariable String generatorId) {
        generatorId = SanitizeInputs.sanitizeInput(generatorId);
        log.info("Binding current user to generator {}", generatorId);

        if (generatorIdentityService.bindUserToGenerator(userService.getUser(), generatorId)) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("It seems that this generator is already bound to a user.", HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/link/{generatorId}")
    public ResponseEntity<String> unlinkGeneratorFromCurrentUser(@NotNull @PathVariable String generatorId) {
        generatorId = SanitizeInputs.sanitizeInput(generatorId);
        log.info("Unbinding current user to generator {}", generatorId);

        if (generatorIdentityService.unbindUserFromGenerator(userService.getUser(), generatorId)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>("This generator doesn't exist or you don't own it.", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/data")
    @Secured(AuthoritiesConstants.ADMIN)
    public void deleteStatisticsData() {
        User user = userService.getUser();

        log.info("Deleting statistics data of {}", user.getLogin());
        statisticsService.deleteStatistics(user);
    }
}
