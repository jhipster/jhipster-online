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

package io.github.jhipster.online.service.dto;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class TemporalDistributionDTO {

    private Instant date;

    private Map<String, Long> values;

    public TemporalDistributionDTO(Instant date, Map<String, Long> values) {
        this.date = date;
        this.values = values;
    }

    public TemporalDistributionDTO(Instant date) {
        this.date = date;
        this.values = new HashMap<>();
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Map<String, Long> getValues() {
        return values;
    }

    public void setValues(Map<String, Long> values) {
        this.values = values;
    }
}
