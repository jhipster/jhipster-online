import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IYoRC } from 'app/shared/model/yo-rc.model';

type EntityResponseType = HttpResponse<IYoRC>;
type EntityArrayResponseType = HttpResponse<IYoRC[]>;

@Injectable()
export class YoRCService {
    private resourceUrl = SERVER_API_URL + 'api/yo-rcs';

    constructor(private http: HttpClient) {}

    create(yoRC: IYoRC): Observable<EntityResponseType> {
        return this.http.post<IYoRC>(this.resourceUrl, yoRC, { observe: 'response' });
    }

    update(yoRC: IYoRC): Observable<EntityResponseType> {
        return this.http.put<IYoRC>(this.resourceUrl, yoRC, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IYoRC>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IYoRC[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
