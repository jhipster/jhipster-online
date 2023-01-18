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
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class StatisticsService {
  constructor(private http: HttpClient) {}

  countYos(): Observable<number> {
    return this.http.get<number>(`/api/s/count-yo`);
  }

  countUsers(): Observable<number> {
    return this.http.get<number>(`/api/s/count-user`);
  }

  countJdls(): Observable<number> {
    return this.http.get<number>(`/api/s/count-jdl`);
  }

  getCount(frequency: string): Observable<any> {
    return this.http.get(`/api/s/count-yo/${frequency}`);
  }

  getFieldCount(field: string, frequency: string): Observable<any> {
    return this.http.get(`/api/s/yo/${field}/${frequency}`);
  }

  getDeploymentToolsCount(frequency: string): Observable<any> {
    return this.http.get(`/api/s/sub-gen-event/deployment/${frequency}`);
  }

  getEntityGenerationCount(frequency: string): Observable<any> {
    return this.http.get(`/api/s/entity/${frequency}`);
  }

  getEntityFieldCount(field: string, frequency: string): Observable<any> {
    return this.http.get(`/api/s/entity/${field}/${frequency}`);
  }
}
