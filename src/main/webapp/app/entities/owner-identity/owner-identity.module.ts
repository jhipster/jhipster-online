import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhonlineSharedModule } from 'app/shared';
import { JhonlineAdminModule } from 'app/admin/admin.module';
import {
    OwnerIdentityService,
    OwnerIdentityComponent,
    OwnerIdentityDetailComponent,
    OwnerIdentityUpdateComponent,
    OwnerIdentityDeletePopupComponent,
    OwnerIdentityDeleteDialogComponent,
    ownerIdentityRoute,
    ownerIdentityPopupRoute,
    OwnerIdentityResolve
} from './';

const ENTITY_STATES = [...ownerIdentityRoute, ...ownerIdentityPopupRoute];

@NgModule({
    imports: [JhonlineSharedModule, JhonlineAdminModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        OwnerIdentityComponent,
        OwnerIdentityDetailComponent,
        OwnerIdentityUpdateComponent,
        OwnerIdentityDeleteDialogComponent,
        OwnerIdentityDeletePopupComponent
    ],
    entryComponents: [
        OwnerIdentityComponent,
        OwnerIdentityUpdateComponent,
        OwnerIdentityDeleteDialogComponent,
        OwnerIdentityDeletePopupComponent
    ],
    providers: [OwnerIdentityService, OwnerIdentityResolve],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhonlineOwnerIdentityModule {}
