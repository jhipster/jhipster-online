import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhonlineSharedModule } from 'app/shared';
import {
    SubGenEventService,
    SubGenEventComponent,
    SubGenEventDetailComponent,
    SubGenEventUpdateComponent,
    SubGenEventDeletePopupComponent,
    SubGenEventDeleteDialogComponent,
    subGenEventRoute,
    subGenEventPopupRoute,
    SubGenEventResolve
} from './';

const ENTITY_STATES = [...subGenEventRoute, ...subGenEventPopupRoute];

@NgModule({
    imports: [JhonlineSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SubGenEventComponent,
        SubGenEventDetailComponent,
        SubGenEventUpdateComponent,
        SubGenEventDeleteDialogComponent,
        SubGenEventDeletePopupComponent
    ],
    entryComponents: [SubGenEventComponent, SubGenEventUpdateComponent, SubGenEventDeleteDialogComponent, SubGenEventDeletePopupComponent],
    providers: [SubGenEventService, SubGenEventResolve],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhonlineSubGenEventModule {}
