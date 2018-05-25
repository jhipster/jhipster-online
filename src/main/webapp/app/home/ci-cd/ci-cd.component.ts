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
import { CiCdService } from './ci-cd.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { CiCdOutputDialogComponent } from './ci-cd.output.component';
import { GitProviderService } from '../git/github.service';
import { GitCompanyModel } from 'app/home/generator/git.company.model';

@Component({
    selector: 'jhi-generator',
    templateUrl: './ci-cd.component.html',
    styleUrls: ['ci-cd.scss']
})
export class CiCdComponent implements OnInit {
    submitted = false;

    ciCdId = '';

    isGithubConfigured = true;

    isGitlabConfigured = true;

    selectedGitCompany: string;

    availableGitProvider = [];

    gitCompanies: GitCompanyModel[];

    gitCompany: string;

    projects: string[];

    gitProject: string;

    baseName: string;

    gitProviderRefresh = false;

    ciCdTool = 'travis';

    selectedGitProvider: string = '';

    constructor(private modalService: NgbModal, private gitService: GitProviderService, private ciCdService: CiCdService) {}

    ngOnInit() {
        this.gitProviderRefresh = true;
        this.gitService.getAvailableProviders().subscribe(result => {
            this.availableGitProvider = result;
            this.availableGitProvider.forEach(provider => {
                if (provider === 'gitlab') {
                    this.refreshGitCompaniesListByGitProvider('gitlab', success => (this.isGitlabConfigured = success));
                } else if (provider === 'github') {
                    this.refreshGitCompaniesListByGitProvider('github', success => (this.isGithubConfigured = success));
                }
            });
            this.selectedGitProvider = result[0];
        });
    }

    refreshGitProjectList() {
        this.gitProviderRefresh = true;
        console.log(this.selectedGitProvider);
        this.gitService.refreshGithub(this.selectedGitProvider).subscribe(
            () => {
                this.gitProviderRefresh = false;
            },
            () => {
                this.gitProviderRefresh = false;
            }
        );
    }

    refreshGitCompaniesListByGitProvider(gitProvider: string, callback: Function) {
        this.gitProviderRefresh = false;
        this.gitService.getCompanies(gitProvider).subscribe(
            companies => {
                this.selectedGitCompany = companies[0].name;
                this.gitCompanies = companies;
                this.updateGitProjects(this.selectedGitCompany);
                callback(true);
            },
            () => callback(false)
        );
    }

    updateGitProjects(companyName: string) {
        this.gitService.getProjects(this.selectedGitProvider, companyName).subscribe(
            projects => {
                this.projects = projects;
                this.gitProject = projects[0];
                if (this.selectedGitProvider === 'gitlab') {
                    this.isGitlabConfigured = true;
                } else if (this.selectedGitProvider === 'github') {
                    this.isGithubConfigured = true;
                }
            },
            () => {
                // TODO
            }
        );
    }

    applyCiCd() {
        this.ciCdService.addCiCd(this.gitCompany, this.gitProject, this.ciCdTool).subscribe(
            res => {
                this.openOutputModal(res);
                this.submitted = false;
            },
            () => console.log('Error configuring CI/CD.')
        );
    }

    openOutputModal(ciCdId: string) {
        const modalRef = this.modalService.open(CiCdOutputDialogComponent, { size: 'lg', backdrop: 'static' }).componentInstance;

        modalRef.ciCdId = ciCdId;
        modalRef.ciCdTool = this.ciCdTool;
        modalRef.gitCompany = this.gitCompany;
        modalRef.gitProject = this.gitProject;
    }
}
