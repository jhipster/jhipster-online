package io.github.jhipster.online.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import io.github.jhipster.online.web.rest.TestUtil;

public class EntityStatsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EntityStats.class);
        EntityStats entityStats1 = new EntityStats();
        entityStats1.setId(1L);
        EntityStats entityStats2 = new EntityStats();
        entityStats2.setId(entityStats1.getId());
        assertThat(entityStats1).isEqualTo(entityStats2);
        entityStats2.setId(2L);
        assertThat(entityStats1).isNotEqualTo(entityStats2);
        entityStats1.setId(null);
        assertThat(entityStats1).isNotEqualTo(entityStats2);
    }
}
