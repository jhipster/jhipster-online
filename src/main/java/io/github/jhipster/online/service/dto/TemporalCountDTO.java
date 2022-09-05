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
import java.util.Objects;

public class TemporalCountDTO implements Comparable<TemporalCountDTO> {

    private Instant date;

    private Long count;

    public TemporalCountDTO(Instant date, Long count) {
        this.date = date;
        this.count = count;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TemporalCountDTO that = (TemporalCountDTO) o;
        return Objects.equals(date, that.date) && Objects.equals(count, that.count);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, count);
    }

    @Override
    public String toString() {
        return "TemporalCountDTO{" + "date=" + date + ", count=" + count + '}';
    }

    @Override
    public int compareTo(TemporalCountDTO other) {
        return date.compareTo(other.date);
    }
}
