import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhonlineSharedModule } from 'app/shared';
import { JhonlineAdminModule } from 'app/admin/admin.module';
import {
    YoRCService,
    YoRCComponent,
    YoRCDetailComponent,
    YoRCUpdateComponent,
    YoRCDeletePopupComponent,
    YoRCDeleteDialogComponent,
    yoRCRoute,
    yoRCPopupRoute,
    YoRCResolve,
    YoRCResolvePagingParams
} from './';

const ENTITY_STATES = [...yoRCRoute, ...yoRCPopupRoute];

@NgModule({
    imports: [JhonlineSharedModule, JhonlineAdminModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [YoRCComponent, YoRCDetailComponent, YoRCUpdateComponent, YoRCDeleteDialogComponent, YoRCDeletePopupComponent],
    entryComponents: [YoRCComponent, YoRCUpdateComponent, YoRCDeleteDialogComponent, YoRCDeletePopupComponent],
    providers: [YoRCService, YoRCResolve, YoRCResolvePagingParams],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhonlineYoRCModule {}
