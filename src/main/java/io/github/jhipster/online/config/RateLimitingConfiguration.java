//package io.github.jhipster.online.config;
//
//import io.github.jhipster.config.JHipsterProperties;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * Configures the Zuul filter that limits the number of API calls per user.
// * <p>
// * This uses Bucket4J to limit the API calls, see {@link io.github.jhipster.online.config.RateLimitingFilter}.
// */
//@Configuration
//@ConditionalOnProperty("jhipster.gateway.rate-limiting.enabled")
//public class RateLimitingConfiguration {
//
//    private final JHipsterProperties jHipsterProperties;
//
//    public RateLimitingConfiguration(JHipsterProperties jHipsterProperties) {
//        this.jHipsterProperties = jHipsterProperties;
//    }
//
//    @Bean
//    public RateLimitingFilter rateLimitingFilter() {
//        return new RateLimitingFilter(jHipsterProperties);
//    }
//}
