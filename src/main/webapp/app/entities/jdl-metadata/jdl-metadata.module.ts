import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhonlineSharedModule } from 'app/shared';
import { JhonlineAdminModule } from 'app/admin/admin.module';
import {
    JdlMetadataComponent,
    JdlMetadataDetailComponent,
    JdlMetadataUpdateComponent,
    JdlMetadataDeletePopupComponent,
    JdlMetadataDeleteDialogComponent,
    jdlMetadataRoute,
    jdlMetadataPopupRoute
} from './';

const ENTITY_STATES = [...jdlMetadataRoute, ...jdlMetadataPopupRoute];

@NgModule({
    imports: [JhonlineSharedModule, JhonlineAdminModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        JdlMetadataComponent,
        JdlMetadataDetailComponent,
        JdlMetadataUpdateComponent,
        JdlMetadataDeleteDialogComponent,
        JdlMetadataDeletePopupComponent
    ],
    entryComponents: [JdlMetadataComponent, JdlMetadataUpdateComponent, JdlMetadataDeleteDialogComponent, JdlMetadataDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhonlineJdlMetadataModule {}
