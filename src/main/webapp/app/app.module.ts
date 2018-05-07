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
import './vendor.ts';

import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { Ng2Webstorage } from 'ng2-webstorage';
import { NavigationEnd, Router } from '@angular/router';

import { JhonlineSharedModule, UserRouteAccessService } from './shared';
import { JhonlineHomeModule } from './home/home.module';
import { JhonlineAdminModule } from './admin/admin.module';
import { JhonlineAccountModule } from './account/account.module';
import { JhonlineEntityModule } from './entities/entity.module';
import { GoogleAnalyticsEventsService } from './shared/ga/google-analytics-events.service';

import { customHttpProvider } from './blocks/interceptor/http.provider';
import { PaginationConfig } from './blocks/config/uib-pagination.config';

// jhipster-needle-angular-add-module-import JHipster will add new module here

import {
    JhiMainComponent,
    LayoutRoutingModule,
    NavbarComponent,
    FooterComponent,
    ProfileService,
    PageRibbonComponent,
    ErrorComponent
} from './layouts';

@NgModule({
    imports: [
        BrowserModule,
        LayoutRoutingModule,
        Ng2Webstorage.forRoot({ prefix: 'jhi', separator: '-'}),
        JhonlineSharedModule,
        JhonlineHomeModule,
        JhonlineAdminModule,
        JhonlineAccountModule,
        JhonlineEntityModule,
        // jhipster-needle-angular-add-module JHipster will add new module here
    ],
    declarations: [
        JhiMainComponent,
        NavbarComponent,
        ErrorComponent,
        PageRibbonComponent,
        FooterComponent
    ],
    providers: [
        ProfileService,
        customHttpProvider(),
        PaginationConfig,
        UserRouteAccessService
    ],
    bootstrap: [ JhiMainComponent ]
})
export class JhonlineAppModule {

    constructor(public router: Router, public googleAnalyticsEventsService: GoogleAnalyticsEventsService) {
        this.router.events.subscribe((event) => {
            if (event instanceof NavigationEnd) {
                ga('set', 'page', 'start' + event.urlAfterRedirects);
                ga('send', 'pageview');
            }
        });
    }
}
