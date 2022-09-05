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

package io.github.jhipster.online.config;

import io.github.jhipster.config.JHipsterProperties;
import java.time.Duration;
import javax.cache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    private final javax.cache.configuration.Configuration<Object, Object> statisticsJcacheConfiguration;

    public static final String STATISTICS_YORC_COUNT = "statisticsYorcCount";
    public static final String STATISTICS_JDL_COUNT = "statisticsJdlCount";
    public static final String STATISTICS_USERS_COUNT = "statisticsUsersCount";

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration =
            Eh107Configuration.fromEhcacheCacheConfiguration(
                CacheConfigurationBuilder
                    .newCacheConfigurationBuilder(Object.class, Object.class, ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                    .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                    .build()
            );

        statisticsJcacheConfiguration =
            Eh107Configuration.fromEhcacheCacheConfiguration(
                CacheConfigurationBuilder
                    .newCacheConfigurationBuilder(Object.class, Object.class, ResourcePoolsBuilder.heap(100L))
                    .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofMinutes(5)))
                    .build()
            );
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createIfNotExists(cm, STATISTICS_YORC_COUNT, statisticsJcacheConfiguration);
            createIfNotExists(cm, STATISTICS_JDL_COUNT, statisticsJcacheConfiguration);
            createIfNotExists(cm, STATISTICS_USERS_COUNT, statisticsJcacheConfiguration);

            createIfNotExists(cm, io.github.jhipster.online.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            createIfNotExists(cm, io.github.jhipster.online.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            createIfNotExists(cm, io.github.jhipster.online.domain.User.class.getName(), jcacheConfiguration);
            createIfNotExists(cm, io.github.jhipster.online.domain.User.class.getName() + ".gitCompanies", jcacheConfiguration);
            createIfNotExists(cm, io.github.jhipster.online.domain.Authority.class.getName(), jcacheConfiguration);
            createIfNotExists(cm, io.github.jhipster.online.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            createIfNotExists(cm, io.github.jhipster.online.domain.Jdl.class.getName(), jcacheConfiguration);
            createIfNotExists(cm, io.github.jhipster.online.domain.JdlMetadata.class.getName(), jcacheConfiguration);
            createIfNotExists(cm, io.github.jhipster.online.domain.GitCompany.class.getName(), jcacheConfiguration);
            createIfNotExists(cm, io.github.jhipster.online.domain.GitCompany.class.getName() + ".gitProjects", jcacheConfiguration);
            createIfNotExists(cm, io.github.jhipster.online.domain.YoRC.class.getName(), jcacheConfiguration);
            createIfNotExists(cm, io.github.jhipster.online.domain.YoRC.class.getName() + ".languages", jcacheConfiguration);
            createIfNotExists(cm, io.github.jhipster.online.domain.GeneratorIdentity.class.getName(), jcacheConfiguration);
            createIfNotExists(cm, io.github.jhipster.online.domain.YoRC.class.getName() + ".testFrameworks", jcacheConfiguration);
            createIfNotExists(cm, io.github.jhipster.online.domain.YoRC.class.getName() + ".selectedLanguages", jcacheConfiguration);
            createIfNotExists(cm, io.github.jhipster.online.domain.SubGenEvent.class.getName(), jcacheConfiguration);
            createIfNotExists(cm, io.github.jhipster.online.domain.EntityStats.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createIfNotExists(
        CacheManager cacheManager,
        String cacheName,
        javax.cache.configuration.Configuration<Object, Object> cacheConfiguration
    ) {
        if (cacheManager.getCache(cacheName) == null) {
            cacheManager.createCache(cacheName, cacheConfiguration);
        }
    }
}
