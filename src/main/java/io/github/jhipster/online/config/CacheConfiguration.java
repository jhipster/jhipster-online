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

package io.github.jhipster.online.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.jhipster.config.JHipsterProperties;

@Configuration
@EnableCaching
@AutoConfigureAfter(value = { MetricsConfiguration.class })
@AutoConfigureBefore(value = { WebConfigurer.class, DatabaseConfiguration.class })
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    private final javax.cache.configuration.Configuration<Object, Object> statisticsJcacheConfiguration;

    public static final String STATISTICS_YORC_COUNT = "statisticsYorcCount";
    public static final String STATISTICS_JDL_COUNT = "statisticsJdlCount";
    public static final String STATISTICS_USERS_COUNT = "statisticsUsersCount";

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());

        statisticsJcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(100L))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofMinutes(5)))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(STATISTICS_YORC_COUNT, statisticsJcacheConfiguration);
            cm.createCache(STATISTICS_JDL_COUNT, statisticsJcacheConfiguration);
            cm.createCache(STATISTICS_USERS_COUNT, statisticsJcacheConfiguration);

            cm.createCache(io.github.jhipster.online.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(io.github.jhipster.online.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(io.github.jhipster.online.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.online.domain.User.class.getName() + ".gitCompanies", jcacheConfiguration);
            cm.createCache(io.github.jhipster.online.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.online.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(io.github.jhipster.online.domain.Jdl.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.online.domain.JdlMetadata.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.online.domain.GitCompany.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.online.domain.GitCompany.class.getName() + ".gitProjects", jcacheConfiguration);
            cm.createCache(io.github.jhipster.online.domain.YoRC.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.online.domain.YoRC.class.getName() + ".languages", jcacheConfiguration);
            cm.createCache(io.github.jhipster.online.domain.GeneratorIdentity.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.online.domain.YoRC.class.getName() + ".testFrameworks", jcacheConfiguration);
            cm.createCache(io.github.jhipster.online.domain.YoRC.class.getName() + ".selectedLanguages", jcacheConfiguration);
            cm.createCache(io.github.jhipster.online.domain.SubGenEvent.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.online.domain.EntityStats.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
