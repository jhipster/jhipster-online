import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IJdlMetadata } from 'app/shared/model/jdl-metadata.model';

type EntityResponseType = HttpResponse<IJdlMetadata>;
type EntityArrayResponseType = HttpResponse<IJdlMetadata[]>;

@Injectable({ providedIn: 'root' })
export class JdlMetadataService {
    private resourceUrl = SERVER_API_URL + 'api/jdl-metadata';

    constructor(private http: HttpClient) {}

    create(jdlMetadata: IJdlMetadata): Observable<EntityResponseType> {
        return this.http.post<IJdlMetadata>(this.resourceUrl, jdlMetadata, { observe: 'response' });
    }

    update(jdlMetadata: IJdlMetadata): Observable<EntityResponseType> {
        return this.http.put<IJdlMetadata>(this.resourceUrl, jdlMetadata, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IJdlMetadata>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IJdlMetadata[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
