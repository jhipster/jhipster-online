import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IYoRC } from 'app/shared/model/yo-rc.model';
import { Principal } from 'app/core';
import { YoRCService } from './yo-rc.service';

@Component({
    selector: 'jhi-yo-rc',
    templateUrl: './yo-rc.component.html'
})
export class YoRCComponent implements OnInit, OnDestroy {
    yoRCS: IYoRC[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private yoRCService: YoRCService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.yoRCService.query().subscribe(
            (res: HttpResponse<IYoRC[]>) => {
                this.yoRCS = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInYoRCS();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IYoRC) {
        return item.id;
    }

    registerChangeInYoRCS() {
        this.eventSubscriber = this.eventManager.subscribe('yoRCListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
