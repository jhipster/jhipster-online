import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { timer } from 'rxjs/observable/timer';

import { AccountService, Principal } from 'app/core';

@Component({
    selector: 'jhi-account-delete-dialog',
    templateUrl: './delete-account-dialog.component.html'
})
export class DeleteAccountDialogComponent implements OnInit {
    showAlert: boolean;

    constructor(private principal: Principal, public account: AccountService, public activeModal: NgbActiveModal, private router: Router) {}

    ngOnInit() {
        this.showAlert = false;
    }

    clear() {
        this.activeModal.dismiss();
    }

    confirmDelete() {
        this.account.delete().subscribe(() => {
            this.principal.identity().then(() => {
                this.principal.authenticate(null);
                this.redirectToHomepage();
            });
        });
    }

    redirectToHomepage() {
        this.showAlert = true;
        timer(2000).subscribe(() => {
            this.activeModal.close();
            this.router.navigate(['/']);
        });
    }
}
