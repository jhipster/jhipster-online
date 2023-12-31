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
package io.github.jhipster.online.service.enums;

import static java.text.MessageFormat.format;

import java.util.Optional;
import java.util.stream.Stream;
import org.apache.commons.lang3.StringUtils;

public enum CiCdTool {
    TRAVIS,
    JENKINS,
    GITLAB,
    AZURE,
    GITHUB,
    CIRCLE;

    public static Optional<CiCdTool> getByName(String name) {
        return Stream.of(values()).filter(ciCdTool -> ciCdTool.name().equalsIgnoreCase(name)).findFirst();
    }

    public String command() {
        return name().toLowerCase();
    }

    public String branchName(String complement) {
        return format("{0}-{1}-{2}", "jhipster", name().toLowerCase(), complement);
    }

    public String getCiCdToolName() {
        if (name().equals("GITLAB")) {
            return "GitLab";
        } else if (name().equals("GITHUB")) {
            return "GitHub";
        } else {
            return StringUtils.capitalize(name().toLowerCase());
        }
    }
}
