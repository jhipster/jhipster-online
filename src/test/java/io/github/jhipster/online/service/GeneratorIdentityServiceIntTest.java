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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import io.github.jhipster.online.JhonlineApp;
import io.github.jhipster.online.domain.GeneratorIdentity;
import io.github.jhipster.online.domain.User;
import io.github.jhipster.online.repository.GeneratorIdentityRepository;
import io.github.jhipster.online.repository.UserRepository;
import io.github.jhipster.online.service.util.DataGenerationUtil;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhonlineApp.class)
public class GeneratorIdentityServiceIntTest {

    @Autowired
    private GeneratorIdentityRepository generatorIdentityRepository;

    @Autowired
    private GeneratorIdentityService generatorIdentityService;

    @Autowired
    private UserRepository userRepository;

    private List<GeneratorIdentity> gids;

    private User user;

    @Before
    public void init() {
        generatorIdentityRepository.deleteAll();
        gids = DataGenerationUtil.addGeneratorIdentitiesToDatabase(100, generatorIdentityRepository);

        user = userRepository.findAll().get((int) (Math.random() * userRepository.count()));
    }

    @Test
    public void assertThatEveryGIIsAddedToDatabase() {
        assertThat(gids.size()).isEqualTo(100);
    }

    @Test
    public void assertThatAGIIsFindableById() {
        GeneratorIdentity giToTest = gids.get((int) (Math.random() * gids.size()));

        assertThat(generatorIdentityRepository.findFirstByGuidEquals(giToTest.getGuid()).orElse(null)).isEqualTo(giToTest);
    }

    @Test
    public void assertThatTryToCreateGeneratorWillCreateOne() {
        generatorIdentityService.tryToCreateGeneratorIdentity(UUID.randomUUID().toString());

        assertThat(generatorIdentityRepository.findAll().size()).isEqualTo(gids.size() + 1);
    }

    @Test
    public void assertThatFindOrCreateOneByGuidWillNotCreateOne() {
        GeneratorIdentity giToTest = gids.get((int) (Math.random() * gids.size()));

        generatorIdentityService.tryToCreateGeneratorIdentity(giToTest.getGuid());
        generatorIdentityService.tryToCreateGeneratorIdentity(giToTest.getGuid());

        assertThat(generatorIdentityRepository.findAll().size()).isEqualTo(gids.size());
    }

    @Test
    public void assertThatAUserCanBeBoundToAGeneratorIdentity() {
        GeneratorIdentity toTest = gids.get((int) (Math.random() * gids.size()));
        generatorIdentityRepository.save(toTest.owner(null));

        generatorIdentityService.bindUserToGenerator(user, toTest.getGuid());

        assertThat(generatorIdentityRepository.findById(toTest.getId()).get().getOwner()).isEqualTo(user);
    }

    @Test
    public void assertThatAUserCanBeUnboundFromAGeneratorIdentity() {
        GeneratorIdentity toTest = gids.get((int) (Math.random() * gids.size()));
        generatorIdentityRepository.save(toTest.owner(user));

        generatorIdentityService.unbindUserFromGenerator(user, toTest.getGuid());

        assertThat(generatorIdentityRepository.findById(toTest.getId()).get().getOwner()).isEqualTo(null);
    }
}
