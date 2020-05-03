import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { JhonlineJdlMetadataModule } from './jdl-metadata/jdl-metadata.module';
import { JhonlineSubGenEventModule } from './sub-gen-event/sub-gen-event.module';
import { JhonlineEntityStatsModule } from './entity-stats/entity-stats.module';
import { JhonlineLanguageModule } from './language/language.module';
import { JhonlineGeneratorIdentityModule } from './generator-identity/generator-identity.module';
import { JhonlineUserApplicationModule } from './user-application/user-application.module';
import { JhonlineYoRCModule } from './yo-rc/yo-rc.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        JhonlineJdlMetadataModule,
        JhonlineSubGenEventModule,
        JhonlineEntityStatsModule,
        JhonlineLanguageModule,
        JhonlineGeneratorIdentityModule,
        JhonlineUserApplicationModule,
        JhonlineYoRCModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhonlineEntityModule {}
