import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import * as moment from 'moment';

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
        const copy = this.convertDateFromClient(yoRC);
        return this.http
            .post<IYoRC>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertDateFromServer(res));
    }

    update(yoRC: IYoRC): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(yoRC);
        return this.http
            .put<IYoRC>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertDateFromServer(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IYoRC>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertDateFromServer(res));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IYoRC[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(yoRC: IYoRC): IYoRC {
        const copy: IYoRC = Object.assign({}, yoRC, {
            createdDate: yoRC.createdDate != null && yoRC.createdDate.isValid() ? yoRC.createdDate.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.createdDate = res.body.createdDate != null ? moment(res.body.createdDate) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((yoRC: IYoRC) => {
            yoRC.createdDate = yoRC.createdDate != null ? moment(yoRC.createdDate) : null;
        });
        return res;
    }
}
