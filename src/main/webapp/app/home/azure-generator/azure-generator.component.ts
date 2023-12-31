/**
 * Copyright 2017-2024 the original author or authors from the JHipster project.
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

import { Component, OnInit } from '@angular/core';
import { GeneratorConfigurationModel } from '../generator/generator.configuration.model';
import { JHipsterConfigurationModel } from '../generator/jhipster.configuration.model';

@Component({
  selector: 'jhi-azure-generator',
  templateUrl: './azure-generator.component.html'
})
export class AzureGeneratorComponent implements OnInit {
  azureGeneratorConfig: GeneratorConfigurationModel = {
    hideRepositoryName: true,
    hideApplicationType: true,
    hideServiceDiscoveryType: true,
    hideAuthenticationType: true,
    hideDatabaseType: true,
    hideProdDatabaseTypeOptions: ['mysql', 'mariadb', 'oracle', 'mssql', 'mongodb', 'cassandra', 'couchbase', 'neo4j', 'no'],
    hideDevDatabaseTypeOptions: [
      'h2Disk',
      'h2Memory',
      'mysql',
      'mariadb',
      'oracle',
      'mssql',
      'mongodb',
      'cassandra',
      'couchbase',
      'neo4j',
      'no'
    ],
    hideCacheProvider: true,
    hideBuildTool: true,
    hideOtherComponents: true,
    hideClientSideOptions: true,
    hideI18nOptions: true,
    hideTestingOptions: true
  };
  azureJHipsterModel: JHipsterConfigurationModel = new JHipsterConfigurationModel();

  ngOnInit(): void {
    this.azureJHipsterModel.devDatabaseType = 'postgresql';
    this.azureJHipsterModel.prodDatabaseType = 'postgresql';
    this.azureJHipsterModel.cacheProvider = 'no';
    this.azureJHipsterModel.clientFramework = 'react';
    // Repository for this blueprint is
    // https://github.com/Azure/generator-jhipster-azure-spring-apps
    this.azureJHipsterModel.blueprints.push({ name: 'generator-jhipster-azure-spring-apps' });
  }
}
