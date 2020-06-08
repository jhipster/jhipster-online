import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhonlineSharedModule } from 'app/shared/shared.module';
import { statisticsRoute } from 'app/entities/statistics/statistics.route';
import { StatisticsComponent } from 'app/entities/statistics/statistics.component';

@NgModule({
  imports: [JhonlineSharedModule, RouterModule.forChild([statisticsRoute])],
  declarations: [StatisticsComponent]
})
export class JhonlineStatisticsModule {}
