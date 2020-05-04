import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhonlineSharedModule } from 'app/shared/shared.module';

import { AuditsComponent } from './audits.component';

import { auditsRoute } from './audits.route';
import { UserManagementModule } from 'app/admin/user-management/user-management.module';
import { FormsModule } from '@angular/forms';
import { NgJhipsterModule } from 'ng-jhipster';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { NgbPaginationModule } from '@ng-bootstrap/ng-bootstrap';

@NgModule({
  imports: [
    JhonlineSharedModule,
    RouterModule.forChild([auditsRoute]),
    UserManagementModule,
    FormsModule,
    NgJhipsterModule,
    FontAwesomeModule,
    NgbPaginationModule
  ],
  declarations: [AuditsComponent]
})
export class AuditsModule {}
