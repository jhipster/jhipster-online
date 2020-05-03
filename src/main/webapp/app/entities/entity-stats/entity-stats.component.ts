import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IEntityStats } from 'app/shared/model/entity-stats.model';
import { Principal } from 'app/core';
import { EntityStatsService } from './entity-stats.service';

@Component({
    selector: 'jhi-entity-stats',
    templateUrl: './entity-stats.component.html'
})
export class EntityStatsComponent implements OnInit, OnDestroy {
    entityStats: IEntityStats[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private entityStatsService: EntityStatsService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.entityStatsService.query().subscribe(
            (res: HttpResponse<IEntityStats[]>) => {
                this.entityStats = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInEntityStats();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IEntityStats) {
        return item.id;
    }

    registerChangeInEntityStats() {
        this.eventSubscriber = this.eventManager.subscribe('entityStatsListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
