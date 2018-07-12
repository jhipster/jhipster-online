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

import java.time.Instant;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.online.domain.*;
import io.github.jhipster.online.repository.*;
import io.github.jhipster.online.security.SecurityUtils;

/**
 * Service Implementation for managing JdlMetadata.
 */
@Service
@Transactional
public class JdlMetadataService {

    private final Logger log = LoggerFactory.getLogger(JdlMetadataService.class);

    private final JdlMetadataRepository jdlMetadataRepository;

    private final JdlRepository jdlRepository;

    private final UserRepository userRepository;

    public JdlMetadataService(JdlMetadataRepository jdlMetadataRepository, JdlRepository jdlRepository,
        UserRepository userRepository) {

        this.jdlMetadataRepository = jdlMetadataRepository;
        this.jdlRepository = jdlRepository;
        this.userRepository = userRepository;
    }

    /**
     * Create a jdlMetadata and the associated JDL.
     *
     * @param jdlMetadata the entity to saveJdlMetadata
     * @return the persisted entity
     */
    public JdlMetadata create(JdlMetadata jdlMetadata, String content) {
        log.debug("Request to create JdlMetadata and JDL : {}", jdlMetadata);
        User currentUser = this.getUser();
        jdlMetadata.setUser(currentUser);
        jdlMetadata.setCreatedDate(Instant.now());
        jdlMetadata.setUpdatedDate(Instant.now());
        jdlMetadata.setIsPublic(false);
        String generatedRandomKey = UUID.randomUUID().toString();
        jdlMetadata.setId(generatedRandomKey);
        jdlMetadataRepository.save(jdlMetadata);
        Jdl jdl = new Jdl();
        jdl.setContent(content);
        jdl.setJdlMetadata(jdlMetadata);
        jdlRepository.save(jdl);
        return jdlMetadata;
    }

    /**
     * Save a jdlMetadata.
     *
     * @param jdlMetadata the entity to saveJdlMetadata
     * @return the persisted entity
     */
    public JdlMetadata saveJdlMetadata(JdlMetadata jdlMetadata) {
        log.debug("Request to save JdlMetadata : {}", jdlMetadata);
        User user = this.getUser();
        jdlMetadata.setUser(user);
        return jdlMetadataRepository.save(jdlMetadata);
    }

    public void updateJdlContent(JdlMetadata jdlMetadata, String content) throws Exception {
        log.debug("Request to update JDL : {}", jdlMetadata);
        jdlMetadata.setUpdatedDate(Instant.now());
        jdlMetadataRepository.save(jdlMetadata);
        Optional<Jdl> jdl = jdlRepository.findOneByJdlMetadataId(jdlMetadata.getId());
        if (!jdl.isPresent()) {
            log.error("Error creating updating the JDL, the JDL could not be found: {}", jdlMetadata);
            throw new Exception("JDL could not be found");
        }
        jdl.get().setContent(content);
    }

    /**
     *  Get all the jdlMetadata.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<JdlMetadata> findAllForUser(User user) {
        log.debug("Request to get all JdlMetadata for user {}", user.getLogin());
        return jdlMetadataRepository.findAllByUserLogin(user.getLogin());
    }

    /**
     *  Get one jdlMetadata by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<JdlMetadata> findOne(String id) {
        log.debug("Request to get JdlMetadata : {}", id);
        Optional<JdlMetadata> jdlMetadata = jdlMetadataRepository.findById(id);
        if (jdlMetadata.isPresent() && jdlMetadata.get().isIsPublic() != null && jdlMetadata.get().isIsPublic()) {
            return jdlMetadata;
        } else if (jdlMetadata.isPresent() && jdlMetadata.get().getUser().equals(this.getUser())) {
            return jdlMetadata;
        } else {
            throw new AccessDeniedException("Current user does not have access to this JDL file");
        }
    }

    /**
     *  Delete the  jdlMetadata by id.
     *
     *  @param id the id of the entity
     */
    public void delete(String id) throws Exception {
        log.debug("Request to delete JdlMetadata : {}", id);
        this.findOne(id); // Checks if the user has access to this JDL
        Optional<Jdl> jdl = jdlRepository.findOneByJdlMetadataId(id);
        if (!jdl.isPresent()) {
            log.error("Error creating updating the JDL, the JDL could not be found: {}", id);
            throw new Exception("JDL could not be found");
        }
        this.jdlRepository.delete(jdl.get());
        jdlMetadataRepository.deleteById(id);
    }

    /**
     *  Delete all the jdlMetadata.
     *
     */
    @Transactional
    public void deleteAllForUser(User user) {
        log.debug("Request to delete all JdlMetadata for user {}", user.getLogin());
        jdlMetadataRepository.deleteAllByUserLogin(user.getLogin());
    }

    private User getUser() {
        return userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().orElse(null)).orElse(null);
    }
}
