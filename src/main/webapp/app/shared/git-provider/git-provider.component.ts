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
import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { Router } from '@angular/router';

import { GitProviderService } from 'app/home/git/git.service';

@Component({
    selector: 'jhi-git-provider',
    templateUrl: './git-provider.component.html'
})
export class JhiGitProviderComponent implements OnInit {
    @Output() sharedData = new EventEmitter<any>();

    data: any = {
        selectedGitProvider: null,
        selectedGitCompany: null,
        selectedGitRepository: null,
        availableGitProviders: [],
        gitCompanies: [],
        gitProjects: [],
        isGithubConfigured: false,
        isGitlabConfigured: false,
        baseName: null,
        gitCompanyListRefresh: false,
        gitProjectListRefresh: false
    };

    constructor(private gitService: GitProviderService, public router: Router) {}

    ngOnInit() {
        this.data.gitProjectListRefresh = true;
        this.gitService.getAvailableProviders().subscribe(providers => {
            providers.forEach(provider => this.refreshGitCompanyListByGitProvider(provider));
        });
    }

    refreshGitCompanyListByGitProvider(gitProvider: string) {
        this.data.gitCompanyListRefresh = true;
        this.gitService.getCompanies(gitProvider).subscribe(
            companies => {
                this.setGitProviderConfigurationStatus(gitProvider, true);
                this.data.gitCompanyListRefresh = false;
                this.data.selectedGitProvider = gitProvider;
                this.data.gitCompanies = companies;
                this.data.selectedGitCompany = companies[0].name;
                this.addToAvailableProviderList(gitProvider);
                if (this.router.url === '/generate-application') {
                    this.data = {
                        ...this.data,
                        selectedGitProvider: this.data.selectedGitProvider,
                        selectedGitCompany: this.data.selectedGitCompany
                    };
                    this.sharedData.emit(this.data);
                } else {
                    this.refreshGitProjectList();
                }
            },
            () => {
                this.data.gitCompanyListRefresh = false;
                this.setGitProviderConfigurationStatus(gitProvider, false);
            }
        );
    }

    refreshGitProjectList() {
        this.data.gitProjectListRefresh = true;
        this.gitService.refreshGitProvider(this.data.selectedGitProvider).subscribe(
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
        this.gitService.getProjects(this.data.selectedGitProvider, companyName).subscribe(projects => {
            this.data.gitProjects = projects;
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

    private setGitProviderConfigurationStatus(gitProvider: string, status: boolean) {
        if (gitProvider === 'github') {
            this.data.isGithubConfigured = status;
            this.data = {
                ...this.data,
                isGithubConfigured: this.data.isGithubConfigured
            };
        } else if (gitProvider === 'gitlab') {
            this.data.isGitlabConfigured = status;
            this.data = {
                ...this.data,
                isGitlabConfigured: this.data.isGitlabConfigured
            };
        }
        this.sharedData.emit(this.data);
    }

    private addToAvailableProviderList(gitProvider: string) {
        if (!this.data.availableGitProviders.includes(gitProvider)) {
            this.data.availableGitProviders.push(gitProvider);
        }
    }
}
