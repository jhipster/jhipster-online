import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IGeneratorIdentity } from 'app/shared/model/generator-identity.model';
import { Principal } from 'app/core';
import { GeneratorIdentityService } from './generator-identity.service';

@Component({
    selector: 'jhi-generator-identity',
    templateUrl: './generator-identity.component.html'
})
export class GeneratorIdentityComponent implements OnInit, OnDestroy {
    generatorIdentities: IGeneratorIdentity[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private generatorIdentityService: GeneratorIdentityService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.generatorIdentityService.query().subscribe(
            (res: HttpResponse<IGeneratorIdentity[]>) => {
                this.generatorIdentities = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInGeneratorIdentities();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IGeneratorIdentity) {
        return item.id;
    }

    registerChangeInGeneratorIdentities() {
        this.eventSubscriber = this.eventManager.subscribe('generatorIdentityListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
