/**
 * Copyright 2017-2022 the original author or authors from the JHipster project.
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

import java.util.regex.Pattern;

public class SanitizeInputs {

    private static final Pattern SANITIZE_REGEX = Pattern.compile("[\n\r\t]");
    private static final Pattern ALPHANUMERIC_REGEX = Pattern.compile("[a-zA-Z0-9]*");
    private static final Pattern ALPHANUMERIC_AND_SPACES_REGEX = Pattern.compile("[\\w\\s]*");

    private SanitizeInputs() {
        throw new IllegalStateException("Utility class: SanitizeInputs");
    }

    public static String sanitizeInput(String inputString) {
        if (inputString == null) return null;
        return SANITIZE_REGEX.matcher(inputString).replaceAll("_");
    }

    public static boolean isAlphaNumeric(String inputString) {
        if (inputString == null) return false;
        return ALPHANUMERIC_REGEX.matcher(inputString).matches();
    }

    public static boolean isLettersNumbersAndSpaces(String inputString) {
        if (inputString == null) return false;
        return ALPHANUMERIC_AND_SPACES_REGEX.matcher(inputString).matches();
    }
}
