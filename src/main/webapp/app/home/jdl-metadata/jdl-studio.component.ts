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
import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JdlMetadataService } from './jdl-metadata.service';
import { JdlMetadata } from './jdl-metadata.model';
import { GithubService } from '../github/github.service';
import { GithubOrganizationModel } from '../generator/github.organization.model';
import { JdlOutputDialogComponent } from './jdl.output.component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { JdlService } from './jdl.service';

@Component({
    selector: 'jhi-jdl-studio-delete',
    templateUrl: './jdl-studio-delete.component.html'
})
export class DeleteJdlStudioComponent implements OnInit, OnDestroy {
    jdlModelName = '';
    jdlId = '';
    private subscription: Subscription;

    constructor(private jdlMetadataService: JdlMetadataService, private route: ActivatedRoute, private router: Router) {}

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.jdlMetadataService.find(params['jdlId']).subscribe(
                (jdlMetadata: JdlMetadata) => {
                    this.jdlId = jdlMetadata.id;
                    this.jdlModelName = jdlMetadata.name;
                },
                (res: any) => console.log(res)
            );
        });
    }

    deleteJdl(jdlId: string) {
        this.jdlMetadataService.delete(jdlId).subscribe(
            () => {
                this.router.navigate(['/design-entities']);
            },
            error => {
                console.log(error);
                this.router.navigate(['/design-entities']);
            }
        );
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }
}

@Component({
    selector: 'jhi-jdl-studio-apply',
    templateUrl: './jdl-studio-apply.component.html'
})
export class ApplyJdlStudioComponent implements OnInit, OnDestroy {
    private subscription: Subscription;

    jdlModelName = '';

    jdlId = '';

    submitted = false;

    gitHubConfigured = true;

    organizations: GithubOrganizationModel[];

    gitHubOrganization: String;

    projects: String[];

    gitHubProject: String;

    baseName: String;

    githubRefresh = false;

    constructor(
        private modalService: NgbModal,
        private jdlMetadataService: JdlMetadataService,
        private route: ActivatedRoute,
        private githubService: GithubService,
        private jdlService: JdlService
    ) {}

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.jdlMetadataService.find(params['jdlId']).subscribe(
                (jdlMetadata: JdlMetadata) => {
                    this.jdlId = jdlMetadata.id;
                    this.jdlModelName = jdlMetadata.name;
                    this.updateGitHubOrganizations();
                },
                (res: any) => console.log(res)
            );
        });
    }

    refreshGithub() {
        this.githubRefresh = true;
        this.githubService.refreshGithub().subscribe(
            () => {
                this.githubRefresh = false;
                this.updateGitHubOrganizations();
            },
            () => {
                this.githubRefresh = false;
            }
        );
    }

    updateGitHubOrganizations() {
        this.githubService.getOrganizations().subscribe(
            orgs => {
                this.organizations = orgs;
                this.gitHubOrganization = orgs[0].name;
                this.gitHubConfigured = true;
                this.updateGitHubProjects(this.gitHubOrganization);
            },
            () => {
                this.gitHubConfigured = false;
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
            }
        );
    }

    applyJdl() {
        this.jdlService.doApplyJdl(this.gitHubOrganization, this.gitHubProject, this.jdlId).subscribe(
            res => {
                this.openOutputModal(res);
                this.submitted = false;
            },
            () => console.log('Error applying the JDL Model.')
        );
    }

    openOutputModal(applyJdlId: String) {
        const modalRef = this.modalService.open(JdlOutputDialogComponent, { size: 'lg', backdrop: 'static' }).componentInstance;

        modalRef.applyJdlId = applyJdlId;
        modalRef.gitHubOrganization = this.gitHubOrganization;
        modalRef.gitHubProject = this.gitHubProject;
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }
}
