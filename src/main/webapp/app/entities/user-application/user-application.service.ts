import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IUserApplication } from 'app/shared/model/user-application.model';

type EntityResponseType = HttpResponse<IUserApplication>;
type EntityArrayResponseType = HttpResponse<IUserApplication[]>;

@Injectable({ providedIn: 'root' })
export class UserApplicationService {
    private resourceUrl = SERVER_API_URL + 'api/user-applications';

    constructor(private http: HttpClient) {}

    create(userApplication: IUserApplication): Observable<EntityResponseType> {
        return this.http.post<IUserApplication>(this.resourceUrl, userApplication, { observe: 'response' });
    }

    update(userApplication: IUserApplication): Observable<EntityResponseType> {
        return this.http.put<IUserApplication>(this.resourceUrl, userApplication, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IUserApplication>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IUserApplication[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
