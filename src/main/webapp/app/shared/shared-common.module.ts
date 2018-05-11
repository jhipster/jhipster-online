import { NgModule } from '@angular/core';

import { JhonlineSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [JhonlineSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    providers: [],
    exports: [JhonlineSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class JhonlineSharedCommonModule {}
