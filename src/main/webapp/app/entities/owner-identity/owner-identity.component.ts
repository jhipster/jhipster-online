import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IOwnerIdentity } from 'app/shared/model/owner-identity.model';
import { Principal } from 'app/core';
import { OwnerIdentityService } from './owner-identity.service';

@Component({
    selector: 'jhi-owner-identity',
    templateUrl: './owner-identity.component.html'
})
export class OwnerIdentityComponent implements OnInit, OnDestroy {
    ownerIdentities: IOwnerIdentity[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private ownerIdentityService: OwnerIdentityService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.ownerIdentityService.query().subscribe(
            (res: HttpResponse<IOwnerIdentity[]>) => {
                this.ownerIdentities = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInOwnerIdentities();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IOwnerIdentity) {
        return item.id;
    }

    registerChangeInOwnerIdentities() {
        this.eventSubscriber = this.eventManager.subscribe('ownerIdentityListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
