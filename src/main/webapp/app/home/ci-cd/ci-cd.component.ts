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
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { GitConfigurationModel } from 'app/core/git/git-configuration.model';
import { GitConfigurationService } from 'app/core/git/git-configuration.service';

import { CiCdOutputDialogComponent } from './ci-cd.output.component';
import { CiCdService } from './ci-cd.service';

@Component({
  selector: 'jhi-generator',
  templateUrl: './ci-cd.component.html'
})
export class CiCdComponent implements OnInit {
  submitted = false;

  ciCdId = '';

  ciCdTool = 'travis';

  selectedGitProvider: string | undefined;
  selectedGitCompany: string | undefined;
  selectedGitRepository: string | undefined;

  isGitProviderComponentValid = false;

  githubConfigured = false;
  gitlabConfigured = false;

  gitConfig: GitConfigurationModel | undefined;

  constructor(private modalService: NgbModal, private gitConfigurationService: GitConfigurationService, private ciCdService: CiCdService) {}

  ngOnInit(): void {
    this.gitConfig = this.gitConfigurationService.gitConfig;
    if (this.gitConfig) {
      this.gitlabConfigured = this.gitConfig.gitlabConfigured || false;
      this.githubConfigured = this.gitConfig.githubConfigured || false;
    }

    this.gitConfigurationService.sharedData.subscribe((gitConfig: GitConfigurationModel) => {
      this.gitConfig = gitConfig;
      this.gitlabConfigured = gitConfig.gitlabConfigured || false;
      this.githubConfigured = gitConfig.githubConfigured || false;
    });
  }

  updateSharedData(data: any): void {
    this.selectedGitProvider = data.selectedGitProvider;
    this.selectedGitCompany = data.selectedGitCompany;
    this.selectedGitRepository = data.selectedGitRepository;
    this.isGitProviderComponentValid = data.isValid;
  }

  applyCiCd(): void {
    if (this.selectedGitProvider && this.selectedGitCompany && this.selectedGitRepository) {
      this.ciCdService.addCiCd(this.selectedGitProvider, this.selectedGitCompany, this.selectedGitRepository, this.ciCdTool).subscribe(
        (res: any) => {
          this.openOutputModal(res);
          this.submitted = false;
        },
        // eslint-disable-next-line no-console
        () => console.log('Error configuring CI/CD.')
      );
    }
  }

  openOutputModal(ciCdId: string): void {
    const modalRef = this.modalService.open(CiCdOutputDialogComponent, { size: 'lg', backdrop: 'static' }).componentInstance;

    modalRef.ciCdId = ciCdId;
    modalRef.ciCdTool = this.ciCdTool;
    modalRef.gitlabHost = this.gitConfig!.gitlabHost;
    modalRef.githubHost = this.gitConfig!.githubHost;
    modalRef.selectedGitProvider = this.selectedGitProvider;
    modalRef.selectedGitCompany = this.selectedGitCompany;
    modalRef.selectedGitRepository = this.selectedGitRepository;
  }

  isAtLeastOneGitProviderAvailableAndConfigured(): boolean {
    return (
      (this.gitConfig &&
        ((this.gitConfig.githubAvailable && this.githubConfigured) || (this.gitConfig.gitlabAvailable && this.gitlabConfigured))) ||
      false
    );
  }
}
