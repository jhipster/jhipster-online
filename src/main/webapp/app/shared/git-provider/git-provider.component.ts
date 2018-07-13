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

    displayedGitProvider: string;

    isGithubConfigured: boolean = JSON.parse(localStorage.getItem('isGithubConfigured'));
    isGitlabConfigured: boolean = JSON.parse(localStorage.getItem('isGitlabConfigured'));

    constructor(private gitConfigurationService: GitConfigurationService) {}

    ngOnInit() {
        this.gitConfig = this.gitConfigurationService.gitConfig;
        this.updateGitProviderName();

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
        return (
            (this.gitConfig.isGithubAvailable || this.gitConfig.isGitlabAvailable) && !this.isGithubConfigured && !this.isGitlabConfigured
        );
    }

    private updateGitProviderName() {
        if (this.gitConfig.isGithubAvailable && this.gitConfig.isGitlabAvailable) {
            this.displayedGitProvider = 'GitHub/GitLab';
        } else if (this.gitConfig.isGithubAvailable && !this.gitConfig.isGitlabAvailable) {
            this.displayedGitProvider = 'GitHub';
        } else if (!this.gitConfig.isGithubAvailable && this.gitConfig.isGitlabAvailable) {
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

    isGithubConfigured: boolean = JSON.parse(localStorage.getItem('isGithubConfigured'));
    isGitlabConfigured: boolean = JSON.parse(localStorage.getItem('isGitlabConfigured'));

    constructor(private gitConfigurationService: GitConfigurationService, public router: Router) {}

    ngOnInit() {
        this.newGitProviderModel();
        this.gitConfig = this.gitConfigurationService.gitConfig;

        if (this.gitConfig.isGithubAvailable && this.isGithubConfigured) {
            this.data.availableGitProviders.push('GitHub');
            this.data.selectedGitProvider = 'GitHub';
        }
        if (this.gitConfig.isGitlabAvailable && this.isGitlabConfigured) {
            this.data.availableGitProviders.push('GitLab');
            this.data.selectedGitProvider = 'GitLab';
        }

        this.refreshGitCompanyListByGitProvider(this.data.selectedGitProvider);
    }

    refreshGitCompanyListByGitProvider(gitProvider: string) {
        this.data.gitCompanyListRefresh = true;
        this.gitConfigurationService.gitProviderService.getCompanies(gitProvider).subscribe(
            companies => {
                this.data.gitCompanyListRefresh = false;
                this.data.gitCompanies = companies;
                this.data.selectedGitCompany = companies[0].name;
                this.addToAvailableProviderList(gitProvider);
                if (this.simpleMode) {
                    this.data = {
                        ...this.data,
                        selectedGitProvider: this.data.selectedGitProvider,
                        selectedGitCompany: this.data.selectedGitCompany
                    };
                    this.sharedData.emit(this.data);
                } else {
                    this.updateGitProjectList(this.data.selectedGitCompany);
                    this.sharedData.emit(this.data);

                }
            },
            () => {
                this.data.gitCompanyListRefresh = false;
            }
        );
    }

    refreshGitProjectList() {
        this.data.gitProjectListRefresh = true;
        this.sharedData.emit(this.data);
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
            this.sharedData.emit(this.data);
        });
    }

    updateSelectedGitRepository(gitRepository: string) {
        this.sharedData.emit({ ...this.data, selectedGitRepository: gitRepository });
    }

    isRefreshing() {
        return this.data.gitCompanyListRefresh || this.data.gitProjectListRefresh;
    }

    private newGitProviderModel() {
        this.data = new GitProviderModel([], null, null, null, [], [], false, false);
    }
}
