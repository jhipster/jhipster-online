import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { IUserApplication } from 'app/shared/model/user-application.model';
import { UserApplicationService } from './user-application.service';

@Component({
    selector: 'jhi-user-application-update',
    templateUrl: './user-application-update.component.html'
})
export class UserApplicationUpdateComponent implements OnInit {
    private _userApplication: IUserApplication;
    isSaving: boolean;

    constructor(
        private dataUtils: JhiDataUtils,
        private userApplicationService: UserApplicationService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ userApplication }) => {
            this.userApplication = userApplication;
        });
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.userApplication.id !== undefined) {
            this.subscribeToSaveResponse(this.userApplicationService.update(this.userApplication));
        } else {
            this.subscribeToSaveResponse(this.userApplicationService.create(this.userApplication));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IUserApplication>>) {
        result.subscribe((res: HttpResponse<IUserApplication>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get userApplication() {
        return this._userApplication;
    }

    set userApplication(userApplication: IUserApplication) {
        this._userApplication = userApplication;
    }
}
