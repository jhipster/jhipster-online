/**
 * Copyright 2017-2020 the original author or authors from the JHipster Online project.
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

import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Router } from '@angular/router';

import { GitConfigurationService, GitProviderModel } from 'app/core';

@Component({
    selector: 'jhi-git-provider-alert',
    templateUrl: './git-provider-alert.component.html'
})
export class JhiGitProviderAlertComponent implements OnInit {
    gitConfig: any;

    @Input() tab: string;

    warningMessage: string;
    infoMessage: string;
    noGitProvidersMessage: string;

    displayedGitProvider: string;

    githubConfigured = false;
    gitlabConfigured = false;

    constructor(private gitConfigurationService: GitConfigurationService) {}

    ngOnInit() {
        this.gitConfig = this.gitConfigurationService.gitConfig;
        this.gitlabConfigured = this.gitConfig.gitlabConfigured;
        this.githubConfigured = this.gitConfig.githubConfigured;
        this.gitConfigurationService.sharedData.subscribe(gitConfig => {
            this.gitConfig = gitConfig;
            this.gitlabConfigured = gitConfig.gitlabConfigured;
            this.githubConfigured = gitConfig.githubConfigured;
        });
        this.updateGitProviderName();
        this.noGitProvidersMessage = `There is no Git provider available, please contact your administrator to configure one.`;

        if (this.tab === 'ci-cd') {
            this.warningMessage = ` To configure Continuous Integration/Continuous Deployment on your ${this.displayedGitProvider} project,
                you must authorize JHipster Online to access your ${this.displayedGitProvider} account.`;
            this.infoMessage = ` will access your project's ${this.displayedGitProvider} repository and create a new branch
                with Continuous Integration configuration. You can then decide if you want to merge this branch into your master branch.`;
        } else if (this.tab === 'generate-application') {
            this.warningMessage = ` To generate your application on ${
                this.displayedGitProvider
            }, you must authorize JHipster Online to access
                your ${this.displayedGitProvider} account. You will only be able to download your application as a Zip file.`;
            this.infoMessage = ` will create a new ${this.displayedGitProvider} repository,
                and will push the generated project in that repository.`;
            this.noGitProvidersMessage = `${this.noGitProvidersMessage} You will only be able to download your application as a Zip file.`;
        } else if (this.tab === 'design-entities-apply') {
            this.warningMessage = ` To apply a JDL Model on a ${
                this.displayedGitProvider
            } project, you must authorize JHipster Online to access
                your ${this.displayedGitProvider} account.`;
            this.infoMessage = ` will access your project's ${this.displayedGitProvider} repository and create a new branch with this model.
                You can then decide if you want to merge this branch into your master branch.`;
        }
    }

    isAlertShowing() {
        return (this.gitConfig.githubAvailable || this.gitConfig.gitlabAvailable) && !this.githubConfigured && !this.gitlabConfigured;
    }

    isAtLeastOneGitProviderAvailableAndConfigured() {
        return (this.gitConfig.githubAvailable && this.githubConfigured) || (this.gitConfig.githubAvailable && this.gitlabConfigured);
    }

    isNoGitProviders() {
        return !this.gitConfig.githubAvailable && !this.gitConfig.gitlabAvailable;
    }

    updateGitProviderName() {
        if (this.gitConfig.githubAvailable && this.gitConfig.gitlabAvailable) {
            this.displayedGitProvider = 'GitHub or GitLab';
        } else if (this.gitConfig.githubAvailable && !this.gitConfig.gitlabAvailable) {
            this.displayedGitProvider = 'GitHub';
        } else if (!this.gitConfig.githubAvailable && this.gitConfig.gitlabAvailable) {
            this.displayedGitProvider = 'GitLab';
        }
    }
}

@Component({
    selector: 'jhi-git-provider',
    templateUrl: './git-provider.component.html'
})
export class JhiGitProviderComponent implements OnInit {
    @Output() sharedData = new EventEmitter<any>();

    @Input() simpleMode = false;

    data: GitProviderModel;

    gitConfig: any;

    githubConfigured = false;
    gitlabConfigured = false;

    constructor(private gitConfigurationService: GitConfigurationService, public router: Router) {}

    ngOnInit() {
        this.newGitProviderModel();
        this.gitConfig = this.gitConfigurationService.gitConfig;
        this.gitlabConfigured = this.gitConfig.gitlabConfigured;
        this.githubConfigured = this.gitConfig.githubConfigured;
        this.gitConfigurationService.sharedData.subscribe(gitConfig => {
            this.gitConfig = gitConfig;
            this.gitlabConfigured = gitConfig.gitlabConfigured;
            this.githubConfigured = gitConfig.githubConfigured;
            this.updateAvailableProviders();
        });

        this.updateAvailableProviders();
    }

    updateAvailableProviders() {
        if (this.gitConfig.gitlabAvailable && this.gitlabConfigured && !this.data.availableGitProviders.includes('GitLab')) {
            this.data.availableGitProviders.push('GitLab');
            this.data.selectedGitProvider = 'GitLab';
        }
        if (this.gitConfig.githubAvailable && this.githubConfigured && !this.data.availableGitProviders.includes('GitHub')) {
            this.data.availableGitProviders.push('GitHub');
            this.data.selectedGitProvider = 'GitHub';
        }

        this.refreshGitCompanyListByGitProvider(this.data.selectedGitProvider || '');
    }
    refreshGitCompanyListByGitProvider(gitProvider: string) {
        if (gitProvider.length === 0) {
            return;
        }
        this.data.gitCompanyListRefresh = true;
        this.gitConfigurationService.gitProviderService.getCompanies(gitProvider.toLowerCase()).subscribe(
            companies => {
                this.data.gitCompanyListRefresh = false;
                this.data.gitCompanies = companies;
                this.data.selectedGitCompany = companies[0].name;
                if (this.simpleMode) {
                    this.data = {
                        ...this.data,
                        selectedGitProvider: this.data.selectedGitProvider,
                        selectedGitCompany: this.data.selectedGitCompany
                    };
                    this.emitSharedData();
                } else {
                    this.updateGitProjectList(this.data.selectedGitCompany);
                    this.emitSharedData();
                }
            },
            () => {
                this.data.gitCompanyListRefresh = false;
            }
        );
    }

    refreshGitProjectList() {
        this.data.gitProjectListRefresh = true;
        this.emitSharedData();
        this.gitConfigurationService.gitProviderService.refreshGitProvider(this.data.selectedGitProvider).subscribe(
            () => {
                this.data.gitProjectListRefresh = false;
                this.updateGitProjectList(this.data.selectedGitCompany);
            },
            () => {
                this.data.gitProjectListRefresh = false;
            }
        );
    }

    updateGitProjectList(companyName: string) {
        this.data.gitProjects = null;
        this.gitConfigurationService.gitProviderService.getProjects(this.data.selectedGitProvider, companyName).subscribe(projects => {
            this.data.gitProjects = projects.sort();
            this.data.selectedGitRepository = projects[0];
            this.data = {
                ...this.data,
                selectedGitProvider: this.data.selectedGitProvider,
                selectedGitCompany: this.data.selectedGitCompany,
                selectedGitRepository: this.data.selectedGitRepository
            };
            this.emitSharedData();
        });
    }

    updateSelectedGitRepository(gitRepository: string) {
        this.sharedData.emit({ ...this.data, selectedGitRepository: gitRepository });
    }

    emitSharedData() {
        this.data.isValid = !this.isRefreshing() && !this.simpleMode && this.isGitProjectsEmpty(); // There's available project when not in simple mode
        this.sharedData.emit(this.data);
    }

    isGitProjectsEmpty() {
        return this.data.gitProjects && this.data.gitProjects.length > 0;
    }

    isRefreshing() {
        return this.data.gitCompanyListRefresh || this.data.gitProjectListRefresh;
    }

    private newGitProviderModel() {
        this.data = new GitProviderModel([], null, null, null, [], [], false, false, false);
    }
}
