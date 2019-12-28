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
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { GeneratorService } from './generator.service';

@Component({
    selector: 'jhi-generator-output-dialog',
    templateUrl: './generator.output.component.html'
})
export class GeneratorOutputDialogComponent implements OnInit {
    logs = '';

    applicationId: string;

    repositoryName: string;

    displayApplicationUrl = false;

    selectedGitProvider: string;
    selectedGitCompany: string;

    githubConfigured = false;
    gitlabConfigured = false;

    gitlabHost: string;
    githubHost: string;

    constructor(private activeModal: NgbActiveModal, private generatorService: GeneratorService) {}

    ngOnInit() {
        this.updateLogsData();
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    updateLogsData() {
        if (this.applicationId === undefined) {
            setTimeout(() => {
                this.updateLogsData();
            }, 500);
        } else {
            this.generatorService.getGenerationData(this.applicationId).subscribe(
                (data: string) => {
                    this.logs += data;
                    if (!data.endsWith('Generation finished\n') && !data.endsWith('Generation failed\n')) {
                        setTimeout(() => {
                            this.updateLogsData();
                        }, 500);
                    } else {
                        if (data.endsWith('Generation finished\n')) {
                            this.displayApplicationUrl = true;
                        }
                    }
                },
                () => {
                    this.logs += 'Server disconnected...';
                }
            );
        }
    }
}
