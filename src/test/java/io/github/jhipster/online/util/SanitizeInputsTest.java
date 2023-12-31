/**
 * Copyright 2017-2024 the original author or authors from the JHipster project.
 *
 * This file is part of the JHipster Online project, see https://github.com/jhipster/jhipster-online
 * for more information.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.jhipster.online.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class SanitizeInputsTest {

    @Test
    void sanitizeInput() {
        assertThat(SanitizeInputs.sanitizeInput(null)).isNull();
        assertThat(SanitizeInputs.sanitizeInput("This is JHipster")).isEqualTo("This is JHipster");
        assertThat(SanitizeInputs.sanitizeInput("This is \nJHipster")).isEqualTo("This is _JHipster");
        assertThat(SanitizeInputs.sanitizeInput("This is \tJHipster")).isEqualTo("This is _JHipster");
        assertThat(SanitizeInputs.sanitizeInput("This is \rJHipster")).isEqualTo("This is _JHipster");
        assertThat(SanitizeInputs.sanitizeInput("T\this \nis \rJHipster")).isEqualTo("T_his _is _JHipster");
        assertThat(SanitizeInputs.sanitizeInput("T\t\rhis \n\tis \r\nJHipster")).isEqualTo("T__his __is __JHipster");
    }

    @Test
    void isAlphaNumeric() {
        assertThat(SanitizeInputs.isAlphaNumeric(null)).isFalse();
        assertThat(SanitizeInputs.isAlphaNumeric("This is JHipster")).isFalse();
        assertThat(SanitizeInputs.isAlphaNumeric("442kj32342kd")).isTrue();
        assertThat(SanitizeInputs.isAlphaNumeric("A3sdflkjE422")).isTrue();
        assertThat(SanitizeInputs.isAlphaNumeric("A$sdlfjk%332")).isFalse();
        assertThat(SanitizeInputs.isAlphaNumeric("Asdkl/djf323")).isFalse();
    }

    @Test
    void isLettersNumbersAndSpaces() {
        assertThat(SanitizeInputs.isLettersNumbersAndSpaces(null)).isFalse();
        assertThat(SanitizeInputs.isLettersNumbersAndSpaces("This is JHipster")).isTrue();
        assertThat(SanitizeInputs.isLettersNumbersAndSpaces("442kj32342kd")).isTrue();
        assertThat(SanitizeInputs.isLettersNumbersAndSpaces("23094823023")).isTrue();
        assertThat(SanitizeInputs.isLettersNumbersAndSpaces("2309/482$3023")).isFalse();
        assertThat(SanitizeInputs.isLettersNumbersAndSpaces("2309/\n482\r$3\t023")).isFalse();
        assertThat(SanitizeInputs.isLettersNumbersAndSpaces("23};09/\n482\r$3\t023**")).isFalse();
    }
}
