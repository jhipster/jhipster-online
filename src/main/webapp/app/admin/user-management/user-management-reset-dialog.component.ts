import { Component, OnInit } from '@angular/core';

import { User } from 'app/core';
import { PasswordResetService } from 'app/admin/user-management/password-reset.service';

@Component({
    selector: 'jhi-user-mgmt-reset-dialog',
    templateUrl: './user-management-reset-dialog.component.html'
})
export class UserMgmtResetDialogComponent implements OnInit {
    user: User;
    resetLink: string;

    constructor(private passwordResetService: PasswordResetService) {}

    ngOnInit() {
        this.getResetLink(this.user.email);
    }

    getResetLink(mail: string) {
        this.passwordResetService.getResetLink(mail).subscribe(value => {
            this.resetLink = value;
            console.log(this.resetLink);
        });
    }
}
