import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IJdlMetadata } from 'app/shared/model/jdl-metadata.model';
import { JdlMetadataService } from './jdl-metadata.service';
import { IUser, UserService } from 'app/core';

@Component({
    selector: 'jhi-jdl-metadata-update',
    templateUrl: './jdl-metadata-update.component.html'
})
export class JdlMetadataUpdateComponent implements OnInit {
    private _jdlMetadata: IJdlMetadata;
    isSaving: boolean;

    users: IUser[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private jdlMetadataService: JdlMetadataService,
        private userService: UserService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ jdlMetadata }) => {
            this.jdlMetadata = jdlMetadata;
        });
        this.userService.query().subscribe(
            (res: HttpResponse<IUser[]>) => {
                this.users = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.jdlMetadata.id !== undefined) {
            this.subscribeToSaveResponse(this.jdlMetadataService.update(this.jdlMetadata));
        } else {
            this.subscribeToSaveResponse(this.jdlMetadataService.create(this.jdlMetadata));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IJdlMetadata>>) {
        result.subscribe((res: HttpResponse<IJdlMetadata>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
    get jdlMetadata() {
        return this._jdlMetadata;
    }

    set jdlMetadata(jdlMetadata: IJdlMetadata) {
        this._jdlMetadata = jdlMetadata;
    }
}
