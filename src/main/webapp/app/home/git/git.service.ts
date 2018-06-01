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
import { Observable } from 'rxjs';

import { GitCompanyModel } from 'app/home/generator/git.company.model';

@Injectable({ providedIn: 'root' })
export class GitProviderService {
    constructor(private http: HttpClient) {}

    clientId(string: string): Observable<string> {
        return this.http.get(`api/${string.valueOf()}/client-id`, { responseType: 'text' });
    }

    saveGitOAuthToken(provider: string, token: string): Observable<any> {
        return this.http.post<any>(`api/${provider}/save-token`, token);
    }

    refreshGitProvider(provider: string): Observable<any> {
        return this.http.post<any>(`api/${provider}/refresh`, '');
    }

    getCompanies(provider: string): Observable<GitCompanyModel[]> {
        return this.http.get<GitCompanyModel[]>(`api/${provider}/companies`).map((res: GitCompanyModel[]) => res);
    }

    getProjects(provider: string, companyName: string): Observable<string[]> {
        return this.http.get<string[]>(`api/${provider}/companies/${companyName}/projects`).map((res: string[]) => res);
    }

    getAvailableProviders(): Observable<string[]> {
        return this.http.get<string[]>('api/git/providers').map((res: string[]) => res);
    }

    getGitlabConfig(): Observable<any> {
        return this.http.get<any>('api/gitlab/config');
    }
}
