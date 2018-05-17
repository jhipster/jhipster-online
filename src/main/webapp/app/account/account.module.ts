import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhonlineSharedModule } from 'app/shared';

import {
    Register,
    ActivateService,
    PasswordService,
    PasswordResetInitService,
    PasswordResetFinishService,
    PasswordStrengthBarComponent,
    RegisterComponent,
    ActivateComponent,
    PasswordComponent,
    PasswordResetInitComponent,
    PasswordResetFinishComponent,
    SettingsComponent,
    DeleteAccountDialogComponent,
    accountState
} from './';

@NgModule({
    imports: [JhonlineSharedModule, RouterModule.forChild(accountState)],
    declarations: [
        ActivateComponent,
        RegisterComponent,
        PasswordComponent,
        PasswordStrengthBarComponent,
        PasswordResetInitComponent,
        PasswordResetFinishComponent,
        SettingsComponent,
        DeleteAccountDialogComponent
    ],
    entryComponents: [DeleteAccountDialogComponent],
    providers: [Register, ActivateService, PasswordService, PasswordResetInitService, PasswordResetFinishService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhonlineAccountModule {}
