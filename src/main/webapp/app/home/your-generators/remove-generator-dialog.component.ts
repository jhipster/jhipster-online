/**
 * Copyright 2017-2023 the original author or authors from the JHipster project.
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
  selector: 'jhi-remove-generator-dialog',
  templateUrl: './remove-generator-dialog.component.html'
})
export class RemoveGeneratorDialogComponent {
  generatorId: string | undefined;

  constructor(
    private generatorIdentityService: GeneratorIdentityService,
    private eventManager: JhiEventManager,
    private activeModal: NgbActiveModal
  ) {}

  confirmUnbind(): void {
    if (this.generatorId) {
      this.generatorIdentityService.unbind(this.generatorId).subscribe(() => {
        this.eventManager.broadcast({
          name: 'generatorIdentityListModification',
          content: 'Remove a generatorIdentity'
        });
        this.activeModal.close();
      });
    }
  }

  clear(): void {
    this.activeModal.dismiss('cancel');
  }
}
