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

package io.github.jhipster.online.service;

import java.io.*;

import org.apache.commons.lang3.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class JHipsterService {

    public final Logger log = LoggerFactory.getLogger(JHipsterService.class);

    private final LogsService logsService;

    private String jhipsterCommand = "";

    public JHipsterService(LogsService logsService) {
        this.logsService = logsService;
        if (SystemUtils.IS_OS_LINUX) {
            jhipsterCommand = "timeout 120 ";
        }
        jhipsterCommand += System.getProperty("user.home") + "/.yarn/bin/jhipster";
    }

    public void installYarnDependencies(String generationId, File workingDir) throws IOException {
        this.logsService.addLog(generationId, "Installing the JHipster version used by the project");
        this.runProcess(generationId, workingDir, "yarn install --ignore-scripts --frozen-lockfile");
    }

    public void generateApplication(String generationId, File workingDir) throws IOException {
        this.logsService.addLog(generationId, "Running JHipster");
        this.runProcess(generationId, workingDir, jhipsterCommand + " --no-insight --skip-checks " +
            "--skip-install --skip-cache --skip-git");
    }

    public void runImportJdl(String generationId, File workingDir, String jdlFileName) throws IOException {
        this.logsService.addLog(generationId, "Running `jhipster import-jdl");
        this.runProcess(generationId, workingDir, jhipsterCommand + " import-jdl " +
            jdlFileName + ".jh " +
            "--no-insight --skip-checks --skip-install --force ");
    }

    public void addCiCdTravis(String generationId, File workingDir, String ciCdTool) throws Exception {
        if (ciCdTool == null || (!ciCdTool.equals("travis") && !ciCdTool.equals("jenkins"))) {
            this.logsService.addLog(generationId, "Continuous Integration system not supported, aborting");
            throw new Exception("Invalid Continuous Integration system");
        }
        this.logsService.addLog(generationId, "Running `jhipster ci-cd`");
        this.runProcess(generationId, workingDir, jhipsterCommand + " ci-cd " +
            "--autoconfigure-" + ciCdTool + " --no-insight --skip-checks --skip-install --force ");
    }

    private void runProcess(String generationId, File workingDir, String command) throws IOException {
        try {
            String line;
            Process p = Runtime.getRuntime().exec
                (command, null, workingDir);
            BufferedReader input =
                new BufferedReader
                    (new InputStreamReader(p.getInputStream()));
            while ((line = input.readLine()) != null) {
                log.debug(line);
                this.logsService.addLog(generationId, line);
            }
            input.close();
        } catch (Exception e) {
            log.error("Error while running the process", e);
            throw e;
        }
    }
}
