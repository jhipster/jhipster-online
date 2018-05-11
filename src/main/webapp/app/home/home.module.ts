import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GithubComponent } from './github/github.component';
import { WelcomeComponent } from './welcome/welcome.component';
import { GeneratorService } from './generator/generator.service';
import { GithubService } from './github/github.service';
import { CallbackComponent } from './github/callback/callback.component';
import { GithubCallbackService } from './github/callback/callback.service';
import { GeneratorOutputDialogComponent } from './generator/generator.output.component';
import { JdlMetadataComponent } from './jdl-metadata/jdl-metadata.component';
import { ApplyJdlStudioComponent, DeleteJdlStudioComponent } from './jdl-metadata/jdl-studio.component';
import { JdlMetadataService } from './jdl-metadata/jdl-metadata.service';
import { JdlOutputDialogComponent } from './jdl-metadata/jdl.output.component';
import { JdlService } from './jdl-metadata/jdl.service';
import { CiCdComponent } from './ci-cd/ci-cd.component';
import { CiCdOutputDialogComponent } from './ci-cd/ci-cd.output.component';
import { CiCdService } from './ci-cd/ci-cd.service';
import { JhonlineSharedModule } from 'app/shared';
import { HOME_ROUTE, HomeComponent } from './';
import { GeneratorComponent } from 'app/home/generator/generator.component';

@NgModule({
    imports: [JhonlineSharedModule, RouterModule.forRoot([HOME_ROUTE], { useHash: true })],
    declarations: [
        HomeComponent,
        GeneratorComponent,
        GeneratorOutputDialogComponent,
        WelcomeComponent,
        GithubComponent,
        CallbackComponent,
        JdlMetadataComponent,
        DeleteJdlStudioComponent,
        ApplyJdlStudioComponent,
        JdlOutputDialogComponent,
        CiCdComponent,
        CiCdOutputDialogComponent
    ],
    entryComponents: [GeneratorOutputDialogComponent, JdlOutputDialogComponent, CiCdOutputDialogComponent],
    providers: [GeneratorService, GithubService, GithubCallbackService, JdlMetadataService, JdlService, CiCdService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhonlineHomeModule {}
