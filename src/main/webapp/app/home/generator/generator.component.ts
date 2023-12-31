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
import { Component, Input, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { GitConfigurationModel } from 'app/core/git/git-configuration.model';
import { GitConfigurationService } from 'app/core/git/git-configuration.service';
import { AccountService } from 'app/core/auth/account.service';

import { JHipsterConfigurationModel } from './jhipster.configuration.model';
import { GeneratorService } from './generator.service';
import { GeneratorOutputDialogComponent } from './generator.output.component';
import {
  AllDevDatabaseTypes,
  AllProdDatabaseTypes,
  DevDatabaseType,
  GeneratorConfigurationModel,
  ProdDatabaseType
} from './generator.configuration.model';

@Component({
  selector: 'jhi-generator',
  templateUrl: './generator.component.html'
})
export class GeneratorComponent implements OnInit {
  @Input() config: GeneratorConfigurationModel = {};
  @Input() defaultModel: Partial<JHipsterConfigurationModel> | undefined;

  model: JHipsterConfigurationModel = new JHipsterConfigurationModel();

  submitted = false;

  languageOptions: any;

  selectedGitProvider: string | undefined;
  selectedGitCompany: string | undefined;

  githubConfigured = false;
  gitlabConfigured = false;

  repositoryName: string | undefined;

  gitConfig: GitConfigurationModel | undefined;

  isStatsEnabled = false;

  constructor(
    private modalService: NgbModal,
    private generatorService: GeneratorService,
    private gitConfigurationService: GitConfigurationService,
    private accountService: AccountService
  ) {}

  /**
   * get all the languages options supported by JHipster - copied from the generator.
   */
  static getAllSupportedLanguageOptions(): any {
    return [
      { name: 'Albanian', value: 'al' },
      { name: 'Arabic (Libya)', value: 'ar-ly' },
      { name: 'Armenian', value: 'hy' },
      { name: 'Belorussian', value: 'by' },
      { name: 'Bengali', value: 'bn' },
      { name: 'Catalan', value: 'ca' },
      { name: 'Chinese (Simplified)', value: 'zh-cn' },
      { name: 'Chinese (Traditional)', value: 'zh-tw' },
      { name: 'Czech', value: 'cs' },
      { name: 'Danish', value: 'da' },
      { name: 'Dutch', value: 'nl' },
      { name: 'English', value: 'en' },
      { name: 'Estonian', value: 'et' },
      { name: 'Farsi', value: 'fa' },
      { name: 'Finnish', value: 'fi' },
      { name: 'French', value: 'fr' },
      { name: 'Galician', value: 'gl' },
      { name: 'German', value: 'de' },
      { name: 'Greek', value: 'el' },
      { name: 'Hindi', value: 'hi' },
      { name: 'Hungarian', value: 'hu' },
      { name: 'Indonesia', value: 'in' },
      { name: 'Italian', value: 'it' },
      { name: 'Japanese', value: 'ja' },
      { name: 'Korean', value: 'ko' },
      { name: 'Marathi', value: 'mr' },
      { name: 'Myanmar', value: 'my' },
      { name: 'Polish', value: 'pl' },
      { name: 'Portuguese (Brazilian)', value: 'pt-br' },
      { name: 'Portuguese', value: 'pt-pt' },
      { name: 'Romanian', value: 'ro' },
      { name: 'Russian', value: 'ru' },
      { name: 'Slovak', value: 'sk' },
      { name: 'Serbian', value: 'sr' },
      { name: 'Spanish', value: 'es' },
      { name: 'Swedish', value: 'sv' },
      { name: 'Turkish', value: 'tr' },
      { name: 'Tamil', value: 'ta' },
      { name: 'Telugu', value: 'te' },
      { name: 'Thai', value: 'th' },
      { name: 'Ukrainian', value: 'ua' },
      { name: 'Uzbek (Cyrillic)', value: 'uz-Cyrl-uz' },
      { name: 'Uzbek (Latin)', value: 'uz-Latn-uz' },
      { name: 'Vietnamese', value: 'vi' }
    ];
  }

  ngOnInit(): void {
    this.newGenerator();
    this.languageOptions = GeneratorComponent.getAllSupportedLanguageOptions();
    this.gitConfig = this.gitConfigurationService.gitConfig;
    if (this.gitConfig) {
      this.gitlabConfigured = this.gitConfig.gitlabConfigured ?? false;
      this.githubConfigured = this.gitConfig.githubConfigured ?? false;
    }
    this.gitConfigurationService.sharedData.subscribe((gitConfig: GitConfigurationModel) => {
      if (gitConfig) {
        this.gitlabConfigured = gitConfig.gitlabConfigured ?? false;
        this.githubConfigured = gitConfig.githubConfigured ?? false;
      }
    });
  }

  updateSharedData(data: any): void {
    this.selectedGitProvider = data.selectedGitProvider;
    this.selectedGitCompany = data.selectedGitCompany;
  }

  checkModelBeforeSubmit(): void {
    this.submitted = true;

    if (this.model.cacheProvider === 'no') {
      this.model.enableHibernateCache = false;
    }
    if (this.model.websocket) {
      this.model.websocket = 'spring-websocket';
    }
    if (this.model.searchEngine) {
      this.model.searchEngine = 'elasticsearch';
    }
    if (this.model.enableSwaggerCodegen) {
      this.model.enableSwaggerCodegen = 'true';
    }
    if (this.model.messageBroker) {
      this.model.messageBroker = 'kafka';
    }
    if (this.model.enableTranslation && !this.model.languages.includes(this.model.nativeLanguage)) {
      this.model.languages.push(this.model.nativeLanguage);
    }
    this.model.jhiPrefix = 'jhi';
  }

  onSubmit(): void {
    this.checkModelBeforeSubmit();

    if (this.selectedGitProvider && this.selectedGitCompany && this.repositoryName) {
      this.generatorService.generateOnGit(this.model, this.selectedGitProvider, this.selectedGitCompany, this.repositoryName).subscribe(
        (res: any) => {
          this.openOutputModal(res);
          this.submitted = false;
        },
        (error: any) => {
          console.error('Error generating the application.');
          console.error(error);
        }
      );
    }
  }

  onSubmitDownload(): void {
    this.checkModelBeforeSubmit();
    this.generatorService.download(this.model).subscribe(
      (data: any) => this.downloadFile(data.body),
      // eslint-disable-next-line no-console
      (error: any) => console.log(error),
      () => {
        // eslint-disable-next-line no-console
        console.log('Application downloaded');
        this.submitted = false;
      }
    );
  }

  openOutputModal(applicationId: string): void {
    const modalRef = this.modalService.open(GeneratorOutputDialogComponent, { size: 'lg', backdrop: 'static' }).componentInstance;

    modalRef.applicationId = applicationId;
    modalRef.selectedGitProvider = this.selectedGitProvider;
    modalRef.selectedGitCompany = this.selectedGitCompany;
    modalRef.repositoryName = this.repositoryName;
    modalRef.gitlabHost = this.gitConfig!.gitlabHost;
    modalRef.githubHost = this.gitConfig!.githubHost;
    modalRef.gitlabConfigured = this.gitConfig!.gitlabAvailable;
    modalRef.githubConfigured = this.gitConfig!.githubAvailable;
  }

  downloadFile(blob: Blob): void {
    const a = document.createElement('a'),
      fileURL = URL.createObjectURL(blob);

    a.href = fileURL;
    a.download = this.model.baseName + '.zip';
    window.document.body.appendChild(a);
    a.click();
    window.document.body.removeChild(a);
    URL.revokeObjectURL(fileURL);
  }

  newGenerator(): void {
    this.model = new JHipsterConfigurationModel(this.defaultModel);
    this.repositoryName = 'jhipster-sample-application';
  }

  changeApplicationType(): void {
    // server port
    if (this.model.applicationType === 'microservice') {
      this.model.serverPort = 8081;
    } else {
      this.model.serverPort = 8080;
    }
    // authentication
    if (this.model.applicationType !== 'microservice') {
      this.model.authenticationType = 'jwt';
    }
    // service discovery
    if (this.model.applicationType === 'gateway' || this.model.applicationType === 'microservice') {
      this.model.serviceDiscoveryType = 'consul';
    }
    // database
    if (this.model.databaseType === 'no') {
      this.model.databaseType = 'sql';
      this.changeDatabaseType();
    }
    // cache
    if (this.model.applicationType === 'microservice') {
      this.model.cacheProvider = 'hazelcast';
      this.model.enableHibernateCache = true;
    }
  }

  changePackageName(): void {
    this.model.packageFolder = this.model.packageName.replace(/\./g, '/');
  }

  changeServiceDiscoveryType(): void {
    if (this.model.serviceDiscoveryType === 'eureka') {
      this.model.authenticationType = 'jwt';
    }
    if (this.model.serviceDiscoveryType === 'false') {
      this.model.serviceDiscoveryType = false;
    }
  }

  changeAuthenticationType(): void {
    this.model.databaseType = 'sql';
    this.model.clientFramework = 'angularX';
    this.changeDatabaseType();
  }

  changeDatabaseType(): void {
    if (this.model.databaseType === 'sql') {
      this.model.prodDatabaseType = AllProdDatabaseTypes.find(type => !this.isProdDatabaseOptionHidden('sql', type)) ?? 'mysql';
      this.model.devDatabaseType = AllDevDatabaseTypes.find(type => !this.isDevDatabaseOptionHidden('sql', type)) ?? 'h2Disk';
      this.model.cacheProvider = 'ehcache';
      this.model.enableHibernateCache = true;
    } else if (this.model.databaseType === 'mongodb') {
      this.model.prodDatabaseType = 'mongodb';
      this.model.devDatabaseType = 'mongodb';
      this.model.cacheProvider = 'no';
      this.model.enableHibernateCache = false;
    } else if (this.model.databaseType === 'cassandra') {
      this.model.prodDatabaseType = 'cassandra';
      this.model.devDatabaseType = 'cassandra';
      this.model.cacheProvider = 'no';
      this.model.enableHibernateCache = false;
      this.model.searchEngine = false;
    } else if (this.model.databaseType === 'couchbase') {
      this.model.prodDatabaseType = 'couchbase';
      this.model.devDatabaseType = 'couchbase';
      this.model.cacheProvider = 'no';
      this.model.enableHibernateCache = false;
      this.model.searchEngine = false;
    } else if (this.model.databaseType === 'no') {
      this.model.devDatabaseType = 'no';
      this.model.prodDatabaseType = 'no';
      this.model.cacheProvider = 'no';
      this.model.enableHibernateCache = false;
      this.model.searchEngine = false;
    }
  }

  changeProdDatabaseType(): void {
    if (this.model.devDatabaseType === this.model.prodDatabaseType) {
      return;
    }

    if (this.model.databaseType === 'sql') {
      // Find first allowed dev database type
      this.model.devDatabaseType = AllDevDatabaseTypes.find(type => !this.isDevDatabaseOptionHidden('sql', type)) ?? 'h2Disk';
    } else if (this.model.prodDatabaseType === 'mongodb') {
      this.model.devDatabaseType = 'mongodb';
      this.model.cacheProvider = 'no';
      this.model.enableHibernateCache = false;
    } else if (this.model.prodDatabaseType === 'cassandra') {
      this.model.devDatabaseType = 'cassandra';
      this.model.cacheProvider = 'no';
      this.model.enableHibernateCache = false;
    } else if (this.model.prodDatabaseType === 'no') {
      this.model.devDatabaseType = 'no';
      this.model.cacheProvider = 'no';
      this.model.enableHibernateCache = false;
    }
  }

  isAuthenticated(): boolean {
    return this.accountService.isAuthenticated();
  }

  isProdDatabaseOptionHidden(validDatabaseType: string, databaseName: ProdDatabaseType): boolean {
    return this.model.databaseType !== validDatabaseType || Boolean(this.config?.hideProdDatabaseTypeOptions?.includes(databaseName));
  }

  isDevDatabaseOptionHidden(validDatabaseType: string, databaseName: DevDatabaseType): boolean {
    return (
      this.model.databaseType !== validDatabaseType ||
      Boolean(this.config?.hideDevDatabaseTypeOptions?.includes(databaseName)) ||
      (databaseName !== 'h2Disk' && databaseName !== 'h2Memory' && this.model.prodDatabaseType !== databaseName)
    );
  }
}
