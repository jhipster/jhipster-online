import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhonlineSharedModule } from 'app/shared';
import {
    GeneratorIdentityService,
    GeneratorIdentityComponent,
    GeneratorIdentityDetailComponent,
    GeneratorIdentityUpdateComponent,
    GeneratorIdentityDeletePopupComponent,
    GeneratorIdentityDeleteDialogComponent,
    generatorIdentityRoute,
    generatorIdentityPopupRoute,
    GeneratorIdentityResolve
} from './';

const ENTITY_STATES = [...generatorIdentityRoute, ...generatorIdentityPopupRoute];

@NgModule({
    imports: [JhonlineSharedModule, RouterModule.forChild(ENTITY_STATES)],
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
    providers: [GeneratorIdentityService, GeneratorIdentityResolve],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhonlineGeneratorIdentityModule {}
