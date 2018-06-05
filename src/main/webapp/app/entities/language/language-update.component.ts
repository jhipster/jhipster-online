import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { JhiAlertService } from 'ng-jhipster';

import { ILanguage } from 'app/shared/model/language.model';
import { LanguageService } from './language.service';
import { IYoRC } from 'app/shared/model/yo-rc.model';
import { YoRCService } from 'app/entities/yo-rc';

@Component({
    selector: 'jhi-language-update',
    templateUrl: './language-update.component.html'
})
export class LanguageUpdateComponent implements OnInit {
    private _language: ILanguage;
    isSaving: boolean;

    yorcs: IYoRC[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private languageService: LanguageService,
        private yoRCService: YoRCService,
        private route: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.route.data.subscribe(({ language }) => {
            this.language = language.body ? language.body : language;
        });
        this.yoRCService.query().subscribe(
            (res: HttpResponse<IYoRC[]>) => {
                this.yorcs = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.language.id !== undefined) {
            this.subscribeToSaveResponse(this.languageService.update(this.language));
        } else {
            this.subscribeToSaveResponse(this.languageService.create(this.language));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ILanguage>>) {
        result.subscribe((res: HttpResponse<ILanguage>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackYoRCById(index: number, item: IYoRC) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
    get language() {
        return this._language;
    }

    set language(language: ILanguage) {
        this._language = language;
    }
}
