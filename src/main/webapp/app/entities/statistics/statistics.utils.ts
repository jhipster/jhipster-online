/**
 * Copyright 2017-2020 the original author or authors from the JHipster Online project.
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
import { Injectable } from '@angular/core';

export const displayNames = {
    react: 'React',
    angularX: 'Angular',
    angularJS: 'AngularJS',
    vuejs: 'Vue.js',

    heroku: 'Heroku',
    kubernetes: 'Kubernetes',
    aws: 'AWS',
    cloudfoundry: 'Cloud Foundry',
    openshift: 'OpenShift',

    postgresql: 'PostgreSQL',
    oracle: 'Oracle',
    mysql: 'MySQL',
    mssql: 'MsSQL',
    mariadb: 'MariaDB',
    cassandra: 'Cassandra',
    couchbase: 'Couchbase',
    mongodb: 'MongoDB',
    db2: 'Db2',

    ehcache: 'Ehcache',
    hazelcast: 'Hazelcast',
    infinispan: 'Infinispan',

    maven: 'Maven',
    gradle: 'Gradle',

    monolithic: 'Monolithic',
    gateway: 'Gateway',
    microservice: 'Microservice',
    uaa: 'UAA',

    default: 'None/Other'
};

export const computeAngularKey = (lowercaseKey: string) => {
    let key;
    if (lowercaseKey !== 'angular') {
        const angularVersion = lowercaseKey.slice(-1);
        if (angularVersion === 'x') {
            key = displayNames.angularX;
        } else {
            key = /^\d$/.test(angularVersion) && Number(angularVersion) > 1 ? displayNames.angularX : displayNames.angularJS;
        }
    } else {
        key = displayNames.angularX;
    }
    return key;
};

@Injectable({ providedIn: 'root' })
export class StatisticsUtils {
    public static getDisplayName(name: string, upperFirst = true) {
        return upperFirst ? StatisticsUtils.upperFirst(displayNames[name] || name) : displayNames[name] || name;
    }

    public static upperFirst(text: string) {
        return text.charAt(0).toUpperCase() + text.slice(1);
    }
}
