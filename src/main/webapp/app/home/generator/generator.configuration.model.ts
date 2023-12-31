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

export const AllProdDatabaseTypes: ProdDatabaseType[] = [
  'postgresql',
  'mysql',
  'mariadb',
  'oracle',
  'mssql',
  'mongodb',
  'cassandra',
  'couchbase',
  'neo4j',
  'no'
];
export const AllDevDatabaseTypes: DevDatabaseType[] = ['h2Disk', 'h2Memory', ...AllProdDatabaseTypes];

export type ProdDatabaseType =
  | 'postgresql'
  | 'mysql'
  | 'mariadb'
  | 'oracle'
  | 'mssql'
  | 'mongodb'
  | 'cassandra'
  | 'couchbase'
  | 'neo4j'
  | 'no';
export type DevDatabaseType = 'h2Disk' | 'h2Memory' | ProdDatabaseType;

export interface GeneratorConfigurationModel {
  hideRepositoryName?: boolean;
  hideApplicationType?: boolean;
  hideServiceDiscoveryType?: boolean;
  hideAuthenticationType?: boolean;
  hideDatabaseType?: boolean;
  hideProdDatabaseType?: boolean;
  hideProdDatabaseTypeOptions?: ProdDatabaseType[];
  hideDevDatabaseType?: boolean;
  hideDevDatabaseTypeOptions?: DevDatabaseType[];
  hideCacheProvider?: boolean;
  hideBuildTool?: boolean;
  hideOtherComponents?: boolean;
  hideClientSideOptions?: boolean;
  hideI18nOptions?: boolean;
  hideTestingOptions?: boolean;
}
