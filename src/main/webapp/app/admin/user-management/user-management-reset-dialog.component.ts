/**
 * Copyright 2017-2024 the original author or authors from the JHipster project.
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
import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { User } from 'app/core/user/user.model';
import { PasswordResetService } from 'app/core/auth/password-reset.service';

@Component({
  selector: 'jhi-user-mgmt-reset-dialog',
  templateUrl: './user-management-reset-dialog.component.html'
})
export class UserMgmtResetDialogComponent {
  user?: User;
  resetLink: string;
  showClipboardSuccess: boolean;
  showClipboardError: boolean;
  isGeneratingLink: boolean;

  constructor(private passwordResetService: PasswordResetService, public activeModal: NgbActiveModal) {
    this.resetLink = '';
    this.showClipboardSuccess = false;
    this.showClipboardError = false;
    this.isGeneratingLink = false;
  }

  generateResetLink(email: string): void {
    this.isGeneratingLink = true;
    this.passwordResetService.getResetLink(email).subscribe(
      value => {
        this.resetLink = value;
        this.isGeneratingLink = false;
      },
      () => {
        this.isGeneratingLink = false;
      }
    );
  }

  clear(): void {
    this.activeModal.dismiss('cancel');
  }
}
