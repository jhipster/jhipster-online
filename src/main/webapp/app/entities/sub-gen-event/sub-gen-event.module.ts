import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhonlineSharedModule } from 'app/shared/shared.module';
import { SubGenEventComponent } from './sub-gen-event.component';
import { SubGenEventDetailComponent } from './sub-gen-event-detail.component';
import { SubGenEventUpdateComponent } from './sub-gen-event-update.component';
import { SubGenEventDeleteDialogComponent } from './sub-gen-event-delete-dialog.component';
import { subGenEventRoute } from './sub-gen-event.route';

@NgModule({
  imports: [JhonlineSharedModule, RouterModule.forChild(subGenEventRoute)],
  declarations: [SubGenEventComponent, SubGenEventDetailComponent, SubGenEventUpdateComponent, SubGenEventDeleteDialogComponent],
  entryComponents: [SubGenEventDeleteDialogComponent]
})
export class JhonlineSubGenEventModule {}
