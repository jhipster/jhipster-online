import { Component, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { Principal, AccountService } from 'app/core';
import { DeleteAccountDialogComponent } from 'app/account/settings/delete-account-dialog.component';

@Component({
    selector: 'jhi-settings',
    templateUrl: './settings.component.html'
})
export class SettingsComponent implements OnInit {
    error: string;
    success: string;
    settingsAccount: any;
    languages: any[];

    constructor(private account: AccountService, private principal: Principal, private modalService: NgbModal) {}

    ngOnInit() {
        this.principal.identity().then(account => {
            this.settingsAccount = this.copyAccount(account);
        });
    }

    deleteAccount() {
        this.modalService.open(DeleteAccountDialogComponent, { size: 'lg', backdrop: 'static' });
    }

    save() {
        this.account.save(this.settingsAccount).subscribe(
            () => {
                this.error = null;
                this.success = 'OK';
                this.principal.identity(true).then(account => {
                    this.settingsAccount = this.copyAccount(account);
                });
            },
            () => {
                this.success = null;
                this.error = 'ERROR';
            }
        );
    }

    private copyAccount(account) {
        return {
            activated: account.activated,
            email: account.email,
            firstName: account.firstName,
            langKey: account.langKey,
            lastName: account.lastName,
            login: account.login,
            imageUrl: account.imageUrl
        };
    }
}
