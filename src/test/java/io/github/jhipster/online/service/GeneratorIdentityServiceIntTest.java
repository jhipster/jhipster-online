package io.github.jhipster.online.service;

import io.github.jhipster.online.JhonlineApp;
import io.github.jhipster.online.domain.GeneratorIdentity;
import io.github.jhipster.online.repository.GeneratorIdentityRepository;
import io.github.jhipster.online.service.util.DataGenerationUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
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
}
