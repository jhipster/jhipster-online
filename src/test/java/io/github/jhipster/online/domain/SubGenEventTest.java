package io.github.jhipster.online.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import io.github.jhipster.online.web.rest.TestUtil;

public class SubGenEventTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SubGenEvent.class);
        SubGenEvent subGenEvent1 = new SubGenEvent();
        subGenEvent1.setId(1L);
        SubGenEvent subGenEvent2 = new SubGenEvent();
        subGenEvent2.setId(subGenEvent1.getId());
        assertThat(subGenEvent1).isEqualTo(subGenEvent2);
        subGenEvent2.setId(2L);
        assertThat(subGenEvent1).isNotEqualTo(subGenEvent2);
        subGenEvent1.setId(null);
        assertThat(subGenEvent1).isNotEqualTo(subGenEvent2);
    }
}
