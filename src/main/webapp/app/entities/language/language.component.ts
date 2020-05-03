import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ILanguage } from 'app/shared/model/language.model';
import { Principal } from 'app/core';
import { LanguageService } from './language.service';

@Component({
    selector: 'jhi-language',
    templateUrl: './language.component.html'
})
export class LanguageComponent implements OnInit, OnDestroy {
    languages: ILanguage[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private languageService: LanguageService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.languageService.query().subscribe(
            (res: HttpResponse<ILanguage[]>) => {
                this.languages = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInLanguages();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ILanguage) {
        return item.id;
    }

    registerChangeInLanguages() {
        this.eventSubscriber = this.eventManager.subscribe('languageListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
