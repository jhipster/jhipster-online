import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhonlineSharedModule } from 'app/shared';
import {
    YoRCService,
    YoRCComponent,
    YoRCDetailComponent,
    YoRCUpdateComponent,
    YoRCDeletePopupComponent,
    YoRCDeleteDialogComponent,
    yoRCRoute,
    yoRCPopupRoute,
    YoRCResolve
} from './';

const ENTITY_STATES = [...yoRCRoute, ...yoRCPopupRoute];

@NgModule({
    imports: [JhonlineSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [YoRCComponent, YoRCDetailComponent, YoRCUpdateComponent, YoRCDeleteDialogComponent, YoRCDeletePopupComponent],
    entryComponents: [YoRCComponent, YoRCUpdateComponent, YoRCDeleteDialogComponent, YoRCDeletePopupComponent],
    providers: [YoRCService, YoRCResolve],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhonlineYoRCModule {}
