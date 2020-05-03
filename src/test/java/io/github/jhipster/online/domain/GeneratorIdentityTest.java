package io.github.jhipster.online.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import io.github.jhipster.online.web.rest.TestUtil;

public class GeneratorIdentityTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GeneratorIdentity.class);
        GeneratorIdentity generatorIdentity1 = new GeneratorIdentity();
        generatorIdentity1.setId(1L);
        GeneratorIdentity generatorIdentity2 = new GeneratorIdentity();
        generatorIdentity2.setId(generatorIdentity1.getId());
        assertThat(generatorIdentity1).isEqualTo(generatorIdentity2);
        generatorIdentity2.setId(2L);
        assertThat(generatorIdentity1).isNotEqualTo(generatorIdentity2);
        generatorIdentity1.setId(null);
        assertThat(generatorIdentity1).isNotEqualTo(generatorIdentity2);
    }
}
