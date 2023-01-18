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
export class JdlService {
  constructor(private http: HttpClient) {}

  doApplyJdl(gitProvider: string, organizationName: string, projectName: string, jdlId: string): Observable<string> {
    return this.http.post(`api/apply-jdl/${gitProvider}/${organizationName}/${projectName}/${jdlId}`, '', { responseType: 'text' });
  }

  getApplyJdlLogs(applyJdlId: string): Observable<string> {
    return this.http.get('api/apply-jdl-logs/' + applyJdlId, { responseType: 'text' });
  }
}
