import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IOwnerIdentity } from 'app/shared/model/owner-identity.model';

type EntityResponseType = HttpResponse<IOwnerIdentity>;
type EntityArrayResponseType = HttpResponse<IOwnerIdentity[]>;

@Injectable()
export class OwnerIdentityService {
    private resourceUrl = SERVER_API_URL + 'api/owner-identities';

    constructor(private http: HttpClient) {}

    create(ownerIdentity: IOwnerIdentity): Observable<EntityResponseType> {
        return this.http.post<IOwnerIdentity>(this.resourceUrl, ownerIdentity, { observe: 'response' });
    }

    update(ownerIdentity: IOwnerIdentity): Observable<EntityResponseType> {
        return this.http.put<IOwnerIdentity>(this.resourceUrl, ownerIdentity, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IOwnerIdentity>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IOwnerIdentity[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
