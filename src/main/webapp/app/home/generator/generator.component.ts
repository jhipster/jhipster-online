/**
 * Copyright 2017-2018 the original author or authors from the JHipster Online project.
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
import { JHipsterConfigurationModel } from './jhipster.configuration.model';
import { GeneratorService } from './generator.service';
import { GithubService } from '../github/github.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { GeneratorOutputDialogComponent } from './generator.output.component';
import { GithubOrganizationModel } from './github.organization.model';

@Component({
    selector: 'jhi-generator',
    templateUrl: './generator.component.html',
    styleUrls: ['generator.scss']
})
export class GeneratorComponent implements OnInit {
    model: JHipsterConfigurationModel;

    submitted = false;

    languageOptions;

    githubConfigured = true;

    organizations: GithubOrganizationModel[];

    githubRefresh = false;

    /**
     * get all the languages options supported by JHipster - copied from the generator.
     */
    static getAllSupportedLanguageOptions() {
        return [
            { name: 'Arabic (Libya)', value: 'ar-ly' },
            { name: 'Armenian', value: 'hy' },
            { name: 'Bahasa Indonesia', value: 'id' },
            { name: 'Catalan', value: 'ca' },
            { name: 'Chinese (Simplified)', value: 'zh-cn' },
            { name: 'Chinese (Traditional)', value: 'zh-tw' },
            { name: 'Czech', value: 'cs' },
            { name: 'Danish', value: 'da' },
            { name: 'Dutch', value: 'nl' },
            { name: 'English', value: 'en' },
            { name: 'Estonian', value: 'et' },
            { name: 'French', value: 'fr' },
            { name: 'Galician', value: 'gl' },
            { name: 'German', value: 'de' },
            { name: 'Greek', value: 'el' },
            { name: 'Hindi', value: 'hi' },
            { name: 'Hungarian', value: 'hu' },
            { name: 'Italian', value: 'it' },
            { name: 'Japanese', value: 'ja' },
            { name: 'Korean', value: 'ko' },
            { name: 'Marathi', value: 'mr' },
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
            { name: 'Thai', value: 'th' },
            { name: 'Ukrainian', value: 'ua' },
            { name: 'Vietnamese', value: 'vi' }
        ];
    }

    constructor(private modalService: NgbModal, private generatorService: GeneratorService, private githubService: GithubService) {
        this.newGenerator();
    }

    ngOnInit() {
        this.languageOptions = GeneratorComponent.getAllSupportedLanguageOptions();
        this.githubRefresh = true;
        this.githubService.getOrganizations().subscribe(
            orgs => {
                this.organizations = orgs;
                this.model.gitHubOrganization = orgs[0].name;
                this.githubConfigured = true;
                this.githubRefresh = false;
            },
            () => {
                this.githubConfigured = false;
                this.githubRefresh = false;
            }
        );
    }

    refreshGithub() {
        this.githubRefresh = true;
        this.githubService.refreshGithub().subscribe(
            () => {
                this.githubRefresh = false;
            },
            () => {
                this.githubRefresh = false;
            }
        );
    }

    checkModelBeforeSubmit() {
        this.submitted = true;

        if (this.model.authenticationType === 'oauth2') {
            this.model.clientFramework = 'angularX';
        }
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
        if (this.model.enableSocialSignIn) {
            this.model.enableSocialSignIn = true;
        }
        if (this.model.enableTranslation && this.model.languages.indexOf(this.model.nativeLanguage) === -1) {
            this.model.languages.push(this.model.nativeLanguage);
        }
    }

    onSubmit() {
        this.checkModelBeforeSubmit();
        this.generatorService.generateOnGitHub(this.model).subscribe(
            res => {
                this.openOutputModal(res);
                this.submitted = false;
            },
            error => {
                console.error('Error generating the application.');
                console.error(error);
            }
        );
    }

    onSubmitDownload() {
        this.checkModelBeforeSubmit();
        this.generatorService.download(this.model).subscribe(
            data => this.downloadFile(data.body),
            error => console.log(error),
            () => {
                console.log('Application downloaded');
                this.submitted = false;
            }
        );
    }

    openOutputModal(applicationId: String) {
        const modalRef = this.modalService.open(GeneratorOutputDialogComponent, { size: 'lg', backdrop: 'static' }).componentInstance;

        modalRef.applicationId = applicationId;
        modalRef.gitHubOrganization = this.model.gitHubOrganization;
        modalRef.baseName = this.model.baseName;
    }

    downloadFile(blob: Blob) {
        const a = document.createElement('a'),
            fileURL = URL.createObjectURL(blob);

        a.href = fileURL;
        a.download = this.model.baseName + '.zip';
        window.document.body.appendChild(a);
        a.click();
        window.document.body.removeChild(a);
        URL.revokeObjectURL(fileURL);
    }

    newGenerator() {
        this.model = new JHipsterConfigurationModel(
            'monolith',
            '',
            'jhipsterSampleApplication',
            'io.github.jhipster.application',
            8080,
            false,
            'jwt',
            '../uaa',
            'ehcache',
            true,
            false,
            'sql',
            'h2Disk',
            'mysql',
            false,
            false,
            false,
            'maven',
            false,
            false,
            'yarn',
            [],
            false,
            'en',
            ['en'],
            'angularX'
        );
    }

    changeApplicationType() {
        // server port
        if (this.model.applicationType === 'microservice') {
            this.model.serverPort = 8081;
        } else if (this.model.applicationType === 'uaa') {
            this.model.serverPort = 9999;
        } else {
            this.model.serverPort = 8080;
        }
        // authentication
        if (this.model.applicationType !== 'microservice') {
            this.model.authenticationType = 'jwt';
        }
        // service discovery
        if (this.model.applicationType === 'gateway' || this.model.applicationType === 'microservice') {
            this.model.serviceDiscoveryType = 'eureka';
        }
        // database
        if (this.model.applicationType !== 'microservice' && this.model.databaseType === 'no') {
            this.model.databaseType = 'sql';
            this.changeDatabaseType();
        }
        // cache
        if (this.model.applicationType === 'microservice') {
            this.model.cacheProvider = 'hazelcast';
            this.model.enableHibernateCache = true;
        }
    }

    changeServiceDiscoveryType() {
        if (this.model.serviceDiscoveryType === 'eureka') {
            this.model.authenticationType = 'jwt';
        }
        if (this.model.serviceDiscoveryType === 'false') {
            this.model.serviceDiscoveryType = false;
        }
    }

    changeAuthenticationType() {
        this.model.databaseType = 'sql';
        this.model.clientFramework = 'angularX';
        this.changeDatabaseType();
    }

    changeDatabaseType() {
        if (this.model.databaseType === 'sql') {
            this.model.devDatabaseType = 'h2Disk';
            this.model.prodDatabaseType = 'mysql';
            this.model.cacheProvider = 'ehcache';
            this.model.enableHibernateCache = true;
        } else if (this.model.databaseType === 'mongodb') {
            this.model.devDatabaseType = 'mongodb';
            this.model.prodDatabaseType = 'mongodb';
            this.model.cacheProvider = 'no';
            this.model.enableHibernateCache = false;
        } else if (this.model.databaseType === 'cassandra') {
            this.model.devDatabaseType = 'cassandra';
            this.model.prodDatabaseType = 'cassandra';
            this.model.cacheProvider = 'no';
            this.model.enableHibernateCache = false;
            this.model.enableSocialSignIn = false;
            this.model.searchEngine = false;
        } else if (this.model.databaseType === 'couchbase') {
            this.model.devDatabaseType = 'couchbase';
            this.model.prodDatabaseType = 'couchbase';
            this.model.cacheProvider = 'no';
            this.model.enableHibernateCache = false;
            this.model.enableSocialSignIn = false;
            this.model.searchEngine = false;
        } else if (this.model.databaseType === 'no') {
            this.model.devDatabaseType = 'no';
            this.model.prodDatabaseType = 'no';
            this.model.cacheProvider = 'no';
            this.model.enableHibernateCache = false;
            this.model.searchEngine = false;
        }
    }

    changeProdDatabaseType() {
        if (this.model.devDatabaseType === this.model.prodDatabaseType) {
            return;
        }
        if (this.model.databaseType === 'sql') {
            this.model.devDatabaseType = 'h2Disk';
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
}
