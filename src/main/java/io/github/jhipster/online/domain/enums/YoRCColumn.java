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

    private String value;

    YoRCColumn(String value) {
        this.value = value;
    }

    @Override
    public String getDatabaseValue() {
        return value;
    }
}
