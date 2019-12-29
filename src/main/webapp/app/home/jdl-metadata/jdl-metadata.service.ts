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
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/Rx';

import { createRequestOption } from 'app/shared';
import { JdlMetadata } from './jdl-metadata.model';

@Injectable({ providedIn: 'root' })
export class JdlMetadataService {
    private metadataUrl = 'api/jdl-metadata';

    private jdlUrl = 'api/jdl';

    constructor(private http: HttpClient) {}

    update(jdlMetadata: JdlMetadata): Observable<JdlMetadata> {
        const copy = this.convert(jdlMetadata);
        return this.http.put<JdlMetadata>(this.metadataUrl, copy).map((res: JdlMetadata) => res);
    }

    find(id: number): Observable<JdlMetadata> {
        return this.http.get<JdlMetadata>(`${this.metadataUrl}/${id}`);
    }

    query(req?: any): Observable<JdlMetadata[]> {
        const options = createRequestOption(req);
        return this.http.get<JdlMetadata[]>(this.metadataUrl, { params: options });
    }

    delete(id: string): Observable<any> {
        return this.http.delete<any>(`${this.jdlUrl}/${id}`);
    }

    private convert(jdlMetadata: JdlMetadata): JdlMetadata {
        const copy: JdlMetadata = Object.assign({}, jdlMetadata);
        return copy;
    }
}
