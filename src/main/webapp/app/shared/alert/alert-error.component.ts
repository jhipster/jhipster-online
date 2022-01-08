/**
 * Copyright 2017-2022 the original author or authors from the JHipster project.
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
import { Component, OnDestroy } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { JhiEventManager, JhiAlert, JhiAlertService, JhiEventWithContent } from 'ng-jhipster';
import { Subscription } from 'rxjs';

import { AlertError } from './alert-error.model';

@Component({
  selector: 'jhi-alert-error',
  template: `
    <div class="alerts" role="alert">
      <div *ngFor="let alert of alerts" [ngClass]="setClasses(alert)">
        <ngb-alert *ngIf="alert && alert.type && alert.msg" [type]="alert.type" (close)="alert.close(alerts)">
          <pre [innerHTML]="alert.msg"></pre>
        </ngb-alert>
      </div>
    </div>
  `
})
export class AlertErrorComponent implements OnDestroy {
  alerts: JhiAlert[] = [];
  errorListener: Subscription;
  httpErrorListener: Subscription;

  constructor(private alertService: JhiAlertService, private eventManager: JhiEventManager) {
    this.errorListener = eventManager.subscribe('jhonlineApp.error', (response: JhiEventWithContent<AlertError>) => {
      const errorResponse = response.content;
      this.addErrorAlert(errorResponse.message);
    });

    this.httpErrorListener = eventManager.subscribe('jhonlineApp.httpError', (response: JhiEventWithContent<HttpErrorResponse>) => {
      const httpErrorResponse = response.content;
      switch (httpErrorResponse.status) {
        // connection refused, server not reachable
        case 0:
          this.addErrorAlert('Server not reachable');
          break;

        case 400: {
          const arr = httpErrorResponse.headers.keys();
          let errorHeader = null;
          arr.forEach(entry => {
            if (entry.toLowerCase().endsWith('app-error')) {
              errorHeader = httpErrorResponse.headers.get(entry);
            }
          });
          if (errorHeader) {
            this.addErrorAlert(errorHeader);
          } else if (httpErrorResponse.error !== '' && httpErrorResponse.error.fieldErrors) {
            const fieldErrors = httpErrorResponse.error.fieldErrors;
            for (const fieldError of fieldErrors) {
              if (['Min', 'Max', 'DecimalMin', 'DecimalMax'].includes(fieldError.message)) {
                fieldError.message = 'Size';
              }
              // convert 'something[14].other[4].id' to 'something[].other[].id' so translations can be written to it
              const convertedField = fieldError.field.replace(/\[\d*\]/g, '[]');
              const fieldName = convertedField.charAt(0).toUpperCase() + convertedField.slice(1);
              this.addErrorAlert('Error on field "' + fieldName + '"');
            }
          } else if (httpErrorResponse.error !== '' && httpErrorResponse.error.message) {
            this.addErrorAlert(httpErrorResponse.error.message);
          } else {
            this.addErrorAlert(httpErrorResponse.error);
          }
          break;
        }

        case 404:
          this.addErrorAlert('Not found');
          break;

        default:
          if (httpErrorResponse.error !== '' && httpErrorResponse.error.message) {
            this.addErrorAlert(httpErrorResponse.error.message);
          } else {
            this.addErrorAlert(httpErrorResponse.error);
          }
      }
    });
  }

  setClasses(alert: JhiAlert): { [key: string]: boolean } {
    const classes = { 'jhi-toast': Boolean(alert.toast) };
    if (alert.position) {
      return { ...classes, [alert.position]: true };
    }
    return classes;
  }

  ngOnDestroy(): void {
    if (this.errorListener) {
      this.eventManager.destroy(this.errorListener);
    }
    if (this.httpErrorListener) {
      this.eventManager.destroy(this.httpErrorListener);
    }
  }

  addErrorAlert(message: string): void {
    const newAlert: JhiAlert = {
      type: 'danger',
      msg: message,
      timeout: 5000,
      toast: this.alertService.isToast(),
      scoped: true
    };

    this.alerts.push(this.alertService.addAlert(newAlert, this.alerts));
  }
}
