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
import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, Route } from '@angular/router';
import { UserRouteAccessService } from 'app/core';

import { YourGeneratorsComponent } from 'app/home/your-generators/your-generators.component';
import { RemoveGeneratorDialogComponent } from 'app/home/your-generators/remove-generator-dialog.component';
import { DataDeletionDialogComponent } from 'app/home/your-generators/data-deletion-dialog.component';

@Injectable({ providedIn: 'root' })
export class GuidResolve implements Resolve<string> {
    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        return route.params['guid'] ? route.params['guid'] : null;
    }
}

export const YourGeneratorsRoute: Route = {
    path: 'your-generators',
    component: YourGeneratorsComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Bound Generators'
    },
    canActivate: [UserRouteAccessService]
};

export const YourGeneratorsDialogueRoutes: Routes = [
    {
        path: 'your-generators/remove',
        component: RemoveGeneratorDialogComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Remove a generator'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'your-generators/delete-statistics',
        component: DataDeletionDialogComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Delete all user statistics'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
