import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhonlineSharedModule } from 'app/shared/shared.module';
import { UserManagementComponent } from './user-management.component';
import { UserManagementDetailComponent } from './user-management-detail.component';
import { UserManagementUpdateComponent } from './user-management-update.component';
import { UserManagementDeleteDialogComponent } from './user-management-delete-dialog.component';
import { CommonModule } from '@angular/common';
import { UserMgmtResetDialogComponent } from 'app/admin/user-management/user-management-reset-dialog.component';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { NgJhipsterModule } from 'ng-jhipster';
import { NgbAlertModule, NgbPaginationModule } from '@ng-bootstrap/ng-bootstrap';
import { AlertErrorComponent } from 'app/shared/alert/alert-error.component';
import { AlertComponent } from 'app/shared/alert/alert.component';
import { ReactiveFormsModule } from '@angular/forms';
import { userMgmtRoute } from 'app/admin/user-management/user-management.route';

@NgModule({
  imports: [
    JhonlineSharedModule,
    RouterModule.forChild(userMgmtRoute),
    CommonModule,
    FontAwesomeModule,
    NgJhipsterModule,
    NgbPaginationModule,
    NgbAlertModule,
    ReactiveFormsModule
  ],
  declarations: [
    UserManagementComponent,
    UserManagementDetailComponent,
    UserManagementUpdateComponent,
    UserManagementDeleteDialogComponent,
    UserMgmtResetDialogComponent,
    AlertErrorComponent,
    AlertComponent
  ],
  exports: [AlertComponent, AlertErrorComponent],
  entryComponents: [UserManagementDeleteDialogComponent]
})
export class UserManagementModule {}
