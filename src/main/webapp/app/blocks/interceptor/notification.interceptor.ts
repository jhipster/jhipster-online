/**
 * Copyright 2017-2020 the original author or authors from the JHipster Online project.
 *
 * This file is part of the JHipster Online project, see https://github.com/jhipster/jhipster-online
 * for more information.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import { JhiAlertService } from 'ng-jhipster';
import { HttpInterceptor, HttpRequest, HttpResponse, HttpHandler, HttpEvent } from '@angular/common/http';
import { Injector } from '@angular/core';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';

export class NotificationInterceptor implements HttpInterceptor {
    private alertService: JhiAlertService;

    // tslint:disable-next-line: no-unused-variable
    constructor(private injector: Injector) {
        setTimeout(() => (this.alertService = injector.get(JhiAlertService)));
    }

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        return next.handle(request).pipe(
            tap(
                (event: HttpEvent<any>) => {
                    if (event instanceof HttpResponse) {
                        const arr = event.headers.keys();
                        let alert = null;
                        let alertParams = null;
                        arr.forEach(entry => {
                            if (entry.toLowerCase().endsWith('app-alert')) {
                                alert = event.headers.get(entry);
                            } else if (entry.toLowerCase().endsWith('app-params')) {
                                alertParams = event.headers.get(entry);
                            }
                        });
                        if (alert) {
                            if (typeof alert === 'string') {
                                if (this.alertService) {
                                    this.alertService.success(alert, { param: alertParams }, null);
                                }
                            }
                        }
                    }
                },
                (err: any) => {}
            )
        );
    }
}
