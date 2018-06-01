package io.github.jhipster.online.config;

import io.github.jhipster.online.repository.GitCompanyRepository;
import io.github.jhipster.online.repository.UserRepository;
import io.github.jhipster.online.service.GeneratorService;
import io.github.jhipster.online.service.GithubService;
import io.github.jhipster.online.service.GitlabService;
import io.github.jhipster.online.service.LogsService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GitProviderConfiguration {

    @Bean
    @ConditionalOnProperty("application.github.clientId")
    public GithubService getGithubService(GeneratorService generatorService,
                                          LogsService logsService,
                                          ApplicationProperties applicationProperties,
                                          GitCompanyRepository gitCompanyRepository,
                                          UserRepository userRepository) {
        return new GithubService(generatorService, logsService, applicationProperties, gitCompanyRepository, userRepository);
    }

    @Bean
    @ConditionalOnProperty("application.gitlab.clientId")
    public GitlabService getGitlabService(GeneratorService generatorService,
                                          LogsService logsService,
                                          ApplicationProperties applicationProperties,
                                          GitCompanyRepository gitCompanyRepository,
                                          UserRepository userRepository) {
        return new GitlabService(generatorService, applicationProperties, logsService, userRepository, gitCompanyRepository);
    }
}
