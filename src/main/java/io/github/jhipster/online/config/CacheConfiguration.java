package io.github.jhipster.online.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
@AutoConfigureAfter(value = { MetricsConfiguration.class })
@AutoConfigureBefore(value = { WebConfigurer.class, DatabaseConfiguration.class })
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(io.github.jhipster.online.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(io.github.jhipster.online.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(io.github.jhipster.online.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.online.domain.User.class.getName() + ".githubOrganizations", jcacheConfiguration);
            cm.createCache(io.github.jhipster.online.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.online.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(io.github.jhipster.online.domain.Jdl.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.online.domain.JdlMetadata.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.online.domain.GithubOrganization.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.online.domain.GithubOrganization.class.getName() + ".githubProjects", jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
