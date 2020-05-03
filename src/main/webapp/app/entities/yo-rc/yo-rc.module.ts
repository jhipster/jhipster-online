import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhonlineSharedModule } from 'app/shared';
import {
    YoRCComponent,
    YoRCDetailComponent,
    YoRCUpdateComponent,
    YoRCDeletePopupComponent,
    YoRCDeleteDialogComponent,
    yoRCRoute,
    yoRCPopupRoute
} from './';

const ENTITY_STATES = [...yoRCRoute, ...yoRCPopupRoute];

@NgModule({
    imports: [JhonlineSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [YoRCComponent, YoRCDetailComponent, YoRCUpdateComponent, YoRCDeleteDialogComponent, YoRCDeletePopupComponent],
    entryComponents: [YoRCComponent, YoRCUpdateComponent, YoRCDeleteDialogComponent, YoRCDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhonlineYoRCModule {}
