import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ITestFramework } from 'app/shared/model/test-framework.model';
import { Principal } from 'app/core';
import { TestFrameworkService } from './test-framework.service';

@Component({
    selector: 'jhi-test-framework',
    templateUrl: './test-framework.component.html'
})
export class TestFrameworkComponent implements OnInit, OnDestroy {
    testFrameworks: ITestFramework[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private testFrameworkService: TestFrameworkService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.testFrameworkService.query().subscribe(
            (res: HttpResponse<ITestFramework[]>) => {
                this.testFrameworks = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInTestFrameworks();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ITestFramework) {
        return item.id;
    }

    registerChangeInTestFrameworks() {
        this.eventSubscriber = this.eventManager.subscribe('testFrameworkListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
