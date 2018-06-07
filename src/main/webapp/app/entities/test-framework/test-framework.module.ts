import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhonlineSharedModule } from 'app/shared';
import {
    TestFrameworkService,
    TestFrameworkComponent,
    TestFrameworkDetailComponent,
    TestFrameworkUpdateComponent,
    TestFrameworkDeletePopupComponent,
    TestFrameworkDeleteDialogComponent,
    testFrameworkRoute,
    testFrameworkPopupRoute,
    TestFrameworkResolve
} from './';

const ENTITY_STATES = [...testFrameworkRoute, ...testFrameworkPopupRoute];

@NgModule({
    imports: [JhonlineSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        TestFrameworkComponent,
        TestFrameworkDetailComponent,
        TestFrameworkUpdateComponent,
        TestFrameworkDeleteDialogComponent,
        TestFrameworkDeletePopupComponent
    ],
    entryComponents: [
        TestFrameworkComponent,
        TestFrameworkUpdateComponent,
        TestFrameworkDeleteDialogComponent,
        TestFrameworkDeletePopupComponent
    ],
    providers: [TestFrameworkService, TestFrameworkResolve],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhonlineTestFrameworkModule {}
