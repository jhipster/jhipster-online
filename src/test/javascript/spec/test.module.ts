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
import { DatePipe } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { NgModule, ElementRef, Renderer } from '@angular/core';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiDataUtils, JhiDateUtils, JhiEventManager, JhiAlertService, JhiParseLinks } from 'ng-jhipster';

import { Principal, AccountService, LoginModalService } from 'app/core';
import { MockPrincipal } from './helpers/mock-principal.service';
import { MockAccountService } from './helpers/mock-account.service';
import { MockActivatedRoute, MockRouter } from './helpers/mock-route.service';
import { MockActiveModal } from './helpers/mock-active-modal.service';
import { MockEventManager } from './helpers/mock-event-manager.service';

@NgModule({
    providers: [
        DatePipe,
        JhiDataUtils,
        JhiDateUtils,
        JhiParseLinks,
        {
            provide: JhiEventManager,
            useClass: MockEventManager
        },
        {
            provide: NgbActiveModal,
            useClass: MockActiveModal
        },
        {
            provide: ActivatedRoute,
            useValue: new MockActivatedRoute({ id: 123 })
        },
        {
            provide: Router,
            useClass: MockRouter
        },
        {
            provide: Principal,
            useClass: MockPrincipal
        },
        {
            provide: AccountService,
            useClass: MockAccountService
        },
        {
            provide: LoginModalService,
            useValue: null
        },
        {
            provide: ElementRef,
            useValue: null
        },
        {
            provide: Renderer,
            useValue: null
        },
        {
            provide: JhiAlertService,
            useValue: null
        },
        {
            provide: NgbModal,
            useValue: null
        }
    ],
    imports: [HttpClientTestingModule]
})
export class JhonlineTestModule {}
