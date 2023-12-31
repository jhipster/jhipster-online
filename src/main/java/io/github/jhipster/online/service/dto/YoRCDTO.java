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
package io.github.jhipster.online.service.dto;

import java.io.Serializable;
import java.time.Instant;

/**
 * A DTO for the {@link io.github.jhipster.online.domain.YoRC} entity.
 */
public class YoRCDTO implements Serializable {

    private Long id;

    private String jhipsterVersion;

    private Instant creationDate;

    private String gitProvider;

    private String nodeVersion;

    private String os;

    private String arch;

    private String cpu;

    private String cores;

    private String memory;

    private String userLanguage;

    private Integer year;

    private Integer month;

    private Integer week;

    private Integer day;

    private Integer hour;

    private String serverPort;

    private String authenticationType;

    private String cacheProvider;

    private Boolean enableHibernateCache;

    private Boolean websocket;

    private String databaseType;

    private String devDatabaseType;

    private String prodDatabaseType;

    private Boolean searchEngine;

    private Boolean messageBroker;

    private Boolean serviceDiscoveryType;

    private String buildTool;

    private Boolean enableSwaggerCodegen;

    private String clientFramework;

    private Boolean withAdminUi;

    private Boolean useSass;

    private String clientPackageManager;

    private String applicationType;

    private String jhiPrefix;

    private Boolean enableTranslation;

    private String nativeLanguage;

    private Boolean hasProtractor;

    private Boolean hasGatling;

    private Boolean hasCucumber;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJhipsterVersion() {
        return jhipsterVersion;
    }

    public void setJhipsterVersion(String jhipsterVersion) {
        this.jhipsterVersion = jhipsterVersion;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public String getGitProvider() {
        return gitProvider;
    }

    public void setGitProvider(String gitProvider) {
        this.gitProvider = gitProvider;
    }

    public String getNodeVersion() {
        return nodeVersion;
    }

    public void setNodeVersion(String nodeVersion) {
        this.nodeVersion = nodeVersion;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getArch() {
        return arch;
    }

    public void setArch(String arch) {
        this.arch = arch;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getCores() {
        return cores;
    }

    public void setCores(String cores) {
        this.cores = cores;
    }

    public String getMemory() {
        return memory;
    }

    public void setMemory(String memory) {
        this.memory = memory;
    }

    public String getUserLanguage() {
        return userLanguage;
    }

    public void setUserLanguage(String userLanguage) {
        this.userLanguage = userLanguage;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getWeek() {
        return week;
    }

    public void setWeek(Integer week) {
        this.week = week;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public String getServerPort() {
        return serverPort;
    }

    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }

    public String getAuthenticationType() {
        return authenticationType;
    }

    public void setAuthenticationType(String authenticationType) {
        this.authenticationType = authenticationType;
    }

    public String getCacheProvider() {
        return cacheProvider;
    }

    public void setCacheProvider(String cacheProvider) {
        this.cacheProvider = cacheProvider;
    }

    public Boolean getEnableHibernateCache() {
        return enableHibernateCache;
    }

    public void setEnableHibernateCache(Boolean enableHibernateCache) {
        this.enableHibernateCache = enableHibernateCache;
    }

    public Boolean getWebsocket() {
        return websocket;
    }

    public void setWebsocket(Boolean websocket) {
        this.websocket = websocket;
    }

    public String getDatabaseType() {
        return databaseType;
    }

    public void setDatabaseType(String databaseType) {
        this.databaseType = databaseType;
    }

    public String getDevDatabaseType() {
        return devDatabaseType;
    }

    public void setDevDatabaseType(String devDatabaseType) {
        this.devDatabaseType = devDatabaseType;
    }

    public String getProdDatabaseType() {
        return prodDatabaseType;
    }

    public void setProdDatabaseType(String prodDatabaseType) {
        this.prodDatabaseType = prodDatabaseType;
    }

    public Boolean getSearchEngine() {
        return searchEngine;
    }

    public void setSearchEngine(Boolean searchEngine) {
        this.searchEngine = searchEngine;
    }

    public Boolean getMessageBroker() {
        return messageBroker;
    }

    public void setMessageBroker(Boolean messageBroker) {
        this.messageBroker = messageBroker;
    }

    public Boolean getServiceDiscoveryType() {
        return serviceDiscoveryType;
    }

    public void setServiceDiscoveryType(Boolean serviceDiscoveryType) {
        this.serviceDiscoveryType = serviceDiscoveryType;
    }

    public String getBuildTool() {
        return buildTool;
    }

    public void setBuildTool(String buildTool) {
        this.buildTool = buildTool;
    }

    public Boolean getEnableSwaggerCodegen() {
        return enableSwaggerCodegen;
    }

    public void setEnableSwaggerCodegen(Boolean enableSwaggerCodegen) {
        this.enableSwaggerCodegen = enableSwaggerCodegen;
    }

    public String getClientFramework() {
        return clientFramework;
    }

    public void setClientFramework(String clientFramework) {
        this.clientFramework = clientFramework;
    }

    public Boolean getWithAdminUi() {
        return withAdminUi;
    }

    public void setWithAdminUi(Boolean withAdminUi) {
        this.withAdminUi = withAdminUi;
    }

    public Boolean getUseSass() {
        return useSass;
    }

    public void setUseSass(Boolean useSass) {
        this.useSass = useSass;
    }

    public String getClientPackageManager() {
        return clientPackageManager;
    }

    public void setClientPackageManager(String clientPackageManager) {
        this.clientPackageManager = clientPackageManager;
    }

    public String getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(String applicationType) {
        this.applicationType = applicationType;
    }

    public String getJhiPrefix() {
        return jhiPrefix;
    }

    public void setJhiPrefix(String jhiPrefix) {
        this.jhiPrefix = jhiPrefix;
    }

    public Boolean getEnableTranslation() {
        return enableTranslation;
    }

    public void setEnableTranslation(Boolean enableTranslation) {
        this.enableTranslation = enableTranslation;
    }

    public String getNativeLanguage() {
        return nativeLanguage;
    }

    public void setNativeLanguage(String nativeLanguage) {
        this.nativeLanguage = nativeLanguage;
    }

    public Boolean getHasProtractor() {
        return hasProtractor;
    }

    public void setHasProtractor(Boolean hasProtractor) {
        this.hasProtractor = hasProtractor;
    }

    public Boolean getHasGatling() {
        return hasGatling;
    }

    public void setHasGatling(Boolean hasGatling) {
        this.hasGatling = hasGatling;
    }

    public Boolean getHasCucumber() {
        return hasCucumber;
    }

    public void setHasCucumber(Boolean hasCucumber) {
        this.hasCucumber = hasCucumber;
    }

    @Override
    public String toString() {
        return (
            "YoRCDTO{" +
            "id=" +
            id +
            ", jhipsterVersion='" +
            jhipsterVersion +
            '\'' +
            ", creationDate=" +
            creationDate +
            ", gitProvider='" +
            gitProvider +
            '\'' +
            ", nodeVersion='" +
            nodeVersion +
            '\'' +
            ", os='" +
            os +
            '\'' +
            ", arch='" +
            arch +
            '\'' +
            ", cpu='" +
            cpu +
            '\'' +
            ", cores='" +
            cores +
            '\'' +
            ", memory='" +
            memory +
            '\'' +
            ", userLanguage='" +
            userLanguage +
            '\'' +
            ", year=" +
            year +
            ", month=" +
            month +
            ", week=" +
            week +
            ", day=" +
            day +
            ", hour=" +
            hour +
            ", serverPort='" +
            serverPort +
            '\'' +
            ", authenticationType='" +
            authenticationType +
            '\'' +
            ", cacheProvider='" +
            cacheProvider +
            '\'' +
            ", enableHibernateCache=" +
            enableHibernateCache +
            ", websocket=" +
            websocket +
            ", databaseType='" +
            databaseType +
            '\'' +
            ", devDatabaseType='" +
            devDatabaseType +
            '\'' +
            ", prodDatabaseType='" +
            prodDatabaseType +
            '\'' +
            ", searchEngine=" +
            searchEngine +
            ", messageBroker=" +
            messageBroker +
            ", serviceDiscoveryType=" +
            serviceDiscoveryType +
            ", buildTool='" +
            buildTool +
            '\'' +
            ", enableSwaggerCodegen=" +
            enableSwaggerCodegen +
            ", clientFramework='" +
            clientFramework +
            '\'' +
            ", withAdminUi='" +
            withAdminUi +
            '\'' +
            ", useSass=" +
            useSass +
            ", clientPackageManager='" +
            clientPackageManager +
            '\'' +
            ", applicationType='" +
            applicationType +
            '\'' +
            ", jhiPrefix='" +
            jhiPrefix +
            '\'' +
            ", enableTranslation=" +
            enableTranslation +
            ", nativeLanguage='" +
            nativeLanguage +
            '\'' +
            ", hasProtractor=" +
            hasProtractor +
            ", hasGatling=" +
            hasGatling +
            ", hasCucumber=" +
            hasCucumber +
            '}'
        );
    }
}
