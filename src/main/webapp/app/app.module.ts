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
import { APP_INITIALIZER, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { NavigationEnd, Router } from '@angular/router';
import { NgxEchartsModule } from 'ngx-echarts';
import './vendor';
import { JhonlineSharedModule } from 'app/shared/shared.module';
import { JhonlineCoreModule } from 'app/core/core.module';
import { GitConfigurationService } from 'app/core/git/git-configuration.service';

import { JhonlineAppRoutingModule } from './app-routing.module';
import { JhonlineHomeModule } from 'app/home/home.module';
import { JhonlineEntityModule } from './entities/entity.module';
import { GoogleAnalyticsEventsService } from './shared/ga/google-analytics-events.service';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

export function setupGitConfiguration(gitConfigurationService: GitConfigurationService): any {
  return () => gitConfigurationService.setupGitConfiguration();
}

@NgModule({
  imports: [
    BrowserModule,
    JhonlineSharedModule,
    JhonlineCoreModule,
    JhonlineHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    JhonlineEntityModule,
    NgxEchartsModule,
    JhonlineAppRoutingModule
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  providers: [
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
  constructor(public router: Router, public googleAnalyticsEventsService: GoogleAnalyticsEventsService) {
    this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        ga('set', 'page', 'start' + event.urlAfterRedirects);
        ga('send', 'pageview');
      }
    });
  }
}
