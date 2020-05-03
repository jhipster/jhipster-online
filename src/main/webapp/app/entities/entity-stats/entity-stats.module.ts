import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhonlineSharedModule } from 'app/shared/shared.module';
import { EntityStatsComponent } from './entity-stats.component';
import { EntityStatsDetailComponent } from './entity-stats-detail.component';
import { EntityStatsUpdateComponent } from './entity-stats-update.component';
import { EntityStatsDeleteDialogComponent } from './entity-stats-delete-dialog.component';
import { entityStatsRoute } from './entity-stats.route';

@NgModule({
  imports: [JhonlineSharedModule, RouterModule.forChild(entityStatsRoute)],
  declarations: [EntityStatsComponent, EntityStatsDetailComponent, EntityStatsUpdateComponent, EntityStatsDeleteDialogComponent],
  entryComponents: [EntityStatsDeleteDialogComponent]
})
export class JhonlineEntityStatsModule {}
