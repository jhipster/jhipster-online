import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { IUserApplication } from 'app/shared/model/user-application.model';
import { Principal } from 'app/core';
import { UserApplicationService } from './user-application.service';

@Component({
    selector: 'jhi-user-application',
    templateUrl: './user-application.component.html'
})
export class UserApplicationComponent implements OnInit, OnDestroy {
    userApplications: IUserApplication[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private userApplicationService: UserApplicationService,
        private jhiAlertService: JhiAlertService,
        private dataUtils: JhiDataUtils,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.userApplicationService.query().subscribe(
            (res: HttpResponse<IUserApplication[]>) => {
                this.userApplications = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInUserApplications();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IUserApplication) {
        return item.id;
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    registerChangeInUserApplications() {
        this.eventSubscriber = this.eventManager.subscribe('userApplicationListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
