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

package io.github.jhipster.online.service;

import static java.util.Collections.EMPTY_LIST;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.config.JHipsterProperties;
import io.github.jhipster.online.config.CacheConfiguration;
import io.github.jhipster.online.config.Constants;
import io.github.jhipster.online.domain.*;
import io.github.jhipster.online.domain.enums.GitProvider;
import io.github.jhipster.online.repository.*;
import io.github.jhipster.online.security.AuthoritiesConstants;
import io.github.jhipster.online.security.SecurityUtils;
import io.github.jhipster.online.service.dto.UserDTO;
import io.github.jhipster.online.service.util.RandomUtil;
import io.github.jhipster.online.web.rest.errors.InvalidPasswordException;

/**
 * Service class for managing users.
 */
@Service
@Transactional
public class UserService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    private final GithubService githubService;

    private final GitlabService gitlabService;

    private final GitCompanyRepository gitCompanyRepository;

    private final MailService mailService;

    private final JHipsterProperties jHipsterProperties;

    private final PasswordEncoder passwordEncoder;

    private final AuthorityRepository authorityRepository;

    private final CacheManager cacheManager;

    private final JdlMetadataService jdlMetadataService;

    private final JdlService jdlService;

    public UserService(UserRepository userRepository,
        PasswordEncoder passwordEncoder,
        AuthorityRepository authorityRepository,
        GithubService githubService,
        CacheManager cacheManager,
        MailService mailService,
        JHipsterProperties jHipsterProperties,
        GitCompanyRepository gitCompanyRepository,
        GitlabService gitlabService, JdlMetadataService jdlMetadataService, JdlService jdlService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authorityRepository = authorityRepository;
        this.githubService = githubService;
        this.mailService = mailService;
        this.cacheManager = cacheManager;
        this.gitCompanyRepository = gitCompanyRepository;
        this.gitlabService = gitlabService;
        this.jHipsterProperties = jHipsterProperties;
        this.jdlMetadataService = jdlMetadataService;
        this.jdlService = jdlService;
    }

    public Optional<User> activateRegistration(String key) {
        log.debug("Activating user for activation key {}", key);
        return userRepository.findOneByActivationKey(key)
            .map(user -> {
                // activate given user for the registration key.
                user.setActivated(true);
                user.setActivationKey(null);
                this.clearUserCaches(user);
                log.debug("Activated user: {}", user);
                return user;
            });
    }

    public Optional<User> completePasswordReset(String newPassword, String key) {
        log.debug("Reset user password for reset key {}", key);

        return userRepository.findOneByResetKey(key)
            .filter(user -> user.getResetDate().isAfter(Instant.now().minusSeconds(86400)))
            .map(user -> {
                user.setPassword(passwordEncoder.encode(newPassword));
                user.setResetKey(null);
                user.setResetDate(null);
                this.clearUserCaches(user);
                return user;
            });
    }

    public Optional<User> requestPasswordReset(String mail) {
        return userRepository.findOneByEmailIgnoreCase(mail)
            .filter(User::getActivated)
            .map(user -> {
                user.setResetKey(RandomUtil.generateResetKey());
                user.setResetDate(Instant.now());
                this.clearUserCaches(user);
                return user;
            });
    }

    public String generatePasswordResetLink(String resetKey) {
        String baseUrl = jHipsterProperties.getMail().getBaseUrl();
        return baseUrl + "/#/reset/finish?key=" + resetKey;
    }

    public User registerUser(UserDTO userDTO, String password) {
        User newUser = new User();
        String encryptedPassword = passwordEncoder.encode(password);
        newUser.setLogin(userDTO.getLogin());
        // new user gets initially a generated password
        newUser.setPassword(encryptedPassword);
        newUser.setFirstName(userDTO.getFirstName());
        newUser.setLastName(userDTO.getLastName());
        newUser.setEmail(userDTO.getEmail());
        newUser.setImageUrl(userDTO.getImageUrl());
        newUser.setLangKey(userDTO.getLangKey());

        // new user is active if mails are disabled
        newUser.setActivated(!mailService.isEnabled());

        // new user gets registration key
        newUser.setActivationKey(RandomUtil.generateActivationKey());
        Set<Authority> authorities = new HashSet<>();
        authorityRepository.findById(AuthoritiesConstants.USER).ifPresent(authorities::add);
        newUser.setAuthorities(authorities);
        userRepository.save(newUser);
        this.clearUserCaches(newUser);
        log.debug("Created Information for User: {}", newUser);
        return newUser;
    }

    public User createUser(UserDTO userDTO) {
        User user = new User();
        user.setLogin(userDTO.getLogin());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setImageUrl(userDTO.getImageUrl());
        if (userDTO.getLangKey() == null) {
            user.setLangKey(Constants.DEFAULT_LANGUAGE); // default language
        } else {
            user.setLangKey(userDTO.getLangKey());
        }
        if (userDTO.getAuthorities() != null) {
            Set<Authority> authorities = userDTO.getAuthorities().stream()
                .map(authorityRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
            user.setAuthorities(authorities);
        }
        String encryptedPassword = passwordEncoder.encode(RandomUtil.generatePassword());
        user.setPassword(encryptedPassword);
        user.setResetKey(RandomUtil.generateResetKey());
        user.setResetDate(Instant.now());
        user.setActivated(true);
        userRepository.save(user);
        this.clearUserCaches(user);
        log.debug("Created Information for User: {}", user);
        return user;
    }

    /**
     * Update basic information (first name, last name, email, language) for the current user.
     *
     * @param firstName first name of user
     * @param lastName last name of user
     * @param email email id of user
     * @param langKey language key
     * @param imageUrl image URL of user
     */
    public void updateUser(String firstName, String lastName, String email, String langKey, String imageUrl) {
        SecurityUtils.getCurrentUserLogin()
            .flatMap(userRepository::findOneByLogin)
            .ifPresent(user -> {
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setEmail(email);
                user.setLangKey(langKey);
                user.setImageUrl(imageUrl);
                this.clearUserCaches(user);
                log.debug("Changed Information for User: {}", user);
            });
    }

    /**
     * Update all information for a specific user, and return the modified user.
     *
     * @param userDTO user to update
     * @return updated user
     */
    public Optional<UserDTO> updateUser(UserDTO userDTO) {
        return Optional.of(userRepository
            .findById(userDTO.getId()))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .map(user -> {
                this.clearUserCaches(user);
                user.setLogin(userDTO.getLogin());
                user.setFirstName(userDTO.getFirstName());
                user.setLastName(userDTO.getLastName());
                user.setEmail(userDTO.getEmail());
                user.setImageUrl(userDTO.getImageUrl());
                user.setActivated(userDTO.isActivated());
                user.setLangKey(userDTO.getLangKey());
                Set<Authority> managedAuthorities = user.getAuthorities();
                managedAuthorities.clear();
                userDTO.getAuthorities().stream()
                    .map(authorityRepository::findById)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .forEach(managedAuthorities::add);
                this.clearUserCaches(user);
                log.debug("Changed Information for User: {}", user);
                return user;
            })
            .map(UserDTO::new);
    }

    public void deleteUser(String login) {
        userRepository.findOneByLogin(login).ifPresent(user -> {
            for (JdlMetadata jdlMetadata : jdlMetadataService.findAllForUser(user)) {
                jdlService.deleteAllForJdlMetadata(jdlMetadata.getId());
            }
            jdlMetadataService.deleteAllForUser(user);
            if (githubService.isEnabled()) {
                githubService.deleteAllOrganizationsUser(user);
            }
            if (gitlabService.isEnabled()) {
                gitlabService.deleteAllOrganizationsUser(user);
            }
            userRepository.delete(user);
            this.clearUserCaches(user);
            log.debug("Deleted User: {}", user);
        });
    }

    public void saveToken(String code, GitProvider gitProvider) throws Exception {
        User user = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().orElse(null)).orElseThrow(() ->
            new Exception("No authenticated user can be found."));

        if (gitProvider.equals(GitProvider.GITHUB)) {
            user.setGithubOAuthToken(code);
            user = this.githubService.getSyncedUserFromGitProvider(user);
        } else if (gitProvider.equals(GitProvider.GITLAB)) {
            user.setGitlabOAuthToken(code);
            user = this.gitlabService.getSyncedUserFromGitProvider(user);
        }

        userRepository.save(user);
        log.debug("Updated GitHub OAuth token for User: {}", user);
    }

    public void changePassword(String currentClearTextPassword, String newPassword) {
        SecurityUtils.getCurrentUserLogin()
            .flatMap(userRepository::findOneByLogin)
            .ifPresent(user -> {
                String currentEncryptedPassword = user.getPassword();
                if (!passwordEncoder.matches(currentClearTextPassword, currentEncryptedPassword)) {
                    throw new InvalidPasswordException();
                }
                String encryptedPassword = passwordEncoder.encode(newPassword);
                user.setPassword(encryptedPassword);
                this.clearUserCaches(user);
                log.debug("Changed password for User: {}", user);
            });
    }

    @Transactional(readOnly = true)
    public Page<UserDTO> getAllManagedUsers(Pageable pageable) {
        return userRepository.findAllByLoginNot(pageable, Constants.ANONYMOUS_USER).map(UserDTO::new);
    }

    @Transactional(readOnly = true)
    public User getUser() {
        return userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().orElse(null)).orElse(null);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthoritiesByLogin(String login) {
        return userRepository.findOneWithAuthoritiesByLogin(login);
    }

    @Transactional(readOnly = true)
    public Collection<GitCompany> getOrganizations(GitProvider gitProvider) {
        User user = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().orElse(null)).orElse(null);
        Set<GitCompany> gitCompanies = gitCompanyRepository.findAllByUserAndGitProvider(user, gitProvider.getValue());

        Hibernate.initialize(gitCompanies);
        return gitCompanies;
    }

    @Transactional(readOnly = true)
    public Collection<GitCompany> getGroups() throws Exception {
        Collection<GitCompany> groups = userRepository
            .findOneByLogin(SecurityUtils.getCurrentUserLogin().orElse(null))
            .orElseThrow(() -> new Exception("No authenticated user can be found."))
            .getGitCompanies();
        Hibernate.initialize(groups);
        return groups;
    }

    @Transactional(readOnly = true)
    public List getProjects(String organizationName, GitProvider gitProvider) {
        Collection<GitCompany> organizations = this.getOrganizations(gitProvider);
        if (organizations.size() == 0) {
            return EMPTY_LIST;
        }
        Optional<GitCompany> organization = organizations.stream().filter(test -> test.getName().equals
            (organizationName)).findFirst();
        if (!organization.isPresent()) {
            return EMPTY_LIST;
        } else {
            List<String> projects = organization.get().getGitProjects();
            Hibernate.initialize(projects);
            return projects;
        }
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthorities(Long id) {
        return userRepository.findOneWithAuthoritiesById(id);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthorities() {
        return SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneWithAuthoritiesByLogin);
    }

    /**
     * Not activated users should be automatically deleted after 3 days.
     * <p>
     * This is scheduled to get fired everyday, at 01:00 (am).
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void removeNotActivatedUsers() {
        List<User> users = userRepository.findAllByActivatedIsFalseAndCreatedDateBefore(Instant.now().minus(3,
            ChronoUnit.DAYS));
        for (User user : users) {
            log.debug("Deleting not activated user {}", user.getLogin());
            userRepository.delete(user);
            this.clearUserCaches(user);
        }
    }

    /**
     * @return a list of all the authorities
     */
    public List<String> getAuthorities() {
        return authorityRepository.findAll().stream().map(Authority::getName).collect(Collectors.toList());
    }

    @Cacheable(cacheNames = CacheConfiguration.STATISTICS_USERS_COUNT)
    public long countAll() {
        return userRepository.count();
    }

    private void clearUserCaches(User user) {
        Objects.requireNonNull(cacheManager.getCache(UserRepository.USERS_BY_LOGIN_CACHE)).evict(user.getLogin());
        Objects.requireNonNull(cacheManager.getCache(UserRepository.USERS_BY_EMAIL_CACHE)).evict(user.getEmail());
    }
}
