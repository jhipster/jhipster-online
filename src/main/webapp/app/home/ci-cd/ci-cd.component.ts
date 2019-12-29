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
import { Component, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { GitConfigurationModel, GitConfigurationService } from 'app/core';
import { CiCdOutputDialogComponent } from './ci-cd.output.component';
import { CiCdService } from './ci-cd.service';

@Component({
    selector: 'jhi-generator',
    templateUrl: './ci-cd.component.html',
    styleUrls: ['ci-cd.scss']
})
export class CiCdComponent implements OnInit {
    submitted = false;

    ciCdId = '';

    ciCdTool = 'travis';

    selectedGitProvider: string;
    selectedGitCompany: string;
    selectedGitRepository: string;

    isGitProviderComponentValid = false;

    githubConfigured = false;
    gitlabConfigured = false;

    gitConfig: GitConfigurationModel;

    constructor(
        private modalService: NgbModal,
        private gitConfigurationService: GitConfigurationService,
        private ciCdService: CiCdService
    ) {}

    ngOnInit() {
        this.gitConfig = this.gitConfigurationService.gitConfig;
        this.gitlabConfigured = this.gitConfig.gitlabConfigured;
        this.githubConfigured = this.gitConfig.githubConfigured;
        this.gitConfigurationService.sharedData.subscribe(gitConfig => {
            this.gitlabConfigured = gitConfig.gitlabConfigured;
            this.githubConfigured = gitConfig.githubConfigured;
        });
    }

    updateSharedData(data: any) {
        this.selectedGitProvider = data.selectedGitProvider;
        this.selectedGitCompany = data.selectedGitCompany;
        this.selectedGitRepository = data.selectedGitRepository;
        this.isGitProviderComponentValid = data.isValid;
    }

    applyCiCd() {
        this.ciCdService.addCiCd(this.selectedGitProvider, this.selectedGitCompany, this.selectedGitRepository, this.ciCdTool).subscribe(
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
        modalRef.gitlabHost = this.gitConfig.gitlabHost;
        modalRef.githubHost = this.gitConfig.githubHost;
        modalRef.selectedGitProvider = this.selectedGitProvider;
        modalRef.selectedGitCompany = this.selectedGitCompany;
        modalRef.selectedGitRepository = this.selectedGitRepository;
    }

    isAtLeastOneGitProviderAvailableAndConfigured() {
        return (this.gitConfig.githubAvailable && this.githubConfigured) || (this.gitConfig.githubAvailable && this.gitlabConfigured);
    }
}
