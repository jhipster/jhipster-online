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
import { GithubService } from '../github/github.service';
import { GithubOrganizationModel } from '../generator/github.organization.model';

@Component({
    selector: 'jhi-generator',
    templateUrl: './ci-cd.component.html',
    styleUrls: ['ci-cd.scss']
})
export class CiCdComponent implements OnInit {
    submitted = false;

    ciCdId = '';

    gitHubConfigured = true;

    organizations: GithubOrganizationModel[];

    gitHubOrganization: String;

    projects: String[];

    gitHubProject: String;

    baseName: String;

    githubRefresh = false;

    ciCdTool = 'travis';

    constructor(private modalService: NgbModal, private githubService: GithubService, private ciCdService: CiCdService) {}

    ngOnInit() {
        this.updateGitHubOrganizations();
    }

    refreshGithub() {
        this.githubRefresh = true;
        this.githubService.refreshGithub().subscribe(
            () => {
                this.updateGitHubOrganizations();
            },
            () => {
                this.githubRefresh = false;
            }
        );
    }

    updateGitHubOrganizations() {
        this.githubRefresh = false;
        this.githubService.getOrganizations().subscribe(
            orgs => {
                this.organizations = orgs;
                this.gitHubOrganization = orgs[0].name;
                this.gitHubConfigured = true;
                this.updateGitHubProjects(this.gitHubOrganization);
            },
            () => {
                this.gitHubConfigured = false;
                this.githubRefresh = false;
            }
        );
    }

    updateGitHubProjects(organizationName: String) {
        this.githubService.getProjects(organizationName).subscribe(
            projects => {
                this.projects = projects;
                this.gitHubProject = projects[0];
                this.gitHubConfigured = true;
            },
            () => {
                this.gitHubConfigured = false;
                this.githubRefresh = false;
            }
        );
    }

    applyCiCd() {
        this.ciCdService.addCiCd(this.gitHubOrganization, this.gitHubProject, this.ciCdTool).subscribe(
            res => {
                this.openOutputModal(res);
                this.submitted = false;
            },
            () => console.log('Error configuring CI/CD.')
        );
    }

    openOutputModal(ciCdId: String) {
        const modalRef = this.modalService.open(CiCdOutputDialogComponent, { size: 'lg', backdrop: 'static' }).componentInstance;

        modalRef.ciCdId = ciCdId;
        modalRef.ciCdTool = this.ciCdTool;
        modalRef.gitHubOrganization = this.gitHubOrganization;
        modalRef.gitHubProject = this.gitHubProject;
    }
}
