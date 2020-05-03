import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IYoRC } from 'app/shared/model/yo-rc.model';

type EntityResponseType = HttpResponse<IYoRC>;
type EntityArrayResponseType = HttpResponse<IYoRC[]>;

@Injectable({ providedIn: 'root' })
export class YoRCService {
  public resourceUrl = SERVER_API_URL + 'api/yo-rcs';

  constructor(protected http: HttpClient) {}

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

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(yoRC: IYoRC): IYoRC {
    const copy: IYoRC = Object.assign({}, yoRC, {
      creationDate: yoRC.creationDate && yoRC.creationDate.isValid() ? yoRC.creationDate.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.creationDate = res.body.creationDate ? moment(res.body.creationDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((yoRC: IYoRC) => {
        yoRC.creationDate = yoRC.creationDate ? moment(yoRC.creationDate) : undefined;
      });
    }
    return res;
  }
}
