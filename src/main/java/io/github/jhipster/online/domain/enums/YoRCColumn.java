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

package io.github.jhipster.online.domain.enums;

import io.github.jhipster.online.domain.interfaces.DatabaseColumn;

public enum YoRCColumn implements DatabaseColumn {
    SERVER_PORT("serverPort"),
    AUTHENTICATION_TYPE("authenticationType"),
    CACHE_PROVIDER("cacheProvider"),
    ENABLE_HIBERNATE_CACHE("enableHibernateCache"),
    WEB_SOCKET("websocket"),
    DATABASE_TYPE("databaseType"),
    PROD_DATABASE_TYPE("prodDatabaseType"),
    SEARCH_ENGINE("searchEngine"),
    JHIPSTER_VERSION("jhipsterVersion"),
    BUILD_TOOL("buildTool"),
    CLIENT_FRAMEWORK("clientFramework"),
    USE_SASS("useSass"),
    CLIENT_PACKAGE_MANAGER("clientPackageManager"),
    APPLICATION_TYPE("applicationType"),
    ENABLE_TRANSLATION("enableTranslation"),
    NATIVE_LANGUAGE("nativeLanguage");

    private final String value;

    YoRCColumn(String value) {
        this.value = value;
    }

    @Override
    public String getDatabaseValue() {
        return value;
    }
}
