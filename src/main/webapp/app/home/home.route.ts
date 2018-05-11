import { Route } from '@angular/router';

import { HomeComponent } from './';
import { UserRouteAccessService } from 'app/core/';
import { GENERATOR_ROUTE } from './generator/generator.route';
import { GITHUB_ROUTE } from './github/github.route';
import { WELCOME_ROUTE } from './welcome/welcome.route';
import { GITHUB_CALLBACK_ROUTE } from './github/callback/callback.route';
import { jdlMetadataRoute } from './jdl-metadata/jdl-metadata.route';
import { CI_CD_OUTPUT_ROUTE, CI_CD_ROUTE } from './ci-cd/ci-cd.route';

export const HOME_ROUTE: Route = {
    path: '',
    component: HomeComponent,
    data: {
        authorities: [],
        pageTitle: 'JHipster Online'
    },
    canActivate: [UserRouteAccessService],
    children: [GENERATOR_ROUTE, WELCOME_ROUTE, GITHUB_ROUTE, GITHUB_CALLBACK_ROUTE, CI_CD_ROUTE, CI_CD_OUTPUT_ROUTE, ...jdlMetadataRoute]
};
