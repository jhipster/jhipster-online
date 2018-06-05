import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { JhiAlertService } from 'ng-jhipster';

import { IYoRC } from 'app/shared/model/yo-rc.model';
import { YoRCService } from './yo-rc.service';
import { IUser, UserService } from 'app/core';
import { ILanguage } from 'app/shared/model/language.model';
import { LanguageService } from 'app/entities/language';

@Component({
    selector: 'jhi-yo-rc-update',
    templateUrl: './yo-rc-update.component.html'
})
export class YoRCUpdateComponent implements OnInit {
    private _yoRC: IYoRC;
    isSaving: boolean;

    users: IUser[];

    languages: ILanguage[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private yoRCService: YoRCService,
        private userService: UserService,
        private languageService: LanguageService,
        private route: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.route.data.subscribe(({ yoRC }) => {
            this.yoRC = yoRC.body ? yoRC.body : yoRC;
        });
        this.userService.query().subscribe(
            (res: HttpResponse<IUser[]>) => {
                this.users = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.languageService.query().subscribe(
            (res: HttpResponse<ILanguage[]>) => {
                this.languages = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.yoRC.id !== undefined) {
            this.subscribeToSaveResponse(this.yoRCService.update(this.yoRC));
        } else {
            this.subscribeToSaveResponse(this.yoRCService.create(this.yoRC));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IYoRC>>) {
        result.subscribe((res: HttpResponse<IYoRC>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackUserById(index: number, item: IUser) {
        return item.id;
    }

    trackLanguageById(index: number, item: ILanguage) {
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
    get yoRC() {
        return this._yoRC;
    }

    set yoRC(yoRC: IYoRC) {
        this._yoRC = yoRC;
    }
}
