import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ILanguage } from 'app/shared/model/language.model';

type EntityResponseType = HttpResponse<ILanguage>;
type EntityArrayResponseType = HttpResponse<ILanguage[]>;

@Injectable({ providedIn: 'root' })
export class LanguageService {
  public resourceUrl = SERVER_API_URL + 'api/languages';

  constructor(protected http: HttpClient) {}

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

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
