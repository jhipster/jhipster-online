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
    private resourceUrl = SERVER_API_URL + 'api/generator-identities';

    constructor(private http: HttpClient) {}

    create(generatorIdentity: IGeneratorIdentity): Observable<EntityResponseType> {
        return this.http.post<IGeneratorIdentity>(this.resourceUrl, generatorIdentity, { observe: 'response' });
    }

    update(generatorIdentity: IGeneratorIdentity): Observable<EntityResponseType> {
        return this.http.put<IGeneratorIdentity>(this.resourceUrl, generatorIdentity, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IGeneratorIdentity>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IGeneratorIdentity[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
