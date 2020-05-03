import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEntityStats } from 'app/shared/model/entity-stats.model';

type EntityResponseType = HttpResponse<IEntityStats>;
type EntityArrayResponseType = HttpResponse<IEntityStats[]>;

@Injectable({ providedIn: 'root' })
export class EntityStatsService {
  public resourceUrl = SERVER_API_URL + 'api/entity-stats';

  constructor(protected http: HttpClient) {}

  create(entityStats: IEntityStats): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(entityStats);
    return this.http
      .post<IEntityStats>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(entityStats: IEntityStats): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(entityStats);
    return this.http
      .put<IEntityStats>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IEntityStats>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IEntityStats[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(entityStats: IEntityStats): IEntityStats {
    const copy: IEntityStats = Object.assign({}, entityStats, {
      date: entityStats.date && entityStats.date.isValid() ? entityStats.date.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.date = res.body.date ? moment(res.body.date) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((entityStats: IEntityStats) => {
        entityStats.date = entityStats.date ? moment(entityStats.date) : undefined;
      });
    }
    return res;
  }
}
