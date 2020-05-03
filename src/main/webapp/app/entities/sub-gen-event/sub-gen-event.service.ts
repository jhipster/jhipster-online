import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISubGenEvent } from 'app/shared/model/sub-gen-event.model';

type EntityResponseType = HttpResponse<ISubGenEvent>;
type EntityArrayResponseType = HttpResponse<ISubGenEvent[]>;

@Injectable({ providedIn: 'root' })
export class SubGenEventService {
  public resourceUrl = SERVER_API_URL + 'api/sub-gen-events';

  constructor(protected http: HttpClient) {}

  create(subGenEvent: ISubGenEvent): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(subGenEvent);
    return this.http
      .post<ISubGenEvent>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(subGenEvent: ISubGenEvent): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(subGenEvent);
    return this.http
      .put<ISubGenEvent>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISubGenEvent>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISubGenEvent[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(subGenEvent: ISubGenEvent): ISubGenEvent {
    const copy: ISubGenEvent = Object.assign({}, subGenEvent, {
      date: subGenEvent.date && subGenEvent.date.isValid() ? subGenEvent.date.toJSON() : undefined
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
      res.body.forEach((subGenEvent: ISubGenEvent) => {
        subGenEvent.date = subGenEvent.date ? moment(subGenEvent.date) : undefined;
      });
    }
    return res;
  }
}
