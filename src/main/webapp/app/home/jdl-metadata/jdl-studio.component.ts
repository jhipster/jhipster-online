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
import { GitProviderService } from '../git/git.service';
import { GitCompanyModel } from 'app/home/generator/git.company.model';
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

    isGithubConfigured = false;

    isGitlabConfigured = false;

    availableGitProviders: any = [];

    companies: GitCompanyModel[];

    selectedGitCompany: string;

    projects: string[];

    gitProject: string;

    baseName: string;

    gitRefresh = false;

    selectedGitProvider: string;

    constructor(
        private modalService: NgbModal,
        private jdlMetadataService: JdlMetadataService,
        private route: ActivatedRoute,
        private gitService: GitProviderService,
        private jdlService: JdlService
    ) {}

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.gitService.getAvailableProviders().subscribe(result => {
                result.forEach(provider => this.refreshGitCompaniesListByGitProvider(provider));
            });
            this.jdlMetadataService.find(params['jdlId']).subscribe(
                (jdlMetadata: JdlMetadata) => {
                    this.jdlId = jdlMetadata.id;
                    this.jdlModelName = jdlMetadata.name;
                },
                (res: any) => console.log(res)
            );
        });
    }

    refreshGitProvider() {
        this.gitRefresh = true;
        this.gitService.refreshGitProvider(this.selectedGitProvider).subscribe(
            () => {
                this.gitRefresh = false;
                this.updateGitProjects(this.selectedGitCompany);
            },
            () => {
                this.gitRefresh = false;
            }
        );
    }

    refreshGitCompaniesListByGitProvider(gitProvider: string) {
        this.gitService.getCompanies(gitProvider).subscribe(
            companies => {
                this.setGitProviderConfigurationStatus(gitProvider, true);
                this.selectedGitProvider = gitProvider;
                this.companies = companies;
                this.selectedGitCompany = companies[0].name;
                this.addToAvailableProviders(gitProvider);
                this.updateGitProjects(this.selectedGitCompany);
            },
            () => {
                this.setGitProviderConfigurationStatus(gitProvider, false);
            }
        );
    }

    updateGitProjects(organizationName: string) {
        this.gitService.getProjects(this.selectedGitProvider, organizationName).subscribe(
            projects => {
                this.setGitProviderConfigurationStatus(this.selectedGitProvider, true);
                this.projects = projects;
                this.gitProject = projects[0];
            },
            () => {
                this.setGitProviderConfigurationStatus(this.selectedGitProvider, false);
            }
        );
    }

    applyJdl() {
        this.jdlService.doApplyJdl(this.selectedGitProvider, this.selectedGitCompany, this.gitProject, this.jdlId).subscribe(
            res => {
                this.openOutputModal(res);
                this.submitted = false;
            },
            err => {
                console.log('Error applying the JDL Model.');
                console.log(err);
            }
        );
    }

    openOutputModal(applyJdlId: String) {
        const modalRef = this.modalService.open(JdlOutputDialogComponent, { size: 'lg', backdrop: 'static' }).componentInstance;

        modalRef.applyJdlId = applyJdlId;
        // FIXME
        modalRef.gitHubOrganization = this.selectedGitCompany;
        // FIXME
        modalRef.gitHubProject = this.gitProject;
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

    private setGitProviderConfigurationStatus(gitProvider: string, status: boolean) {
        if (gitProvider === 'github') {
            this.isGithubConfigured = status;
        } else if (gitProvider === 'gitlab') {
            this.isGitlabConfigured = status;
        }
    }

    private addToAvailableProviders(gitProvider: string) {
        if (!this.availableGitProviders.includes(gitProvider)) {
            this.availableGitProviders.push(gitProvider);
        }
    }
}
