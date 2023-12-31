/**
 * Copyright 2017-2024 the original author or authors from the JHipster project.
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import io.github.jhipster.online.JhonlineApp;
import io.github.jhipster.online.domain.Jdl;
import io.github.jhipster.online.domain.JdlMetadata;
import io.github.jhipster.online.domain.User;
import io.github.jhipster.online.repository.JdlMetadataRepository;
import io.github.jhipster.online.repository.JdlRepository;
import io.github.jhipster.online.repository.UserRepository;
import io.github.jhipster.online.service.dto.JdlMetadataDTO;
import java.nio.file.FileSystemException;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = JhonlineApp.class)
class JdlMetadataServiceTest {

    @MockBean
    private JdlMetadataRepository jdlMetadataRepository;

    @MockBean
    private JdlRepository jdlRepository;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private JdlMetadataService jdlMetadataService;

    @Captor
    private ArgumentCaptor<Jdl> jdlArgumentCaptor;

    @Captor
    private ArgumentCaptor<JdlMetadata> jdlMetadataArgumentCaptor;

    @BeforeEach
    public void setup() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }

    @Test
    void shouldCreate() {
        final User user = new User();
        given(userRepository.findOneByLogin(nullable(String.class))).willReturn(Optional.of(user));

        JdlMetadata result = jdlMetadataService.create(new JdlMetadata(), "jdl-content");

        assertThat(result.getUser()).isInstanceOf(User.class);
        assertThat(result.getCreatedDate()).isBetween(Instant.now().minusSeconds(3), Instant.now().plusSeconds(1));
        assertThat(result.getUpdatedDate()).isBetween(Instant.now().minusSeconds(3), Instant.now().plusSeconds(1));
        assertThat(result.isIsPublic()).isFalse();
        assertThat(result.getId()).hasSize(UUID.randomUUID().toString().length());
        verify(jdlMetadataRepository).save(any(JdlMetadata.class));
        verify(jdlRepository).save(jdlArgumentCaptor.capture());
        final Jdl persistedJdl = jdlArgumentCaptor.getValue();
        assertThat(persistedJdl.getContent()).isEqualTo("jdl-content");
        assertThat(persistedJdl.getJdlMetadata()).isNotNull();
    }

    @Test
    void shouldSaveJdlMetadata() {
        final JdlMetadataDTO jdlMetadataDTO = new JdlMetadataDTO();
        jdlMetadataDTO.setName("test-name");
        final User user = new User();
        given(userRepository.findOneByLogin(nullable(String.class))).willReturn(Optional.of(user));
        final JdlMetadata jdlMetadata = new JdlMetadata();
        jdlMetadata.setName(jdlMetadataDTO.getName());
        given(jdlMetadataRepository.save(jdlMetadataArgumentCaptor.capture())).willReturn(jdlMetadata);

        JdlMetadataDTO result = jdlMetadataService.saveJdlMetadata(jdlMetadataDTO);

        final JdlMetadata persistedJdlMetadata = jdlMetadataArgumentCaptor.getValue();
        assertThat(persistedJdlMetadata.getUser()).isNotNull();
        assertThat(result.getName()).isEqualTo("test-name");
    }

    @Test
    void shouldUpdateJdlMetadata() throws FileSystemException {
        final JdlMetadata jdlMetadata = new JdlMetadata();
        jdlMetadata.setId("jdl-metadata-id");
        Jdl jdl = new Jdl();
        String content = "jdl-content";
        given(jdlMetadataRepository.save(jdlMetadataArgumentCaptor.capture())).willReturn(jdlMetadata);
        given(jdlRepository.findOneByJdlMetadataId(jdlMetadata.getId())).willReturn(Optional.of(jdl));

        jdlMetadataService.updateJdlContent(jdlMetadata, content);

        assertThat(jdlMetadataArgumentCaptor.getValue().getUpdatedDate())
            .isBetween(Instant.now().minusSeconds(3), Instant.now().plusSeconds(1));
        assertThat(jdl.getContent()).isEqualTo(content);
    }

    @Test
    void shouldUpdateJdlMetadataNotFound() {
        final JdlMetadata jdlMetadata = new JdlMetadata();

        Throwable thrown = catchThrowable(() -> jdlMetadataService.updateJdlContent(jdlMetadata, null));

        verify(jdlMetadataRepository).save(jdlMetadata);
        assertThat(thrown)
            .isInstanceOf(FileSystemException.class)
            .hasMessageStartingWith("Error creating updating the JDL, the JDL could not be found: ");
    }

    @Test
    void shouldFindAllForUser() {
        final User user = new User();
        user.setLogin("user");
        final JdlMetadata jdlMetadata = new JdlMetadata();
        given(jdlMetadataRepository.findAllByUserLogin(user.getLogin())).willReturn(Arrays.asList(jdlMetadata));

        final List<JdlMetadata> result = jdlMetadataService.findAllForUser(user);

        assertThat(result).containsOnly(jdlMetadata);
    }

    @Test
    void shouldFindAllForUserWithSort() {
        final User user = new User();
        user.setLogin("user");
        Sort sort = Sort.by(Sort.Direction.ASC, "firstName");
        final JdlMetadata jdlMetadata = new JdlMetadata();
        given(jdlMetadataRepository.findAllByUserLogin(user.getLogin(), sort)).willReturn(Arrays.asList(jdlMetadata));

        final List<JdlMetadata> result = jdlMetadataService.findAllForUser(user, sort);

        assertThat(result).containsOnly(jdlMetadata);
    }

    @Test
    void shouldFindOnePublic() {
        String id = UUID.randomUUID().toString();
        final JdlMetadata value = new JdlMetadata();
        value.setIsPublic(true);
        given(jdlMetadataRepository.findById(id)).willReturn(Optional.of(value));

        final Optional<JdlMetadata> result = jdlMetadataService.findOne(id);

        assertThat(result).isPresent();
    }

    @Test
    void shouldFindOneForUser() {
        final String id = UUID.randomUUID().toString();
        final JdlMetadata value = new JdlMetadata();
        final User user = new User();
        value.setUser(user);
        given(jdlMetadataRepository.findById(id)).willReturn(Optional.of(value));
        given(userRepository.findOneByLogin(nullable(String.class))).willReturn(Optional.of(user));
        final Optional<JdlMetadata> result = jdlMetadataService.findOne(id);

        assertThat(result).isPresent();
    }

    @Test
    void shouldFindOneAccessDenied() {
        Throwable thrown = catchThrowable(() -> jdlMetadataService.findOne(null));

        assertThat(thrown).isInstanceOf(AccessDeniedException.class).hasMessage("Current user does not have access to this JDL file");
    }

    @Test
    void shouldDelete() throws FileSystemException {
        String id = UUID.randomUUID().toString();
        final JdlMetadata value = new JdlMetadata();
        value.setIsPublic(true);
        given(jdlMetadataRepository.findById(id)).willReturn(Optional.of(value));
        final Jdl jdl = new Jdl();
        given(jdlRepository.findOneByJdlMetadataId(id)).willReturn(Optional.of(jdl));

        jdlMetadataService.delete(id);

        verify(jdlRepository).delete(jdl);
        verify(jdlMetadataRepository).deleteById(id);
    }

    @Test
    void shouldNotDeleteEmptyJdl() {
        String id = UUID.randomUUID().toString();
        final JdlMetadata value = new JdlMetadata();
        value.setIsPublic(true);
        given(jdlMetadataRepository.findById(id)).willReturn(Optional.of(value));

        Throwable thrown = catchThrowable(() -> jdlMetadataService.delete(id));

        assertThat(thrown)
            .isInstanceOf(FileSystemException.class)
            .hasMessage("Error creating updating the JDL, the JDL could not be found: " + id);
        verify(jdlRepository, never()).delete(any(Jdl.class));
        verify(jdlMetadataRepository, never()).deleteById(id);
    }

    @Test
    void shouldDeleteAllForUser() {
        final User user = new User();
        user.setLogin("test-user");

        jdlMetadataService.deleteAllForUser(user);

        verify(jdlMetadataRepository).deleteAllByUserLogin(user.getLogin());
    }
}
