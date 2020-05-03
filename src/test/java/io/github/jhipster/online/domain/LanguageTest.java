package io.github.jhipster.online.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import io.github.jhipster.online.web.rest.TestUtil;

public class LanguageTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Language.class);
        Language language1 = new Language();
        language1.setId(1L);
        Language language2 = new Language();
        language2.setId(language1.getId());
        assertThat(language1).isEqualTo(language2);
        language2.setId(2L);
        assertThat(language1).isNotEqualTo(language2);
        language1.setId(null);
        assertThat(language1).isNotEqualTo(language2);
    }
}
