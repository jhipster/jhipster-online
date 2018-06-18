import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IEntityStats } from 'app/shared/model/entity-stats.model';
import { EntityStatsService } from './entity-stats.service';
import { IOwnerIdentity } from 'app/shared/model/owner-identity.model';
import { OwnerIdentityService } from 'app/entities/owner-identity';

@Component({
    selector: 'jhi-entity-stats-update',
    templateUrl: './entity-stats-update.component.html'
})
export class EntityStatsUpdateComponent implements OnInit {
    private _entityStats: IEntityStats;
    isSaving: boolean;

    owneridentities: IOwnerIdentity[];
    date: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private entityStatsService: EntityStatsService,
        private ownerIdentityService: OwnerIdentityService,
        private route: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.route.data.subscribe(({ entityStats }) => {
            this.entityStats = entityStats.body ? entityStats.body : entityStats;
        });
        this.ownerIdentityService.query().subscribe(
            (res: HttpResponse<IOwnerIdentity[]>) => {
                this.owneridentities = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.entityStats.date = moment(this.date, DATE_TIME_FORMAT);
        if (this.entityStats.id !== undefined) {
            this.subscribeToSaveResponse(this.entityStatsService.update(this.entityStats));
        } else {
            this.subscribeToSaveResponse(this.entityStatsService.create(this.entityStats));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IEntityStats>>) {
        result.subscribe((res: HttpResponse<IEntityStats>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackOwnerIdentityById(index: number, item: IOwnerIdentity) {
        return item.id;
    }
    get entityStats() {
        return this._entityStats;
    }

    set entityStats(entityStats: IEntityStats) {
        this._entityStats = entityStats;
        this.date = moment(entityStats.date).format();
    }
}