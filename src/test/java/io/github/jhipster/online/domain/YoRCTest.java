package io.github.jhipster.online.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import io.github.jhipster.online.web.rest.TestUtil;

public class YoRCTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(YoRC.class);
        YoRC yoRC1 = new YoRC();
        yoRC1.setId(1L);
        YoRC yoRC2 = new YoRC();
        yoRC2.setId(yoRC1.getId());
        assertThat(yoRC1).isEqualTo(yoRC2);
        yoRC2.setId(2L);
        assertThat(yoRC1).isNotEqualTo(yoRC2);
        yoRC1.setId(null);
        assertThat(yoRC1).isNotEqualTo(yoRC2);
    }
}
