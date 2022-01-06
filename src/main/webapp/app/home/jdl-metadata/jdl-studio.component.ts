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
import { Component, OnDestroy, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ActivatedRoute, Router } from '@angular/router';

import { GitConfigurationModel } from 'app/core/git/git-configuration.model';
import { GitConfigurationService } from 'app/core/git/git-configuration.service';

import { JdlMetadataService } from './jdl-metadata.service';
import { JdlMetadata } from './jdl-metadata.model';
import { JdlOutputDialogComponent } from './jdl.output.component';
import { JdlService } from './jdl.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'jhi-jdl-studio-delete',
  templateUrl: './jdl-studio-delete.component.html'
})
export class DeleteJdlStudioComponent implements OnInit, OnDestroy {
  jdlModelName: string | undefined = '';
  jdlId = '';
  private subscription: Subscription | undefined;

  constructor(private jdlMetadataService: JdlMetadataService, private route: ActivatedRoute, private router: Router) {}

  ngOnInit(): void {
    this.subscription = this.route.params.subscribe(params => {
      this.jdlMetadataService.find(params['jdlId']).subscribe(
        (jdlMetadata: any) => {
          this.jdlId = jdlMetadata.id;
          this.jdlModelName = jdlMetadata.name;
        },
        // eslint-disable-next-line no-console
        (res: any) => console.log(res)
      );
    });
  }

  deleteJdl(jdlId: string): void {
    this.jdlMetadataService.delete(jdlId).subscribe(
      () => {
        this.router.navigate(['/design-entities']);
      },
      error => {
        // eslint-disable-next-line no-console
        console.log(error);
        this.router.navigate(['/design-entities']);
      }
    );
  }

  ngOnDestroy(): void {
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
  }
}

@Component({
  selector: 'jhi-jdl-studio-apply',
  templateUrl: './jdl-studio-apply.component.html'
})
export class ApplyJdlStudioComponent implements OnInit, OnDestroy {
  private subscription: Subscription | undefined;

  jdlModelName: string | undefined = '';

  jdlId: string | undefined = '';

  submitted = false;

  selectedGitProvider: string | undefined;
  selectedGitCompany: string | undefined;
  selectedGitRepository: string | undefined;

  githubConfigured = false;
  gitlabConfigured = false;

  gitConfig: GitConfigurationModel;

  isGitProviderComponentValid = false;

  constructor(
    private modalService: NgbModal,
    private jdlMetadataService: JdlMetadataService,
    private route: ActivatedRoute,
    private gitConfigurationService: GitConfigurationService,
    private jdlService: JdlService
  ) {
    this.gitConfig = this.gitConfigurationService.gitConfig;
    this.gitlabConfigured = this.gitConfig.gitlabConfigured || false;
    this.githubConfigured = this.gitConfig.githubConfigured || false;
  }

  ngOnInit(): void {
    this.gitConfigurationService.sharedData.subscribe((gitConfig: GitConfigurationModel) => {
      this.gitConfig = gitConfig;
      this.gitlabConfigured = gitConfig.gitlabConfigured || false;
      this.githubConfigured = gitConfig.githubConfigured || false;
    });

    this.subscription = this.route.params.subscribe(params => {
      this.jdlMetadataService.find(params['jdlId']).subscribe(
        (jdlMetadata: JdlMetadata) => {
          this.jdlId = jdlMetadata.id;
          this.jdlModelName = jdlMetadata.name;
        },
        // eslint-disable-next-line no-console
        (res: any) => console.log(res)
      );
    });
  }

  updateSharedData(data: any): void {
    this.selectedGitProvider = data.selectedGitProvider;
    this.selectedGitCompany = data.selectedGitCompany;
    this.selectedGitRepository = data.selectedGitRepository;
    this.isGitProviderComponentValid = data.isValid;
  }

  applyJdl(): void {
    if (this.selectedGitProvider && this.selectedGitCompany && this.selectedGitRepository && this.jdlId) {
      this.jdlService.doApplyJdl(this.selectedGitProvider, this.selectedGitCompany, this.selectedGitRepository, this.jdlId).subscribe(
        res => {
          this.openOutputModal(res);
          this.submitted = false;
        },
        err => {
          // eslint-disable-next-line no-console
          console.log('Error applying the JDL Model.');
          // eslint-disable-next-line no-console
          console.log(err);
        }
      );
    }
  }

  openOutputModal(applyJdlId: string): void {
    const modalRef = this.modalService.open(JdlOutputDialogComponent, { size: 'lg', backdrop: 'static' }).componentInstance;

    modalRef.applyJdlId = applyJdlId;
    modalRef.gitlabHost = this.gitConfig.gitlabHost;
    modalRef.githubHost = this.gitConfig.githubHost;
    modalRef.selectedGitProvider = this.selectedGitProvider;
    modalRef.selectedGitCompany = this.selectedGitCompany;
    modalRef.selectedGitRepository = this.selectedGitRepository;
  }

  ngOnDestroy(): void {
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
  }

  isAtLeastOneGitProviderAvailableAndConfigured(): boolean {
    return (this.gitConfig.githubAvailable && this.githubConfigured) || (this.gitConfig.gitlabAvailable && this.gitlabConfigured) || false;
  }
}
