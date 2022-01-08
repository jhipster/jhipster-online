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
import { Component, OnInit } from '@angular/core';

import { GitConfigurationModel } from 'app/core/git/git-configuration.model';
import { GitConfigurationService } from 'app/core/git/git-configuration.service';

@Component({
  selector: 'jhi-github',
  templateUrl: './git.component.html'
})
export class GitComponent implements OnInit {
  gitConfig: GitConfigurationModel;

  githubConfigured = false;
  gitlabConfigured = false;

  constructor(private gitConfigurationService: GitConfigurationService) {
    this.gitConfig = this.gitConfigurationService.gitConfig;
  }

  ngOnInit(): void {
    this.gitlabConfigured = this.gitConfig.gitlabConfigured || false;
    this.githubConfigured = this.gitConfig.githubConfigured || false;
    this.gitConfigurationService.sharedData.subscribe((gitConfig: GitConfigurationModel) => {
      this.gitConfig = gitConfig;
      this.gitlabConfigured = gitConfig.gitlabConfigured || false;
      this.githubConfigured = gitConfig.githubConfigured || false;
    });

    this.gitConfig.availableGitProviders.forEach((provider: any) => {
      this.gitConfigurationService.gitProviderService.getCompanies(provider.toLowerCase()).subscribe(companies => {
        if (companies.length === 0) {
          this.gitConfigurationService.gitProviderService.refreshGitProvider(provider);
        }
      });
    });
  }
}
