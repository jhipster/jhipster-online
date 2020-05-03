import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IYoRC } from 'app/shared/model/yo-rc.model';
import { YoRCService } from './yo-rc.service';
import { IGeneratorIdentity } from 'app/shared/model/generator-identity.model';
import { GeneratorIdentityService } from 'app/entities/generator-identity';

@Component({
    selector: 'jhi-yo-rc-update',
    templateUrl: './yo-rc-update.component.html'
})
export class YoRCUpdateComponent implements OnInit {
    private _yoRC: IYoRC;
    isSaving: boolean;

    generatoridentities: IGeneratorIdentity[];
    creationDate: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private yoRCService: YoRCService,
        private generatorIdentityService: GeneratorIdentityService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ yoRC }) => {
            this.yoRC = yoRC;
        });
        this.generatorIdentityService.query().subscribe(
            (res: HttpResponse<IGeneratorIdentity[]>) => {
                this.generatoridentities = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.yoRC.creationDate = moment(this.creationDate, DATE_TIME_FORMAT);
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

    trackGeneratorIdentityById(index: number, item: IGeneratorIdentity) {
        return item.id;
    }
    get yoRC() {
        return this._yoRC;
    }

    set yoRC(yoRC: IYoRC) {
        this._yoRC = yoRC;
        this.creationDate = moment(yoRC.creationDate).format(DATE_TIME_FORMAT);
    }
}
