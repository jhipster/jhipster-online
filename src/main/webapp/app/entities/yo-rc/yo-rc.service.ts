import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IYoRC } from 'app/shared/model/yo-rc.model';

type EntityResponseType = HttpResponse<IYoRC>;
type EntityArrayResponseType = HttpResponse<IYoRC[]>;

@Injectable({ providedIn: 'root' })
export class YoRCService {
    private resourceUrl = SERVER_API_URL + 'api/yo-rcs';

    constructor(private http: HttpClient) {}

    create(yoRC: IYoRC): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(yoRC);
        return this.http
            .post<IYoRC>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(yoRC: IYoRC): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(yoRC);
        return this.http
            .put<IYoRC>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IYoRC>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IYoRC[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(yoRC: IYoRC): IYoRC {
        const copy: IYoRC = Object.assign({}, yoRC, {
            creationDate: yoRC.creationDate != null && yoRC.creationDate.isValid() ? yoRC.creationDate.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.creationDate = res.body.creationDate != null ? moment(res.body.creationDate) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((yoRC: IYoRC) => {
            yoRC.creationDate = yoRC.creationDate != null ? moment(yoRC.creationDate) : null;
        });
        return res;
    }
}
