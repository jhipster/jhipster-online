import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISubGenEvent } from 'app/shared/model/sub-gen-event.model';

type EntityResponseType = HttpResponse<ISubGenEvent>;
type EntityArrayResponseType = HttpResponse<ISubGenEvent[]>;

@Injectable({ providedIn: 'root' })
export class SubGenEventService {
    private resourceUrl = SERVER_API_URL + 'api/sub-gen-events';

    constructor(private http: HttpClient) {}

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

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(subGenEvent: ISubGenEvent): ISubGenEvent {
        const copy: ISubGenEvent = Object.assign({}, subGenEvent, {
            date: subGenEvent.date != null && subGenEvent.date.isValid() ? subGenEvent.date.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.date = res.body.date != null ? moment(res.body.date) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((subGenEvent: ISubGenEvent) => {
            subGenEvent.date = subGenEvent.date != null ? moment(subGenEvent.date) : null;
        });
        return res;
    }
}
