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

export interface BlueprintModel {
  name: string;
}

export class JHipsterConfigurationModel {
  public applicationType = 'monolith';
  public gitCompany = '';
  public baseName = 'jhipsterSampleApplication';
  public packageName = 'com.mycompany.myapp';
  public packageFolder = 'com/mycompany/myapp';
  public serverPort = 8080;
  public serviceDiscoveryType: any = false;
  public authenticationType = 'jwt';
  public cacheProvider = 'ehcache';
  public enableHibernateCache = true;
  public websocket: any = false;
  public databaseType = 'sql';
  public devDatabaseType = 'h2Disk';
  public prodDatabaseType = 'postgresql';
  public searchEngine: any = false;
  public enableSwaggerCodegen: any = false;
  public messageBroker: any = false;
  public buildTool = 'maven';
  public useSass = true;
  public clientPackageManager = 'npm';
  public testFrameworks: string[] = [];
  public enableTranslation = true;
  public nativeLanguage = 'en';
  public languages = ['en'];
  public clientFramework = 'angularX';
  public jhiPrefix = 'jhi';
  public withAdminUi = true;
  public blueprints: BlueprintModel[] = [];

  constructor(data?: Partial<JHipsterConfigurationModel>) {
    if (data) {
      const dataCopy = { ...data };
      dataCopy.testFrameworks = [...(data.testFrameworks ?? [])];
      dataCopy.languages = [...(data.languages ?? [])];
      Object.assign(this, data);
    }
  }
}
