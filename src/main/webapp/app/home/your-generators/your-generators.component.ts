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

import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IGeneratorIdentity } from 'app/shared/model/generator-identity.model';
import { GeneratorIdentityService } from './generator-identity.service';
import { RemoveGeneratorDialogComponent } from 'app/home/your-generators/remove-generator-dialog.component';
import { DataDeletionDialogComponent } from 'app/home/your-generators/data-deletion-dialog.component';
import { Principal } from 'app/core/auth/principal.service';

@Component({
  selector: 'jhi-your-generators',
  templateUrl: './your-generators.component.html',
  styleUrls: ['your-generators.scss']
})
export class YourGeneratorsComponent implements OnInit, OnDestroy {
  generatorIdentities?: IGeneratorIdentity[] | null;
  currentAccount: any;
  eventSubscriber?: Subscription;

  constructor(
    private generatorIdentityService: GeneratorIdentityService,
    private jhiAlertService: JhiAlertService,
    private eventManager: JhiEventManager,
    private principal: Principal,
    private modalService: NgbModal
  ) {}

  refresh(): void {
    this.generatorIdentityService.query().subscribe(
      (res: HttpResponse<IGeneratorIdentity[]>) => {
        this.generatorIdentities = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  ngOnInit(): void {
    this.refresh();
    this.principal.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInGeneratorIdentities();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) this.eventManager.destroy(this.eventSubscriber);
  }

  openUnbindModal(generatorId: string): void {
    const modalRef = this.modalService.open(RemoveGeneratorDialogComponent, { size: 'lg', backdrop: 'static' }).componentInstance;

    modalRef.generatorId = generatorId;
  }

  openDataDeletionModal(): void {
    this.modalService.open(DataDeletionDialogComponent, { size: 'lg', backdrop: 'static' });
  }

  trackId(index: number, item: IGeneratorIdentity): number | undefined {
    return item.id;
  }

  registerChangeInGeneratorIdentities(): void {
    this.eventSubscriber = this.eventManager.subscribe('generatorIdentityListModification', () => this.refresh());
  }

  private onError(errorMessage: string): void {
    this.jhiAlertService.error(errorMessage, null, '');
  }
}
