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
import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core';

import { JdlMetadataComponent } from './jdl-metadata.component';
import { ApplyJdlStudioComponent, DeleteJdlStudioComponent } from './jdl-studio.component';

export const jdlMetadataRoute: Routes = [
    {
        path: 'design-entities',
        component: JdlMetadataComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Design Entities'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'design-entities-delete/:jdlId',
        component: DeleteJdlStudioComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Delete JDL Model'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'design-entities-apply/:jdlId',
        component: ApplyJdlStudioComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Apply JDL Model to a project'
        },
        canActivate: [UserRouteAccessService]
    }
];
