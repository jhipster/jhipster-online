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

import { GitConfigurationModel, GitConfigurationService } from 'app/core';

@Component({
    selector: 'jhi-github',
    templateUrl: './git.component.html',
    styleUrls: ['git.scss']
})
export class GitComponent implements OnInit {
    gitConfig: GitConfigurationModel;

    constructor(private gitConfigurationService: GitConfigurationService) {}

    ngOnInit() {
        this.gitConfigurationService.currentGitConfig.subscribe(config => (this.gitConfig = config));
        console.log(this.gitConfig);
        this.gitConfig.availableGitProviders.forEach(provider => {
            this.gitConfigurationService.gitProviderService.getCompanies(provider).subscribe(orgs => {
                if (orgs.length === 0) {
                    this.gitConfigurationService.gitProviderService.refreshGitProvider(provider).subscribe(
                        () => {
                            switch (provider) {
                                case 'github':
                                    this.gitConfig = { ...this.gitConfig, isAuthorizingGithub: true };
                                    break;
                                case 'gitlab':
                                    this.gitConfig = { ...this.gitConfig, isAuthorizingGitlab: true };
                                    break;
                            }
                        },
                        () => {
                            switch (provider) {
                                case 'github':
                                    this.gitConfig = { ...this.gitConfig, isAuthorizingGithub: true };
                                    break;
                                case 'gitlab':
                                    this.gitConfig = { ...this.gitConfig, isAuthorizingGitlab: true };
                                    break;
                            }
                        }
                    );
                }
            });
        });
    }
}
