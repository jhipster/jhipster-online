import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhonlineSharedModule } from 'app/shared';
import {
    UserApplicationComponent,
    UserApplicationDetailComponent,
    UserApplicationUpdateComponent,
    UserApplicationDeletePopupComponent,
    UserApplicationDeleteDialogComponent,
    userApplicationRoute,
    userApplicationPopupRoute
} from './';

const ENTITY_STATES = [...userApplicationRoute, ...userApplicationPopupRoute];

@NgModule({
    imports: [JhonlineSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        UserApplicationComponent,
        UserApplicationDetailComponent,
        UserApplicationUpdateComponent,
        UserApplicationDeleteDialogComponent,
        UserApplicationDeletePopupComponent
    ],
    entryComponents: [
        UserApplicationComponent,
        UserApplicationUpdateComponent,
        UserApplicationDeleteDialogComponent,
        UserApplicationDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhonlineUserApplicationModule {}
