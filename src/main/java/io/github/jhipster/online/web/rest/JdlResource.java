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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import com.codahale.metrics.annotation.Timed;

import io.github.jhipster.online.domain.*;
import io.github.jhipster.online.domain.enums.GitProvider;
import io.github.jhipster.online.repository.JdlRepository;
import io.github.jhipster.online.security.AuthoritiesConstants;
import io.github.jhipster.online.service.*;
import io.github.jhipster.online.web.rest.vm.JdlVM;

@RestController
@RequestMapping("/api")
public class JdlResource {

    private final Logger log = LoggerFactory.getLogger(JdlResource.class);

    private final UserService userService;

    private final JdlMetadataService jdlMetadataService;

    private final JdlRepository jdlRepository;

    private final JdlService jdlService;

    private final LogsService logsService;

    public JdlResource(UserService userService, JdlMetadataService jdlMetadataService,
        JdlRepository jdlRepository, LogsService logsService,
        JdlService jdlService) {
        this.userService = userService;
        this.jdlMetadataService = jdlMetadataService;
        this.jdlRepository = jdlRepository;
        this.logsService = logsService;
        this.jdlService = jdlService;
    }

    /**
     * Get a JDL file by its ID.
     */
    @GetMapping("/jdl/{jdlId}")
    @Timed
    public ResponseEntity<JdlVM> getJdlFile(@PathVariable String jdlId) {
        log.debug("Trying to retrieve JDL: {}", jdlId);
        Optional<JdlMetadata> jdlMetadata = jdlMetadataService.findOne(jdlId);
        Optional<Jdl> jdl = this.jdlRepository.findOneByJdlMetadataId(jdlId);
        if (!jdl.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        JdlVM vm = new JdlVM();
        vm.setName(jdlMetadata.get().getName());
        vm.setContent(jdl.get().getContent());
        return ResponseEntity.ok(vm);
    }

    /**
     * Create a new JDL files and gives back its key.
     */
    @PostMapping("/jdl")
    @Timed
    @Secured(AuthoritiesConstants.USER)
    public @ResponseBody
    ResponseEntity createJdlFile(@RequestBody JdlVM vm) throws URISyntaxException {
        JdlMetadata jdlMetadata = new JdlMetadata();
        if (vm.getName() == null || vm.getName().equals("")) {
            jdlMetadata.setName("New JDL Model");
        } else {
            jdlMetadata.setName(vm.getName());
        }
        jdlMetadataService.create(jdlMetadata, vm.getContent());
        return ResponseEntity.created(new URI("/api/jdl/" + jdlMetadata.getId()))
            .body(jdlMetadata);
    }

    /**
     * Update a JDL file.
     */
    @PutMapping("/jdl/{jdlId}")
    @Timed
    @Secured(AuthoritiesConstants.USER)
    public @ResponseBody
    ResponseEntity updateJdlFile(@PathVariable String jdlId, @RequestBody JdlVM vm) {
        Optional<JdlMetadata> jdlMetadata = jdlMetadataService.findOne(jdlId);
        try {
            jdlMetadataService.updateJdlContent(jdlMetadata.get(), vm.getContent());
        } catch (Exception e) {
            log.info("Could not update the JDL file", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Delete a JDL file.
     */
    @DeleteMapping("/jdl/{jdlId}")
    @Timed
    @Secured(AuthoritiesConstants.USER)
    public @ResponseBody
    ResponseEntity deleteJdlFile(@PathVariable String jdlId) {
        try {
            this.jdlMetadataService.delete(jdlId);
        } catch (Exception e) {
            log.info("Could not delete the JDL file", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/apply-jdl/{gitProvider}/{organizationName}/{projectName}/{jdlId}")
    @Timed
    @Secured(AuthoritiesConstants.USER)
    public ResponseEntity applyJdl(@PathVariable String gitProvider, @PathVariable String organizationName,
        @PathVariable String projectName,
        @PathVariable String jdlId) {
        boolean isGitHub = gitProvider.toLowerCase().equals("github");
        log.info("Applying JDL `{}` on " + (isGitHub ? "GitHub" : "GitLab") + " project {}/{}", jdlId,
            organizationName, projectName);
        User user = userService.getUser();

        Optional<JdlMetadata> jdlMetadata = this.jdlMetadataService.findOne(jdlId);
        String applyJdlId = this.jdlService.kebabCaseJdlName(jdlMetadata.get()) + "-" +
            System.nanoTime();
        this.logsService.addLog(applyJdlId, "JDL Model is going to be applied to " + organizationName + "/" +
            projectName);

        try {
            this.jdlService.applyJdl(user, organizationName, projectName, jdlMetadata.get(),
                applyJdlId, GitProvider.getGitProviderByValue(gitProvider).orElseThrow(null));
        } catch (Exception e) {
            log.error("Error generating application", e);
            this.logsService.addLog(jdlId, "An error has occurred: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(applyJdlId, HttpStatus.CREATED);
    }

    @GetMapping("/apply-jdl-logs/{applyJdlId}")
    @Timed
    @Secured(AuthoritiesConstants.USER)
    public ResponseEntity<String> generateApplicationOutput(@PathVariable String applyJdlId) {
        String logs = this.logsService.getLogs(applyJdlId);
        return new ResponseEntity<>(logs, HttpStatus.OK);
    }
}
