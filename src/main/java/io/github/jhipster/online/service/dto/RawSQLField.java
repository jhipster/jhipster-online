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

public class RawSQLField {

    private Integer moment;

    private String field;

    private Long count;

    public RawSQLField(Integer moment, String field, Long count) {
        this.moment = moment;
        this.field = field;
        this.count = count;
    }

    public Integer getMoment() {
        return moment;
    }

    public void setMoment(Integer moment) {
        this.moment = moment;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "RawSQLField{" + "moment=" + moment + ", field='" + field + '\'' + ", count=" + count + '}';
    }
}
