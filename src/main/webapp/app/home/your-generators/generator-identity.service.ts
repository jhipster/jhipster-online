/**
 * Copyright 2017-2020 the original author or authors from the JHipster Online project.
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
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IGeneratorIdentity } from 'app/shared/model/generator-identity.model';

type EntityResponseType = HttpResponse<IGeneratorIdentity>;
type EntityArrayResponseType = HttpResponse<IGeneratorIdentity[]>;

@Injectable({ providedIn: 'root' })
export class GeneratorIdentityService {
    constructor(private http: HttpClient) {}

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IGeneratorIdentity[]>(SERVER_API_URL + 'api/generator-identities/owned', {
            params: options,
            observe: 'response'
        });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IGeneratorIdentity>(`${SERVER_API_URL}api/generator-identities/${id}`, { observe: 'response' });
    }

    unbind(generatorId: string): Observable<HttpResponse<any>> {
        return this.http.delete(`${SERVER_API_URL}api/s/link/${generatorId}`, { observe: 'response' });
    }

    deleteStatistics(): Observable<any> {
        return this.http.delete(`${SERVER_API_URL}api/s/data`);
    }
}
