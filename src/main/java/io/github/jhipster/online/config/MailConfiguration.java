package io.github.jhipster.online.config;

import io.github.jhipster.config.JHipsterProperties;
import io.github.jhipster.online.service.MailService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.spring5.SpringTemplateEngine;


@Configuration
public class MailConfiguration {

    @Bean
    @ConditionalOnProperty(name = "application.mail.enable", havingValue = "true")
    public MailService getMailService(JHipsterProperties jHipsterProperties,
                                      JavaMailSender javaMailSender,
                                      MessageSource messageSource,
                                      SpringTemplateEngine templateEngine) {

        return new MailService(jHipsterProperties, javaMailSender, messageSource, templateEngine);
    }





}
