import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOwnerIdentity } from 'app/shared/model/owner-identity.model';

@Component({
    selector: 'jhi-owner-identity-detail',
    templateUrl: './owner-identity-detail.component.html'
})
export class OwnerIdentityDetailComponent implements OnInit {
    ownerIdentity: IOwnerIdentity;

    constructor(private route: ActivatedRoute) {}

    ngOnInit() {
        this.route.data.subscribe(({ ownerIdentity }) => {
            this.ownerIdentity = ownerIdentity.body ? ownerIdentity.body : ownerIdentity;
        });
    }

    previousState() {
        window.history.back();
    }
}
