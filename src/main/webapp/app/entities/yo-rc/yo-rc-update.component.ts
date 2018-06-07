import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { JhiAlertService } from 'ng-jhipster';

import { IYoRC } from 'app/shared/model/yo-rc.model';
import { YoRCService } from './yo-rc.service';
import { IOwnerIdentity } from 'app/shared/model/owner-identity.model';
import { OwnerIdentityService } from 'app/entities/owner-identity';

@Component({
    selector: 'jhi-yo-rc-update',
    templateUrl: './yo-rc-update.component.html'
})
export class YoRCUpdateComponent implements OnInit {
    private _yoRC: IYoRC;
    isSaving: boolean;

    owneridentities: IOwnerIdentity[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private yoRCService: YoRCService,
        private ownerIdentityService: OwnerIdentityService,
        private route: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.route.data.subscribe(({ yoRC }) => {
            this.yoRC = yoRC.body ? yoRC.body : yoRC;
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

    trackOwnerIdentityById(index: number, item: IOwnerIdentity) {
        return item.id;
    }
    get yoRC() {
        return this._yoRC;
    }

    set yoRC(yoRC: IYoRC) {
        this._yoRC = yoRC;
    }
}
