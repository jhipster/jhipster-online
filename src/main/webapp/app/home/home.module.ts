/**
 * Copyright 2017-2022 the original author or authors from the JHipster project.
 *
 * This file is part of the JHipster Online project, see https://github.com/jhipster/jhipster-online
 * for more information.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { NgxEchartsModule } from 'ngx-echarts';

import { GitComponent } from './git/git.component';
import { WelcomeComponent } from './welcome/welcome.component';
import { CallbackComponent } from './git/callback/callback.component';
import { GeneratorOutputDialogComponent } from './generator/generator.output.component';
import { JdlMetadataComponent } from './jdl-metadata/jdl-metadata.component';
import { ApplyJdlStudioComponent, DeleteJdlStudioComponent } from './jdl-metadata/jdl-studio.component';
import { JdlOutputDialogComponent } from './jdl-metadata/jdl.output.component';
import { CiCdComponent } from './ci-cd/ci-cd.component';
import { CiCdOutputDialogComponent } from './ci-cd/ci-cd.output.component';
import { RemoveGeneratorDialogComponent } from './your-generators/remove-generator-dialog.component';
import { JhonlineSharedModule } from 'app/shared/shared.module';
import { HOME_ROUTE } from './home.route';
import { HomeComponent } from './home.component';
import { GeneratorComponent } from 'app/home/generator/generator.component';
import { YourGeneratorsComponent } from 'app/home/your-generators/your-generators.component';
import { DataDeletionDialogComponent } from 'app/home/your-generators/data-deletion-dialog.component';

@NgModule({
  imports: [JhonlineSharedModule, NgxEchartsModule, RouterModule.forRoot([HOME_ROUTE])],
  declarations: [
    HomeComponent,
    GeneratorComponent,
    GeneratorOutputDialogComponent,
    WelcomeComponent,
    GitComponent,
    CallbackComponent,
    JdlMetadataComponent,
    DeleteJdlStudioComponent,
    ApplyJdlStudioComponent,
    JdlOutputDialogComponent,
    CiCdComponent,
    YourGeneratorsComponent,
    CiCdOutputDialogComponent,
    RemoveGeneratorDialogComponent,
    DataDeletionDialogComponent
  ],
  entryComponents: [
    GeneratorOutputDialogComponent,
    JdlOutputDialogComponent,
    CiCdOutputDialogComponent,
    RemoveGeneratorDialogComponent,
    DataDeletionDialogComponent
  ]
})
export class JhonlineHomeModule {}
