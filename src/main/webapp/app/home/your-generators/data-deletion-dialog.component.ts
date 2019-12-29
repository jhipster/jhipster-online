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
import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { JhiEventManager } from 'ng-jhipster';

import { GeneratorIdentityService } from './generator-identity.service';

@Component({
    selector: 'jhi-data-deletion',
    templateUrl: './data-deletion-dialog.component.html'
})
export class DataDeletionDialogComponent {
    constructor(
        private generatorIdentityService: GeneratorIdentityService,
        private eventManager: JhiEventManager,
        private activeModal: NgbActiveModal
    ) {}

    confirmDeletion() {
        this.generatorIdentityService.deleteStatistics().subscribe(() => {
            this.eventManager.broadcast({
                name: 'statisticsModification',
                content: 'Delete all user related statistics'
            });
            this.activeModal.close();
        });
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }
}
