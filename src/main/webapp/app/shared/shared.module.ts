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
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { NgbDateAdapter } from '@ng-bootstrap/ng-bootstrap';

import { NgbDateMomentAdapter } from './util/datepicker-adapter';
import { GoogleAnalyticsEventsService } from './ga/google-analytics-events.service';

import {
    JhonlineSharedLibsModule,
    JhiGitProviderComponent,
    JhiGitProviderAlertComponent,
    JhonlineSharedCommonModule,
    JhiLoginModalComponent,
    HasAnyAuthorityDirective
} from 'app/shared';

@NgModule({
    imports: [JhonlineSharedLibsModule, JhonlineSharedCommonModule, RouterModule],
    declarations: [JhiLoginModalComponent, JhiGitProviderComponent, JhiGitProviderAlertComponent, HasAnyAuthorityDirective],
    providers: [{ provide: NgbDateAdapter, useClass: NgbDateMomentAdapter }, GoogleAnalyticsEventsService],
    entryComponents: [JhiLoginModalComponent],
    exports: [
        JhonlineSharedCommonModule,
        JhiLoginModalComponent,
        JhiGitProviderComponent,
        JhiGitProviderAlertComponent,
        HasAnyAuthorityDirective
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhonlineSharedModule {}
