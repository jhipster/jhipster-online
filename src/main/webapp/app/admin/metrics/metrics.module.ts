import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhonlineSharedModule } from 'app/shared/shared.module';

import { MetricsComponent } from './metrics.component';

import { metricsRoute } from './metrics.route';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';

@NgModule({
  imports: [JhonlineSharedModule, RouterModule.forChild([metricsRoute]), FontAwesomeModule],
  declarations: [MetricsComponent]
})
export class MetricsModule {}
