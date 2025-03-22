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
package io.github.jhipster.online.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    private JhipsterCmdProperties jhipsterCmd = new JhipsterCmdProperties();

    private NpmCmdProperties npmCmd = new NpmCmdProperties();

    private final GithubProperties github = new GithubProperties();

    private final GitlabProperties gitlab = new GitlabProperties();

    private final MailProperties mail = new MailProperties();

    private String tmpFolder = System.getProperty("java.io.tmpdir");

    public JhipsterCmdProperties getJhipsterCmd() {
        return jhipsterCmd;
    }

    public NpmCmdProperties getNpmCmd() {
        return npmCmd;
    }

    public void setNpmCmd(NpmCmdProperties npmCmd) {
        this.npmCmd = npmCmd;
    }

    public void setJhipsterCmd(JhipsterCmdProperties jhipsterCmd) {
        this.jhipsterCmd = jhipsterCmd;
    }

    public String getTmpFolder() {
        return tmpFolder;
    }

    public void setTmpFolder(String tmpFolder) {
        this.tmpFolder = tmpFolder;
    }

    public GithubProperties getGithub() {
        return github;
    }

    public GitlabProperties getGitlab() {
        return gitlab;
    }

    public MailProperties getMail() {
        return mail;
    }
}
