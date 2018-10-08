/**
 * Copyright 2017-2018 the original author or authors from the JHipster Online project.
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

import io.github.jhipster.online.service.enums.CiCdTool;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import com.codahale.metrics.annotation.Timed;

import io.github.jhipster.online.domain.User;
import io.github.jhipster.online.domain.enums.GitProvider;
import io.github.jhipster.online.security.AuthoritiesConstants;
import io.github.jhipster.online.service.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CiCdResource {

    private final Logger log = LoggerFactory.getLogger(CiCdResource.class);

    private final UserService userService;

    private final CiCdService ciCdService;

    private final LogsService logsService;

    public CiCdResource(UserService userService, CiCdService ciCdService, LogsService logsService) {
        this.userService = userService;
        this.ciCdService = ciCdService;
        this.logsService = logsService;
    }

    @PostMapping("/ci-cd/{gitProvider}/{organizationName}/{projectName}/{ciCdTool}")
    @Timed
    @Secured(AuthoritiesConstants.USER)
    public ResponseEntity configureCiCd(@PathVariable String gitProvider, @PathVariable String organizationName,
        @PathVariable String projectName, @PathVariable String ciCdTool) {
        boolean isGitHub = gitProvider.toLowerCase().equals("github");
        log.info("Configuring CI: {} on " + (isGitHub ? "GitHub" : "GitLab") + " {}/{}", ciCdTool, organizationName,
            projectName);
        User user = userService.getUser();
        String ciCdId = "ci-" + System.nanoTime();

        Optional<CiCdTool> integrationTool = CiCdTool.getByName(ciCdTool);
        if (!integrationTool.isPresent()) {
            this.logsService.addLog(ciCdId, "Continuous Integration with `" + ciCdTool + "` is not supported.");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        this.logsService.addLog(ciCdId, "Continuous Integration with " +
            StringUtils.capitalize(ciCdTool) +
            " is going to be applied to " +
            organizationName + "/" + projectName);

        try {
            this.ciCdService.configureCiCd(user, organizationName, projectName, integrationTool.get(),
                ciCdId, GitProvider.getGitProviderByValue(gitProvider).orElseThrow(null));
        } catch (Exception e) {
            log.error("Error generating application", e);
            this.logsService.addLog(ciCdId, "An error has occurred: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(ciCdId, HttpStatus.CREATED);
    }

    @GetMapping("/ci-cd-logs/{ciCdId}")
    @Timed
    @Secured(AuthoritiesConstants.USER)
    public ResponseEntity<String> generateApplicationOutput(@PathVariable String ciCdId) {
        String logs = this.logsService.getLogs(ciCdId);
        return new ResponseEntity<>(logs, HttpStatus.OK);
    }
}
