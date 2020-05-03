package io.github.jhipster.online.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import io.github.jhipster.config.jcache.BeanClassLoaderAwareJCacheRegionFactory;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        BeanClassLoaderAwareJCacheRegionFactory.setBeanClassLoader(this.getClass().getClassLoader());
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
            cm.createCache(io.github.jhipster.online.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.online.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(io.github.jhipster.online.domain.JdlMetadata.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.online.domain.SubGenEvent.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.online.domain.EntityStats.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.online.domain.Language.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.online.domain.GeneratorIdentity.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.online.domain.UserApplication.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.online.domain.YoRC.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
