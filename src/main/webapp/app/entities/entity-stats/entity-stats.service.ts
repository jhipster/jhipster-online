import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IEntityStats } from 'app/shared/model/entity-stats.model';

type EntityResponseType = HttpResponse<IEntityStats>;
type EntityArrayResponseType = HttpResponse<IEntityStats[]>;

@Injectable()
export class EntityStatsService {
    private resourceUrl = SERVER_API_URL + 'api/entity-stats';

    constructor(private http: HttpClient) {}

    create(entityStats: IEntityStats): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(entityStats);
        return this.http
            .post<IEntityStats>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertDateFromServer(res));
    }

    update(entityStats: IEntityStats): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(entityStats);
        return this.http
            .put<IEntityStats>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertDateFromServer(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IEntityStats>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertDateFromServer(res));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IEntityStats[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(entityStats: IEntityStats): IEntityStats {
        const copy: IEntityStats = Object.assign({}, entityStats, {
            date: entityStats.date != null && entityStats.date.isValid() ? entityStats.date.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.date = res.body.date != null ? moment(res.body.date) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((entityStats: IEntityStats) => {
            entityStats.date = entityStats.date != null ? moment(entityStats.date) : null;
        });
        return res;
    }
}
