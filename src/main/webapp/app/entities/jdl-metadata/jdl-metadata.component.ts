import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IJdlMetadata } from 'app/shared/model/jdl-metadata.model';
import { Principal } from 'app/core';
import { JdlMetadataService } from './jdl-metadata.service';

@Component({
    selector: 'jhi-jdl-metadata',
    templateUrl: './jdl-metadata.component.html'
})
export class JdlMetadataComponent implements OnInit, OnDestroy {
    jdlMetadata: IJdlMetadata[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private jdlMetadataService: JdlMetadataService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.jdlMetadataService.query().subscribe(
            (res: HttpResponse<IJdlMetadata[]>) => {
                this.jdlMetadata = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInJdlMetadata();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IJdlMetadata) {
        return item.id;
    }

    registerChangeInJdlMetadata() {
        this.eventSubscriber = this.eventManager.subscribe('jdlMetadataListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
