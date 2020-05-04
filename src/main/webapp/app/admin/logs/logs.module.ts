import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhonlineSharedModule } from 'app/shared/shared.module';

import { LogsComponent } from './logs.component';

import { logsRoute } from './logs.route';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { NgJhipsterModule } from 'ng-jhipster';

@NgModule({
  imports: [JhonlineSharedModule, RouterModule.forChild([logsRoute]), FormsModule, CommonModule, NgJhipsterModule],
  declarations: [LogsComponent]
})
export class LogsModule {}
