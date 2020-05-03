package io.github.jhipster.online.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import io.github.jhipster.online.web.rest.TestUtil;

public class JdlMetadataTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(JdlMetadata.class);
        JdlMetadata jdlMetadata1 = new JdlMetadata();
        jdlMetadata1.setId(1L);
        JdlMetadata jdlMetadata2 = new JdlMetadata();
        jdlMetadata2.setId(jdlMetadata1.getId());
        assertThat(jdlMetadata1).isEqualTo(jdlMetadata2);
        jdlMetadata2.setId(2L);
        assertThat(jdlMetadata1).isNotEqualTo(jdlMetadata2);
        jdlMetadata1.setId(null);
        assertThat(jdlMetadata1).isNotEqualTo(jdlMetadata2);
    }
}
