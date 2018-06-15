import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhonlineSharedModule } from 'app/shared';
import {
    LanguageService,
    LanguageComponent,
    LanguageDetailComponent,
    LanguageUpdateComponent,
    LanguageDeletePopupComponent,
    LanguageDeleteDialogComponent,
    languageRoute,
    languagePopupRoute,
    LanguageResolve
} from './';

const ENTITY_STATES = [...languageRoute, ...languagePopupRoute];

@NgModule({
    imports: [JhonlineSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        LanguageComponent,
        LanguageDetailComponent,
        LanguageUpdateComponent,
        LanguageDeleteDialogComponent,
        LanguageDeletePopupComponent
    ],
    entryComponents: [LanguageComponent, LanguageUpdateComponent, LanguageDeleteDialogComponent, LanguageDeletePopupComponent],
    providers: [LanguageService, LanguageResolve],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhonlineLanguageModule {}
