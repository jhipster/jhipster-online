package io.github.jhipster.online.util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class SanitizeInputsTest {

    @Test
    void sanitizeInput() {
        assertThat(SanitizeInputs.sanitizeInput("This is JHipster")).isEqualTo("This is JHipster");
        assertThat(SanitizeInputs.sanitizeInput("This is \nJHipster")).isEqualTo("This is _JHipster");
        assertThat(SanitizeInputs.sanitizeInput("This is \tJHipster")).isEqualTo("This is _JHipster");
        assertThat(SanitizeInputs.sanitizeInput("This is \rJHipster")).isEqualTo("This is _JHipster");
        assertThat(SanitizeInputs.sanitizeInput("T\this \nis \rJHipster")).isEqualTo("T_his _is _JHipster");
        assertThat(SanitizeInputs.sanitizeInput("T\t\rhis \n\tis \r\nJHipster")).isEqualTo("T__his __is __JHipster");
    }
}
