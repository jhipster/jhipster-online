package io.github.jhipster.online.service;

import io.github.jhipster.online.JhonlineApp;
import io.github.jhipster.online.domain.GeneratorIdentity;
import io.github.jhipster.online.domain.User;
import io.github.jhipster.online.repository.GeneratorIdentityRepository;
import io.github.jhipster.online.repository.UserRepository;
import io.github.jhipster.online.service.util.DataGenerationUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

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

    @Before
    public void init() {
        generatorIdentityRepository.deleteAll();
        gids = DataGenerationUtil.addGeneratorIdentitiesToDatabase(100, generatorIdentityRepository);
    }

    @Test
    public void assertThatEveryGIIsAddedToDatabase() {
        assertThat(gids.size()).isEqualTo(100);
    }

    @Test
    public void assertThatAGIIsFindableById() {
        GeneratorIdentity giToTest = gids.get((int) (Math.random() * gids.size()));
        assertThat(generatorIdentityRepository.findFirstByGuidEquals(giToTest.getGuid()).get()).isEqualTo(giToTest);
    }

    @Test
    public void assertThatFindOrCreateOneByGuidWillCreateOne() {
        generatorIdentityService.findOrCreateOneByGuid(UUID.randomUUID().toString());

        assertThat(generatorIdentityRepository.findAll().size()).isEqualTo(gids.size() + 1);
    }

    @Test
    public void assertThatFindOrCreateOneByGuidWillNotCreateOne() {
        GeneratorIdentity giToTest = gids.get((int) (Math.random() * gids.size()));
        assertThat(generatorIdentityService.findOrCreateOneByGuid(giToTest.getGuid()).getGuid()).isEqualTo(giToTest.getGuid());
        assertThat(generatorIdentityService.findOrCreateOneByGuid(giToTest.getGuid()).getGuid()).isEqualTo(giToTest.getGuid());
        assertThat(generatorIdentityRepository.findAll().size()).isEqualTo(gids.size());
    }

    @Test
    public void assertThatAUserCanBeBoundToAGeneratorIdentity() {
        User user = new User();
        user.setLogin("johndoe");
        user.setPassword(RandomStringUtils.random(60));
        userRepository.save(user);

        GeneratorIdentity toTest = gids.get((int) (Math.random() * gids.size()));
        generatorIdentityRepository.save(toTest.owner(null));

        generatorIdentityService.bindUserToGenerator(user, toTest.getGuid());

        assertThat(generatorIdentityRepository.findById(toTest.getId()).get().getOwner()).isEqualTo(user);
    }

    @Test
    public void assertThatAUserCanBeUnboundFromAGeneratorIdentity() {
        User user = new User();
        user.setLogin("johndoe");
        user.setPassword(RandomStringUtils.random(60));
        userRepository.save(user);

        GeneratorIdentity toTest = gids.get((int) (Math.random() * gids.size()));
        generatorIdentityRepository.save(toTest.owner(user));

        generatorIdentityService.unbindUserFromGenerator(user, toTest.getGuid());

        assertThat(generatorIdentityRepository.findById(toTest.getId()).get().getOwner()).isEqualTo(null);
    }
}
