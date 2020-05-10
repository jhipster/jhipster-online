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
