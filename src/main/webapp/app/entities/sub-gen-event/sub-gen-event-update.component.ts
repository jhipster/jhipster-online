import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { ISubGenEvent } from 'app/shared/model/sub-gen-event.model';
import { SubGenEventService } from './sub-gen-event.service';
import { IGeneratorIdentity } from 'app/shared/model/generator-identity.model';
import { GeneratorIdentityService } from 'app/entities/generator-identity';

@Component({
    selector: 'jhi-sub-gen-event-update',
    templateUrl: './sub-gen-event-update.component.html'
})
export class SubGenEventUpdateComponent implements OnInit {
    private _subGenEvent: ISubGenEvent;
    isSaving: boolean;

    generatoridentities: IGeneratorIdentity[];
    date: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private subGenEventService: SubGenEventService,
        private generatorIdentityService: GeneratorIdentityService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ subGenEvent }) => {
            this.subGenEvent = subGenEvent;
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
        this.subGenEvent.date = moment(this.date, DATE_TIME_FORMAT);
        if (this.subGenEvent.id !== undefined) {
            this.subscribeToSaveResponse(this.subGenEventService.update(this.subGenEvent));
        } else {
            this.subscribeToSaveResponse(this.subGenEventService.create(this.subGenEvent));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ISubGenEvent>>) {
        result.subscribe((res: HttpResponse<ISubGenEvent>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
    get subGenEvent() {
        return this._subGenEvent;
    }

    set subGenEvent(subGenEvent: ISubGenEvent) {
        this._subGenEvent = subGenEvent;
        this.date = moment(subGenEvent.date).format(DATE_TIME_FORMAT);
    }
}
