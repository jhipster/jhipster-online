import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhonlineSharedModule } from 'app/shared/shared.module';
import { GeneratorIdentityComponent } from './generator-identity.component';
import { GeneratorIdentityDetailComponent } from './generator-identity-detail.component';
import { GeneratorIdentityUpdateComponent } from './generator-identity-update.component';
import { GeneratorIdentityDeleteDialogComponent } from './generator-identity-delete-dialog.component';
import { generatorIdentityRoute } from './generator-identity.route';

@NgModule({
  imports: [JhonlineSharedModule, RouterModule.forChild(generatorIdentityRoute)],
  declarations: [
    GeneratorIdentityComponent,
    GeneratorIdentityDetailComponent,
    GeneratorIdentityUpdateComponent,
    GeneratorIdentityDeleteDialogComponent
  ],
  entryComponents: [GeneratorIdentityDeleteDialogComponent]
})
export class JhonlineGeneratorIdentityModule {}
