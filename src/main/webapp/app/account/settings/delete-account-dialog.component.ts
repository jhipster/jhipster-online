import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { AccountService, Principal } from 'app/core';

@Component({
    selector: 'jhi-account-delete-dialog',
    templateUrl: './delete-account-dialog.component.html'
})
export class DeleteAccountDialogComponent {
    constructor(private principal: Principal, public account: AccountService, public activeModal: NgbActiveModal, private router: Router) {}

    clear() {
        this.activeModal.dismiss();
    }

    confirmDelete() {
        this.account.delete().subscribe(() => {
            this.activeModal.dismiss(true);
            this.principal.identity().then(() => {
                this.principal.authenticate(null);
                this.router.navigate(['/']);
            });
        });
    }
}
