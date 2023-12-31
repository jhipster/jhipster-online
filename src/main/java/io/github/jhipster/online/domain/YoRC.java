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

package io.github.jhipster.online.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.github.jhipster.online.domain.deserializer.YoRCDeserializer;
import io.github.jhipster.online.domain.interfaces.CompleteDate;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A YoRC.
 */
@Entity
@Table(name = "yo_rc")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@JsonDeserialize(using = YoRCDeserializer.class)
public class YoRC implements Serializable, CompleteDate {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jhipster_version")
    private String jhipsterVersion;

    @Column(name = "creation_date")
    private Instant creationDate;

    @Column(name = "git_provider")
    private String gitProvider;

    @Column(name = "node_version")
    private String nodeVersion;

    @Column(name = "os")
    private String os;

    @Column(name = "arch")
    private String arch;

    @Column(name = "cpu")
    private String cpu;

    @Column(name = "cores")
    private String cores;

    @Column(name = "memory")
    private String memory;

    @Column(name = "user_language")
    private String userLanguage;

    @Column(name = "jhi_year")
    private Integer year;

    @Column(name = "month")
    private Integer month;

    @Column(name = "week")
    private Integer week;

    @Column(name = "day")
    private Integer day;

    @Column(name = "hour")
    private Integer hour;

    @Column(name = "server_port")
    private String serverPort;

    @Column(name = "authentication_type")
    private String authenticationType;

    @Column(name = "cache_provider")
    private String cacheProvider;

    @Column(name = "enable_hibernate_cache")
    private Boolean enableHibernateCache;

    @Column(name = "websocket")
    private Boolean websocket;

    @Column(name = "database_type")
    private String databaseType;

    @Column(name = "dev_database_type")
    private String devDatabaseType;

    @Column(name = "prod_database_type")
    private String prodDatabaseType;

    @Column(name = "search_engine")
    private Boolean searchEngine;

    @Column(name = "message_broker")
    private Boolean messageBroker;

    @Column(name = "service_discovery_type")
    private Boolean serviceDiscoveryType;

    @Column(name = "build_tool")
    private String buildTool;

    @Column(name = "enable_swagger_codegen")
    private Boolean enableSwaggerCodegen;

    @Column(name = "client_framework")
    private String clientFramework;

    @Column(name = "with_admin_ui")
    private Boolean withAdminUi;

    @Column(name = "use_sass")
    private Boolean useSass;

    @Column(name = "client_package_manager")
    private String clientPackageManager;

    @Column(name = "application_type")
    private String applicationType;

    @Column(name = "jhi_prefix")
    private String jhiPrefix;

    @Column(name = "enable_translation")
    private Boolean enableTranslation;

    @Column(name = "native_language")
    private String nativeLanguage;

    @Column(name = "has_protractor")
    private Boolean hasProtractor;

    @Column(name = "has_gatling")
    private Boolean hasGatling;

    @Column(name = "has_cucumber")
    private Boolean hasCucumber;

    @ElementCollection
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<String> selectedLanguages = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("")
    private GeneratorIdentity owner;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJhipsterVersion() {
        return jhipsterVersion;
    }

    public YoRC jhipsterVersion(String jhipsterVersion) {
        this.jhipsterVersion = jhipsterVersion;
        return this;
    }

    public void setJhipsterVersion(String jhipsterVersion) {
        this.jhipsterVersion = jhipsterVersion;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public YoRC creationDate(Instant creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public String getGitProvider() {
        return gitProvider;
    }

    public YoRC gitProvider(String gitProvider) {
        this.gitProvider = gitProvider;
        return this;
    }

    public void setGitProvider(String gitProvider) {
        this.gitProvider = gitProvider;
    }

    public String getNodeVersion() {
        return nodeVersion;
    }

    public YoRC nodeVersion(String nodeVersion) {
        this.nodeVersion = nodeVersion;
        return this;
    }

    public void setNodeVersion(String nodeVersion) {
        this.nodeVersion = nodeVersion;
    }

    public String getOs() {
        return os;
    }

    public YoRC os(String os) {
        this.os = os;
        return this;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getArch() {
        return arch;
    }

    public YoRC arch(String arch) {
        this.arch = arch;
        return this;
    }

    public void setArch(String arch) {
        this.arch = arch;
    }

    public String getCpu() {
        return cpu;
    }

    public YoRC cpu(String cpu) {
        this.cpu = cpu;
        return this;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getCores() {
        return cores;
    }

    public YoRC cores(String cores) {
        this.cores = cores;
        return this;
    }

    public void setCores(String cores) {
        this.cores = cores;
    }

    public String getMemory() {
        return memory;
    }

    public YoRC memory(String memory) {
        this.memory = memory;
        return this;
    }

    public void setMemory(String memory) {
        this.memory = memory;
    }

    public String getUserLanguage() {
        return userLanguage;
    }

    public YoRC userLanguage(String userLanguage) {
        this.userLanguage = userLanguage;
        return this;
    }

    public void setUserLanguage(String userLanguage) {
        this.userLanguage = userLanguage;
    }

    public Integer getYear() {
        return year;
    }

    public YoRC year(Integer year) {
        this.year = year;
        return this;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public YoRC month(Integer month) {
        this.month = month;
        return this;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getWeek() {
        return week;
    }

    public YoRC week(Integer week) {
        this.week = week;
        return this;
    }

    public void setWeek(Integer week) {
        this.week = week;
    }

    public Integer getDay() {
        return day;
    }

    public YoRC day(Integer day) {
        this.day = day;
        return this;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getHour() {
        return hour;
    }

    public YoRC hour(Integer hour) {
        this.hour = hour;
        return this;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public String getServerPort() {
        return serverPort;
    }

    public YoRC serverPort(String serverPort) {
        this.serverPort = serverPort;
        return this;
    }

    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }

    public String getAuthenticationType() {
        return authenticationType;
    }

    public YoRC authenticationType(String authenticationType) {
        this.authenticationType = authenticationType;
        return this;
    }

    public void setAuthenticationType(String authenticationType) {
        this.authenticationType = authenticationType;
    }

    public String getCacheProvider() {
        return cacheProvider;
    }

    public YoRC cacheProvider(String cacheProvider) {
        this.cacheProvider = cacheProvider;
        return this;
    }

    public void setCacheProvider(String cacheProvider) {
        this.cacheProvider = cacheProvider;
    }

    public Boolean isEnableHibernateCache() {
        return enableHibernateCache;
    }

    public YoRC enableHibernateCache(Boolean enableHibernateCache) {
        this.enableHibernateCache = enableHibernateCache;
        return this;
    }

    public void setEnableHibernateCache(Boolean enableHibernateCache) {
        this.enableHibernateCache = enableHibernateCache;
    }

    public Boolean isWebsocket() {
        return websocket;
    }

    public YoRC websocket(Boolean websocket) {
        this.websocket = websocket;
        return this;
    }

    public void setWebsocket(Boolean websocket) {
        this.websocket = websocket;
    }

    public String getDatabaseType() {
        return databaseType;
    }

    public YoRC databaseType(String databaseType) {
        this.databaseType = databaseType;
        return this;
    }

    public void setDatabaseType(String databaseType) {
        this.databaseType = databaseType;
    }

    public String getDevDatabaseType() {
        return devDatabaseType;
    }

    public YoRC devDatabaseType(String devDatabaseType) {
        this.devDatabaseType = devDatabaseType;
        return this;
    }

    public void setDevDatabaseType(String devDatabaseType) {
        this.devDatabaseType = devDatabaseType;
    }

    public String getProdDatabaseType() {
        return prodDatabaseType;
    }

    public YoRC prodDatabaseType(String prodDatabaseType) {
        this.prodDatabaseType = prodDatabaseType;
        return this;
    }

    public void setProdDatabaseType(String prodDatabaseType) {
        this.prodDatabaseType = prodDatabaseType;
    }

    public Boolean isSearchEngine() {
        return searchEngine;
    }

    public YoRC searchEngine(Boolean searchEngine) {
        this.searchEngine = searchEngine;
        return this;
    }

    public void setSearchEngine(Boolean searchEngine) {
        this.searchEngine = searchEngine;
    }

    public Boolean isMessageBroker() {
        return messageBroker;
    }

    public YoRC messageBroker(Boolean messageBroker) {
        this.messageBroker = messageBroker;
        return this;
    }

    public void setMessageBroker(Boolean messageBroker) {
        this.messageBroker = messageBroker;
    }

    public Boolean isServiceDiscoveryType() {
        return serviceDiscoveryType;
    }

    public YoRC serviceDiscoveryType(Boolean serviceDiscoveryType) {
        this.serviceDiscoveryType = serviceDiscoveryType;
        return this;
    }

    public void setServiceDiscoveryType(Boolean serviceDiscoveryType) {
        this.serviceDiscoveryType = serviceDiscoveryType;
    }

    public String getBuildTool() {
        return buildTool;
    }

    public YoRC buildTool(String buildTool) {
        this.buildTool = buildTool;
        return this;
    }

    public void setBuildTool(String buildTool) {
        this.buildTool = buildTool;
    }

    public Boolean isEnableSwaggerCodegen() {
        return enableSwaggerCodegen;
    }

    public YoRC enableSwaggerCodegen(Boolean enableSwaggerCodegen) {
        this.enableSwaggerCodegen = enableSwaggerCodegen;
        return this;
    }

    public void setEnableSwaggerCodegen(Boolean enableSwaggerCodegen) {
        this.enableSwaggerCodegen = enableSwaggerCodegen;
    }

    public String getClientFramework() {
        return clientFramework;
    }

    public YoRC clientFramework(String clientFramework) {
        this.clientFramework = clientFramework;
        return this;
    }

    public void setClientFramework(String clientFramework) {
        this.clientFramework = clientFramework;
    }

    public Boolean isWithAdminUi() {
        return withAdminUi;
    }

    public YoRC withAdminUi(Boolean withAdminUi) {
        this.withAdminUi = withAdminUi;
        return this;
    }

    public void setWithAdminUi(Boolean withAdminUi) {
        this.withAdminUi = withAdminUi;
    }

    public Boolean isUseSass() {
        return useSass;
    }

    public YoRC useSass(Boolean useSass) {
        this.useSass = useSass;
        return this;
    }

    public void setUseSass(Boolean useSass) {
        this.useSass = useSass;
    }

    public String getClientPackageManager() {
        return clientPackageManager;
    }

    public YoRC clientPackageManager(String clientPackageManager) {
        this.clientPackageManager = clientPackageManager;
        return this;
    }

    public void setClientPackageManager(String clientPackageManager) {
        this.clientPackageManager = clientPackageManager;
    }

    public String getApplicationType() {
        return applicationType;
    }

    public YoRC applicationType(String applicationType) {
        this.applicationType = applicationType;
        return this;
    }

    public void setApplicationType(String applicationType) {
        this.applicationType = applicationType;
    }

    public String getJhiPrefix() {
        return jhiPrefix;
    }

    public YoRC jhiPrefix(String jhiPrefix) {
        this.jhiPrefix = jhiPrefix;
        return this;
    }

    public void setJhiPrefix(String jhiPrefix) {
        this.jhiPrefix = jhiPrefix;
    }

    public Boolean isEnableTranslation() {
        return enableTranslation;
    }

    public YoRC enableTranslation(Boolean enableTranslation) {
        this.enableTranslation = enableTranslation;
        return this;
    }

    public void setEnableTranslation(Boolean enableTranslation) {
        this.enableTranslation = enableTranslation;
    }

    public String getNativeLanguage() {
        return nativeLanguage;
    }

    public YoRC nativeLanguage(String nativeLanguage) {
        this.nativeLanguage = nativeLanguage;
        return this;
    }

    public void setNativeLanguage(String nativeLanguage) {
        this.nativeLanguage = nativeLanguage;
    }

    public Boolean isHasProtractor() {
        return hasProtractor;
    }

    public YoRC hasProtractor(Boolean hasProtractor) {
        this.hasProtractor = hasProtractor;
        return this;
    }

    public void setHasProtractor(Boolean hasProtractor) {
        this.hasProtractor = hasProtractor;
    }

    public Boolean isHasGatling() {
        return hasGatling;
    }

    public YoRC hasGatling(Boolean hasGatling) {
        this.hasGatling = hasGatling;
        return this;
    }

    public void setHasGatling(Boolean hasGatling) {
        this.hasGatling = hasGatling;
    }

    public Boolean isHasCucumber() {
        return hasCucumber;
    }

    public YoRC hasCucumber(Boolean hasCucumber) {
        this.hasCucumber = hasCucumber;
        return this;
    }

    public void setHasCucumber(Boolean hasCucumber) {
        this.hasCucumber = hasCucumber;
    }

    public Set<String> getSelectedLanguages() {
        return selectedLanguages;
    }

    public YoRC selectedLanguages(Set<String> languages) {
        this.selectedLanguages = languages;
        return this;
    }

    public YoRC addSelectedLanguages(String language) {
        this.selectedLanguages.add(language);
        return this;
    }

    public YoRC removeSelectedLanguages(String language) {
        this.selectedLanguages.remove(language);
        return this;
    }

    public void setSelectedLanguages(Set<String> languages) {
        this.selectedLanguages = languages;
    }

    public GeneratorIdentity getOwner() {
        return owner;
    }

    public YoRC owner(GeneratorIdentity ownerIdentity) {
        this.owner = ownerIdentity;
        return this;
    }

    public void setOwner(GeneratorIdentity ownerIdentity) {
        this.owner = ownerIdentity;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        YoRC yoRC = (YoRC) o;
        if (yoRC.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), yoRC.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return (
            "YoRC{" +
            "id=" +
            getId() +
            ", jhipsterVersion='" +
            getJhipsterVersion() +
            "'" +
            ", creationDate='" +
            getCreationDate() +
            "'" +
            ", gitProvider='" +
            getGitProvider() +
            "'" +
            ", nodeVersion='" +
            getNodeVersion() +
            "'" +
            ", os='" +
            getOs() +
            "'" +
            ", arch='" +
            getArch() +
            "'" +
            ", cpu='" +
            getCpu() +
            "'" +
            ", cores='" +
            getCores() +
            "'" +
            ", memory='" +
            getMemory() +
            "'" +
            ", userLanguage='" +
            getUserLanguage() +
            "'" +
            ", year=" +
            getYear() +
            ", month=" +
            getMonth() +
            ", week=" +
            getWeek() +
            ", day=" +
            getDay() +
            ", hour=" +
            getHour() +
            ", serverPort='" +
            getServerPort() +
            "'" +
            ", authenticationType='" +
            getAuthenticationType() +
            "'" +
            ", cacheProvider='" +
            getCacheProvider() +
            "'" +
            ", enableHibernateCache='" +
            isEnableHibernateCache() +
            "'" +
            ", websocket='" +
            isWebsocket() +
            "'" +
            ", databaseType='" +
            getDatabaseType() +
            "'" +
            ", devDatabaseType='" +
            getDevDatabaseType() +
            "'" +
            ", prodDatabaseType='" +
            getProdDatabaseType() +
            "'" +
            ", searchEngine='" +
            isSearchEngine() +
            "'" +
            ", messageBroker='" +
            isMessageBroker() +
            "'" +
            ", serviceDiscoveryType='" +
            isServiceDiscoveryType() +
            "'" +
            ", buildTool='" +
            getBuildTool() +
            "'" +
            ", enableSwaggerCodegen='" +
            isEnableSwaggerCodegen() +
            "'" +
            ", clientFramework='" +
            getClientFramework() +
            "'" +
            ", withAdminUi='" +
            isWithAdminUi() +
            "'" +
            ", useSass='" +
            isUseSass() +
            "'" +
            ", clientPackageManager='" +
            getClientPackageManager() +
            "'" +
            ", applicationType='" +
            getApplicationType() +
            "'" +
            ", jhiPrefix='" +
            getJhiPrefix() +
            "'" +
            ", enableTranslation='" +
            isEnableTranslation() +
            "'" +
            ", nativeLanguage='" +
            getNativeLanguage() +
            "'" +
            ", hasProtractor='" +
            isHasProtractor() +
            "'" +
            ", hasGatling='" +
            isHasGatling() +
            "'" +
            ", hasCucumber='" +
            isHasCucumber() +
            "'" +
            "}"
        );
    }
}
