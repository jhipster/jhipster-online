package io.github.jhipster.online.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import io.github.jhipster.online.web.rest.TestUtil;

public class UserApplicationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserApplication.class);
        UserApplication userApplication1 = new UserApplication();
        userApplication1.setId(1L);
        UserApplication userApplication2 = new UserApplication();
        userApplication2.setId(userApplication1.getId());
        assertThat(userApplication1).isEqualTo(userApplication2);
        userApplication2.setId(2L);
        assertThat(userApplication1).isNotEqualTo(userApplication2);
        userApplication1.setId(null);
        assertThat(userApplication1).isNotEqualTo(userApplication2);
    }
}
