import { Route } from '@angular/router';

import { UserRouteAccessService } from 'app/core';
import { PasswordResetFinishComponent } from './password-reset-finish.component';

export const passwordResetFinishRoute: Route = {
    path: 'reset/finish',
    component: PasswordResetFinishComponent,
    data: {
        authorities: [],
        pageTitle: 'Password'
    },
    canActivate: [UserRouteAccessService]
};
