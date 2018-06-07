import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITestFramework } from 'app/shared/model/test-framework.model';

type EntityResponseType = HttpResponse<ITestFramework>;
type EntityArrayResponseType = HttpResponse<ITestFramework[]>;

@Injectable()
export class TestFrameworkService {
    private resourceUrl = SERVER_API_URL + 'api/test-frameworks';

    constructor(private http: HttpClient) {}

    create(testFramework: ITestFramework): Observable<EntityResponseType> {
        return this.http.post<ITestFramework>(this.resourceUrl, testFramework, { observe: 'response' });
    }

    update(testFramework: ITestFramework): Observable<EntityResponseType> {
        return this.http.put<ITestFramework>(this.resourceUrl, testFramework, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ITestFramework>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ITestFramework[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
