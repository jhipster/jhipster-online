import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ISubGenEvent } from 'app/shared/model/sub-gen-event.model';
import { Principal } from 'app/core';
import { SubGenEventService } from './sub-gen-event.service';

@Component({
    selector: 'jhi-sub-gen-event',
    templateUrl: './sub-gen-event.component.html'
})
export class SubGenEventComponent implements OnInit, OnDestroy {
    subGenEvents: ISubGenEvent[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private subGenEventService: SubGenEventService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.subGenEventService.query().subscribe(
            (res: HttpResponse<ISubGenEvent[]>) => {
                this.subGenEvents = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInSubGenEvents();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ISubGenEvent) {
        return item.id;
    }

    registerChangeInSubGenEvents() {
        this.eventSubscriber = this.eventManager.subscribe('subGenEventListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
