package io.github.jhipster.online.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A YoRC.
 */
@Entity
@Table(name = "yo_rc")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class YoRC implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jhipster_version")
    private String jhipsterVersion;

    @Column(name = "server_port")
    private String serverPort;

    @Column(name = "authentication_type")
    private String authenticationType;

    @Column(name = "cache_provider")
    private String cacheProvider;

    @Column(name = "enable_hibernate_cache")
    private String enableHibernateCache;

    @Column(name = "websocket")
    private String websocket;

    @Column(name = "database_type")
    private String databaseType;

    @Column(name = "dev_database_type")
    private String devDatabaseType;

    @Column(name = "prod_database_type")
    private String prodDatabaseType;

    @Column(name = "search_engine")
    private String searchEngine;

    @Column(name = "message_broker")
    private String messageBroker;

    @Column(name = "service_discovery_type")
    private String serviceDiscoveryType;

    @Column(name = "build_tool")
    private String buildTool;

    @Column(name = "enable_swagger_codegen")
    private String enableSwaggerCodegen;

    @Column(name = "client_framework")
    private String clientFramework;

    @Column(name = "use_sass")
    private String useSass;

    @Column(name = "client_package_manager")
    private String clientPackageManager;

    @Column(name = "application_type")
    private String applicationType;

    @Column(name = "test_frameworks")
    private String testFrameworks;

    @Column(name = "jhi_prefix")
    private String jhiPrefix;

    @Column(name = "enable_translation")
    private String enableTranslation;

    @Column(name = "native_language")
    private String nativeLanguage;

    @Column(name = "git_provider")
    private String gitProvider;

    @ManyToOne
    @JsonIgnoreProperties("")
    private User owner;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "yorc_languages",
               joinColumns = @JoinColumn(name = "yorcs_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "languages_id", referencedColumnName = "id"))
    private Set<Language> languages = new HashSet<>();

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

    public String getEnableHibernateCache() {
        return enableHibernateCache;
    }

    public YoRC enableHibernateCache(String enableHibernateCache) {
        this.enableHibernateCache = enableHibernateCache;
        return this;
    }

    public void setEnableHibernateCache(String enableHibernateCache) {
        this.enableHibernateCache = enableHibernateCache;
    }

    public String getWebsocket() {
        return websocket;
    }

    public YoRC websocket(String websocket) {
        this.websocket = websocket;
        return this;
    }

    public void setWebsocket(String websocket) {
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

    public String getSearchEngine() {
        return searchEngine;
    }

    public YoRC searchEngine(String searchEngine) {
        this.searchEngine = searchEngine;
        return this;
    }

    public void setSearchEngine(String searchEngine) {
        this.searchEngine = searchEngine;
    }

    public String getMessageBroker() {
        return messageBroker;
    }

    public YoRC messageBroker(String messageBroker) {
        this.messageBroker = messageBroker;
        return this;
    }

    public void setMessageBroker(String messageBroker) {
        this.messageBroker = messageBroker;
    }

    public String getServiceDiscoveryType() {
        return serviceDiscoveryType;
    }

    public YoRC serviceDiscoveryType(String serviceDiscoveryType) {
        this.serviceDiscoveryType = serviceDiscoveryType;
        return this;
    }

    public void setServiceDiscoveryType(String serviceDiscoveryType) {
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

    public String getEnableSwaggerCodegen() {
        return enableSwaggerCodegen;
    }

    public YoRC enableSwaggerCodegen(String enableSwaggerCodegen) {
        this.enableSwaggerCodegen = enableSwaggerCodegen;
        return this;
    }

    public void setEnableSwaggerCodegen(String enableSwaggerCodegen) {
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

    public String getUseSass() {
        return useSass;
    }

    public YoRC useSass(String useSass) {
        this.useSass = useSass;
        return this;
    }

    public void setUseSass(String useSass) {
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

    public String getTestFrameworks() {
        return testFrameworks;
    }

    public YoRC testFrameworks(String testFrameworks) {
        this.testFrameworks = testFrameworks;
        return this;
    }

    public void setTestFrameworks(String testFrameworks) {
        this.testFrameworks = testFrameworks;
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

    public String getEnableTranslation() {
        return enableTranslation;
    }

    public YoRC enableTranslation(String enableTranslation) {
        this.enableTranslation = enableTranslation;
        return this;
    }

    public void setEnableTranslation(String enableTranslation) {
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

    public User getOwner() {
        return owner;
    }

    public YoRC owner(User user) {
        this.owner = user;
        return this;
    }

    public void setOwner(User user) {
        this.owner = user;
    }

    public Set<Language> getLanguages() {
        return languages;
    }

    public YoRC languages(Set<Language> languages) {
        this.languages = languages;
        return this;
    }

    public YoRC addLanguages(Language language) {
        this.languages.add(language);
        language.getYorcs().add(this);
        return this;
    }

    public YoRC removeLanguages(Language language) {
        this.languages.remove(language);
        language.getYorcs().remove(this);
        return this;
    }

    public void setLanguages(Set<Language> languages) {
        this.languages = languages;
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
        return "YoRC{" +
            "id=" + getId() +
            ", jhipsterVersion='" + getJhipsterVersion() + "'" +
            ", serverPort='" + getServerPort() + "'" +
            ", authenticationType='" + getAuthenticationType() + "'" +
            ", cacheProvider='" + getCacheProvider() + "'" +
            ", enableHibernateCache='" + getEnableHibernateCache() + "'" +
            ", websocket='" + getWebsocket() + "'" +
            ", databaseType='" + getDatabaseType() + "'" +
            ", devDatabaseType='" + getDevDatabaseType() + "'" +
            ", prodDatabaseType='" + getProdDatabaseType() + "'" +
            ", searchEngine='" + getSearchEngine() + "'" +
            ", messageBroker='" + getMessageBroker() + "'" +
            ", serviceDiscoveryType='" + getServiceDiscoveryType() + "'" +
            ", buildTool='" + getBuildTool() + "'" +
            ", enableSwaggerCodegen='" + getEnableSwaggerCodegen() + "'" +
            ", clientFramework='" + getClientFramework() + "'" +
            ", useSass='" + getUseSass() + "'" +
            ", clientPackageManager='" + getClientPackageManager() + "'" +
            ", applicationType='" + getApplicationType() + "'" +
            ", testFrameworks='" + getTestFrameworks() + "'" +
            ", jhiPrefix='" + getJhiPrefix() + "'" +
            ", enableTranslation='" + getEnableTranslation() + "'" +
            ", nativeLanguage='" + getNativeLanguage() + "'" +
            ", gitProvider='" + getGitProvider() + "'" +
            "}";
    }
}
