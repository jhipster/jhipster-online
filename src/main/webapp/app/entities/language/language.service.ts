import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ILanguage } from 'app/shared/model/language.model';

type EntityResponseType = HttpResponse<ILanguage>;
type EntityArrayResponseType = HttpResponse<ILanguage[]>;

@Injectable()
export class LanguageService {
    private resourceUrl = SERVER_API_URL + 'api/languages';

    constructor(private http: HttpClient) {}

    create(language: ILanguage): Observable<EntityResponseType> {
        return this.http.post<ILanguage>(this.resourceUrl, language, { observe: 'response' });
    }

    update(language: ILanguage): Observable<EntityResponseType> {
        return this.http.put<ILanguage>(this.resourceUrl, language, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ILanguage>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ILanguage[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
