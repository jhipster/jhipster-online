import { Component, OnInit } from '@angular/core';

import { User, PasswordResetService } from 'app/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
    selector: 'jhi-user-mgmt-reset-dialog',
    templateUrl: './user-management-reset-dialog.component.html'
})
export class UserMgmtResetDialogComponent implements OnInit {
    user: User;
    resetLink: string;
    showClipboardSuccess: boolean;
    showClipboardError: boolean;

    constructor(private passwordResetService: PasswordResetService, public activeModal: NgbActiveModal) {}

    ngOnInit() {
        this.showClipboardSuccess = false;
        this.showClipboardError = false;
    }

    generateResetLink(email: string) {
        this.passwordResetService.getResetLink(email).subscribe(value => (this.resetLink = value));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }
}
