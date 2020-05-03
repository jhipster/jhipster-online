import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhonlineSharedModule } from 'app/shared';
import { JhonlineAdminModule } from 'app/admin/admin.module';
import {
    GeneratorIdentityComponent,
    GeneratorIdentityDetailComponent,
    GeneratorIdentityUpdateComponent,
    GeneratorIdentityDeletePopupComponent,
    GeneratorIdentityDeleteDialogComponent,
    generatorIdentityRoute,
    generatorIdentityPopupRoute
} from './';

const ENTITY_STATES = [...generatorIdentityRoute, ...generatorIdentityPopupRoute];

@NgModule({
    imports: [JhonlineSharedModule, JhonlineAdminModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        GeneratorIdentityComponent,
        GeneratorIdentityDetailComponent,
        GeneratorIdentityUpdateComponent,
        GeneratorIdentityDeleteDialogComponent,
        GeneratorIdentityDeletePopupComponent
    ],
    entryComponents: [
        GeneratorIdentityComponent,
        GeneratorIdentityUpdateComponent,
        GeneratorIdentityDeleteDialogComponent,
        GeneratorIdentityDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhonlineGeneratorIdentityModule {}
