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
import { Route } from '@angular/router';

import { HomeComponent } from './home.component';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { GENERATOR_ROUTE } from 'app/home/generator/generator.route';
import { GIT_ROUTE } from 'app/home/git/git.route';
import { WELCOME_ROUTE } from 'app/home/welcome/welcome.route';
import { GITHUB_CALLBACK_ROUTE } from 'app/home/git/callback/callback.route';
import { jdlMetadataRoute } from 'app/home/jdl-metadata/jdl-metadata.route';
import { CI_CD_OUTPUT_ROUTE, CI_CD_ROUTE } from 'app/home/ci-cd/ci-cd.route';
import { YourGeneratorsRoute, YourGeneratorsDialogueRoutes } from 'app/home/your-generators/your-generators.route';

export const HOME_ROUTE: Route = {
  path: '',
  component: HomeComponent,
  data: {
    authorities: [],
    pageTitle: 'JHipster Online'
  },
  canActivate: [UserRouteAccessService],
  children: [
    GENERATOR_ROUTE,
    WELCOME_ROUTE,
    GIT_ROUTE,
    GITHUB_CALLBACK_ROUTE,
    CI_CD_ROUTE,
    YourGeneratorsRoute,
    ...YourGeneratorsDialogueRoutes,
    CI_CD_OUTPUT_ROUTE,
    ...jdlMetadataRoute
  ]
};
