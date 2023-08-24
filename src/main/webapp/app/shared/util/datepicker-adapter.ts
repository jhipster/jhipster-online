/**
 * Copyright 2017-2023 the original author or authors from the JHipster project.
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
/**
 * Angular bootstrap Date adapter
 */
import { Injectable } from '@angular/core';
import { NgbDateAdapter, NgbDateStruct } from '@ng-bootstrap/ng-bootstrap';
import { Dayjs } from 'dayjs';
import dayjs from 'dayjs/esm';

@Injectable()
export class NgbDateDayjsAdapter extends NgbDateAdapter<Dayjs> {
  fromModel(date: Dayjs): NgbDateStruct {
    if (date && dayjs.isDayjs(date) && date.isValid()) {
      return { year: date.year(), month: date.month() + 1, day: date.date() };
    }
    // ! can be removed after https://github.com/ng-bootstrap/ng-bootstrap/issues/1544 is resolved
    return null!;
  }

  toModel(date: NgbDateStruct): Dayjs {
    // ! after null can be removed after https://github.com/ng-bootstrap/ng-bootstrap/issues/1544 is resolved
    return date ? dayjs(date.year + '-' + date.month + '-' + date.day, 'YYYY-MM-DD') : null!;
  }
}
