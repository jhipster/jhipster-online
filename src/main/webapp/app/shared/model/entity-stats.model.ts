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
import { Moment } from 'moment';
import { IGeneratorIdentity } from 'app/shared/model//generator-identity.model';

export interface IEntityStats {
  id?: number;
  year?: number;
  month?: number;
  week?: number;
  day?: number;
  hour?: number;
  fields?: number;
  relationships?: number;
  pagination?: string;
  dto?: string;
  service?: string;
  fluentMethods?: boolean;
  date?: Moment;
  owner?: IGeneratorIdentity;
}

export class EntityStats implements IEntityStats {
  constructor(
    public id?: number,
    public year?: number,
    public month?: number,
    public week?: number,
    public day?: number,
    public hour?: number,
    public fields?: number,
    public relationships?: number,
    public pagination?: string,
    public dto?: string,
    public service?: string,
    public fluentMethods?: boolean,
    public date?: Moment,
    public owner?: IGeneratorIdentity
  ) {
    this.fluentMethods = false;
  }
}
