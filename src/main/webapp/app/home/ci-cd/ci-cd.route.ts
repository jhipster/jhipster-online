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
import { Route } from '@angular/router';

import { CiCdComponent } from './ci-cd.component';
import { UserRouteAccessService } from 'app/core';
import { CiCdOutputDialogComponent } from './ci-cd.output.component';

export const CI_CD_ROUTE: Route = {
    path: 'ci-cd',
    component: CiCdComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Continuous Integration'
    },
    canActivate: [UserRouteAccessService]
};

export const CI_CD_OUTPUT_ROUTE: Route = {
    path: 'ci-cd-output',
    component: CiCdOutputDialogComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Continuous Integration'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
};
