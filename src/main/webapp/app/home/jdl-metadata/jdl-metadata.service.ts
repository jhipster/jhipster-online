/**
 * Copyright 2017-2018 the original author or authors from the JHipster Online project.
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
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { JdlMetadata } from './jdl-metadata.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class JdlMetadataService {

    private metadataUrl = 'api/jdl-metadata';

    private jdlUrl = 'api/jdl';

    constructor(private http: Http) { }

    update(jdlMetadata: JdlMetadata): Observable<JdlMetadata> {
        const copy = this.convert(jdlMetadata);
        return this.http.put(this.metadataUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<JdlMetadata> {
        return this.http.get(`${this.metadataUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.metadataUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: string): Observable<Response> {
        return this.http.delete(`${this.jdlUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convert(jdlMetadata: JdlMetadata): JdlMetadata {
        const copy: JdlMetadata = Object.assign({}, jdlMetadata);
        return copy;
    }
}
