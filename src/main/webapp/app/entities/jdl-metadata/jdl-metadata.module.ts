import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhonlineSharedModule } from 'app/shared/shared.module';
import { JdlMetadataComponent } from './jdl-metadata.component';
import { JdlMetadataDetailComponent } from './jdl-metadata-detail.component';
import { JdlMetadataUpdateComponent } from './jdl-metadata-update.component';
import { JdlMetadataDeleteDialogComponent } from './jdl-metadata-delete-dialog.component';
import { jdlMetadataRoute } from './jdl-metadata.route';

@NgModule({
  imports: [JhonlineSharedModule, RouterModule.forChild(jdlMetadataRoute)],
  declarations: [JdlMetadataComponent, JdlMetadataDetailComponent, JdlMetadataUpdateComponent, JdlMetadataDeleteDialogComponent],
  entryComponents: [JdlMetadataDeleteDialogComponent]
})
export class JhonlineJdlMetadataModule {}
