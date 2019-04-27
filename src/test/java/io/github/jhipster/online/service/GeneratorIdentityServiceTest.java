/**
 * Copyright 2017-2019 the original author or authors from the JHipster Online project.
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

import io.github.jhipster.online.JhonlineApp;
import io.github.jhipster.online.domain.GeneratorIdentity;
import io.github.jhipster.online.repository.GeneratorIdentityRepository;
import io.github.jhipster.online.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = JhonlineApp.class)
public class GeneratorIdentityServiceTest {

    @MockBean
    private GeneratorIdentityRepository generatorIdentityRepository;

    @Autowired
    private GeneratorIdentityService generatorIdentityService;

    @BeforeEach
    public void setup() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }

    @Test
    public void shouldFindOneByGuid() {
        GeneratorIdentity expected = new GeneratorIdentity().guid("123");
        when(generatorIdentityRepository.findFirstByGuidEquals("123")).thenReturn(Optional.of(expected));

        GeneratorIdentity result = generatorIdentityService.findOneByGuid("123").orElse(null);

        assertThat(result).extracting(GeneratorIdentity::getGuid).containsExactly("123");
    }
}
