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
import { Component, OnInit } from '@angular/core';

import { GitProviderService } from './git.service';

@Component({
    selector: 'jhi-github',
    templateUrl: './git.component.html',
    styleUrls: ['git.scss']
})
export class GitComponent implements OnInit {
    githubClientId;
    gitlabClientId;

    isGithubAvailable = false;
    isGitlabAvailable = false;

    isGithubConfigured = false;
    isGitlabConfigured = false;

    isAuthorizingGithub = false;
    isAuthorizingGitlab = false;

    gitlabHost: string;
    gitlabRedirectUri: string;

    constructor(private gitService: GitProviderService) {}

    ngOnInit(): void {
        this.gitService.getAvailableProviders().subscribe(providers => {
            if (providers.includes('gitlab')) {
                this.gitlabClientId = this.gitService.clientId('gitlab').subscribe(clientId => (this.gitlabClientId = clientId));
                this.isAuthorizingGitlab = true;
                this.gitService.refreshGitProvider('gitlab').subscribe(
                    () => (this.isGitlabConfigured = true),
                    () => {
                        this.isAuthorizingGitlab = false;
                        this.isGitlabConfigured = false;
                    }
                );
                this.gitService.getGitlabConfig().subscribe(config => {
                    this.gitlabHost = config.host;
                    this.gitlabRedirectUri = config.redirectUri;
                });
                this.isGitlabAvailable = true;
            }
            if (providers.includes('github')) {
                this.githubClientId = this.gitService.clientId('github').subscribe(clientId => (this.githubClientId = clientId));
                this.isAuthorizingGithub = true;
                this.gitService.refreshGitProvider('github').subscribe(
                    () => (this.isGithubConfigured = true),
                    () => {
                        this.isAuthorizingGithub = false;
                        this.isGithubConfigured = false;
                    }
                );
                this.isGithubAvailable = true;
            }
        });
    }
}
