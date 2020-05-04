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
import { Component, AfterViewInit, ElementRef, ViewChild, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { PasswordResetInitService } from './password-reset-init.service';
import { PasswordResetService } from 'app/core/auth/password-reset.service';
import { HttpErrorResponse } from '@angular/common/http';
import { EMAIL_NOT_FOUND_TYPE } from 'app/shared/constants/error.constants';

@Component({
  selector: 'jhi-password-reset-init',
  templateUrl: './password-reset-init.component.html'
})
export class PasswordResetInitComponent implements AfterViewInit, OnInit {
  @ViewChild('email', { static: false })
  email?: ElementRef;
  isMailEnabled?: boolean;
  success: any;
  errorEmailNotExists: any;
  error: any;
  resetRequestForm = this.fb.group({
    email: ['', [Validators.required, Validators.minLength(5), Validators.maxLength(254), Validators.email]]
  });

  constructor(
    private passwordResetInitService: PasswordResetInitService,
    private elementRef: ElementRef,
    private passwordResetService: PasswordResetService,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.isMailEnabled = true;
    this.passwordResetService.getMailStatus().subscribe(result => (this.isMailEnabled = result['mailEnabled']));
  }

  ngAfterViewInit(): void {
    if (this.email) {
      this.email.nativeElement.focus();
    }
  }

  requestReset(): void {
    this.passwordResetInitService.save(this.resetRequestForm.get(['email'])!.value).subscribe(
      () => (this.success = true),
      response => this.processError(response)
    );
  }

  private processError(response: HttpErrorResponse): void {
    this.success = null;
    if (response.status === 400 && response.error.type === EMAIL_NOT_FOUND_TYPE) {
      this.errorEmailNotExists = 'ERROR';
    } else {
      this.error = 'ERROR';
    }
  }
}
