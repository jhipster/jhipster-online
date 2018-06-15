import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhonlineSharedModule } from 'app/shared';
import {
    EntityStatsService,
    EntityStatsComponent,
    EntityStatsDetailComponent,
    EntityStatsUpdateComponent,
    EntityStatsDeletePopupComponent,
    EntityStatsDeleteDialogComponent,
    entityStatsRoute,
    entityStatsPopupRoute,
    EntityStatsResolve
} from './';

const ENTITY_STATES = [...entityStatsRoute, ...entityStatsPopupRoute];

@NgModule({
    imports: [JhonlineSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        EntityStatsComponent,
        EntityStatsDetailComponent,
        EntityStatsUpdateComponent,
        EntityStatsDeleteDialogComponent,
        EntityStatsDeletePopupComponent
    ],
    entryComponents: [EntityStatsComponent, EntityStatsUpdateComponent, EntityStatsDeleteDialogComponent, EntityStatsDeletePopupComponent],
    providers: [EntityStatsService, EntityStatsResolve],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhonlineEntityStatsModule {}
