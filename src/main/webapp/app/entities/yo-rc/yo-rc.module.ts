import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhonlineSharedModule } from 'app/shared/shared.module';
import { YoRCComponent } from './yo-rc.component';
import { YoRCDetailComponent } from './yo-rc-detail.component';
import { YoRCUpdateComponent } from './yo-rc-update.component';
import { YoRCDeleteDialogComponent } from './yo-rc-delete-dialog.component';
import { yoRCRoute } from './yo-rc.route';

@NgModule({
  imports: [JhonlineSharedModule, RouterModule.forChild(yoRCRoute)],
  declarations: [YoRCComponent, YoRCDetailComponent, YoRCUpdateComponent, YoRCDeleteDialogComponent],
  entryComponents: [YoRCDeleteDialogComponent]
})
export class JhonlineYoRCModule {}
