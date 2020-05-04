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
import './vendor.ts';

import { NgModule, Injector, APP_INITIALIZER } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { LocalStorageService, NgxWebstorageModule, SessionStorageService } from 'ngx-webstorage';
import { NavigationEnd, Router } from '@angular/router';
import { NgxEchartsModule } from 'ngx-echarts';
import { JhiEventManager, NgJhipsterModule } from 'ng-jhipster';
import { fas } from '@fortawesome/free-solid-svg-icons';
import { fab } from '@fortawesome/free-brands-svg-icons';

import { AuthInterceptor } from './blocks/interceptor/auth.interceptor';
import { AuthExpiredInterceptor } from './blocks/interceptor/auth-expired.interceptor';
import { ErrorHandlerInterceptor } from './blocks/interceptor/errorhandler.interceptor';
import { NotificationInterceptor } from './blocks/interceptor/notification.interceptor';
import { JhonlineAppRoutingModule } from './app-routing.module';
import { JhonlineEntityModule } from './entities/entity.module';
import { GoogleAnalyticsEventsService } from './shared/ga/google-analytics-events.service';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { GitConfigurationService } from 'app/core/git/git-configuration.service';
import { JhonlineSharedModule } from 'app/shared/shared.module';
import { JhonlineHomeModule } from 'app/home/home.module';
import { JhonlineCoreModule } from 'app/core/core.module';
import { NavbarComponent } from 'app/layouts/navbar/navbar.component';
import { ErrorComponent } from 'app/layouts/error/error.component';
import { PageRibbonComponent } from 'app/layouts/profiles/page-ribbon.component';
import { FooterComponent } from 'app/layouts/footer/footer.component';
import { MainComponent } from 'app/layouts/main/main.component';
import { AccountModule } from 'app/account/account.module';
import { FaIconLibrary, FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { NgbCollapseModule } from '@ng-bootstrap/ng-bootstrap';

export function setupGitConfiguration(gitConfigurationService: GitConfigurationService): any {
  return () => gitConfigurationService.setupGitConfiguration();
}

@NgModule({
  imports: [
    BrowserModule,
    JhonlineAppRoutingModule,
    NgxWebstorageModule.forRoot({ prefix: 'jhi', separator: '-' }),
    JhonlineSharedModule,
    JhonlineCoreModule,
    JhonlineHomeModule,
    AccountModule,
    JhonlineEntityModule,
    NgxEchartsModule,
    FontAwesomeModule,
    NgbCollapseModule,
    NgJhipsterModule
    // jhipster-needle-angular-add-module JHipster will add new module here
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true,
      deps: [LocalStorageService, SessionStorageService]
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthExpiredInterceptor,
      multi: true,
      deps: [Injector]
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: ErrorHandlerInterceptor,
      multi: true,
      deps: [JhiEventManager]
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: NotificationInterceptor,
      multi: true,
      deps: [Injector]
    },
    GitConfigurationService,
    {
      provide: APP_INITIALIZER,
      useFactory: setupGitConfiguration,
      deps: [GitConfigurationService],
      multi: true
    }
  ],
  bootstrap: [MainComponent]
})
export class JhonlineAppModule {
  constructor(public router: Router, public googleAnalyticsEventsService: GoogleAnalyticsEventsService, library: FaIconLibrary) {
    library.addIconPacks(fas, fab);
    this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        ga('set', 'page', 'start' + event.urlAfterRedirects);
        ga('send', 'pageview');
      }
    });
  }
}
