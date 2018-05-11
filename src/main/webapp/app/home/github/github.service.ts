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
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/Rx';
import { GithubOrganizationModel } from '../generator/github.organization.model';

@Injectable()
export class GithubService {
    constructor(private http: HttpClient) {}

    clientId(): Observable<string> {
        return this.http.get<string>('api/github/client-id').map((res: string) => res);
    }

    saveGithubOAuthToken(token: string): Observable<any> {
        return this.http.post<any>('api/github/save-token', token);
    }

    refreshGithub(): Observable<any> {
        return this.http.post<any>('api/github/refresh', '');
    }

    getOrganizations(): Observable<GithubOrganizationModel[]> {
        return this.http.get<GithubOrganizationModel[]>('api/github/organizations').map((res: GithubOrganizationModel[]) => res);
    }

    getProjects(organizationName: String): Observable<String[]> {
        return this.http.get<string[]>('api/github/organizations/' + organizationName + '/projects').map((res: string[]) => res);
    }
}
