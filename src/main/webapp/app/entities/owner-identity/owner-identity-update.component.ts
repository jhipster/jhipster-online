import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { JhiAlertService } from 'ng-jhipster';

import { IOwnerIdentity } from 'app/shared/model/owner-identity.model';
import { OwnerIdentityService } from './owner-identity.service';
import { IUser, UserService } from 'app/core';

@Component({
    selector: 'jhi-owner-identity-update',
    templateUrl: './owner-identity-update.component.html'
})
export class OwnerIdentityUpdateComponent implements OnInit {
    private _ownerIdentity: IOwnerIdentity;
    isSaving: boolean;

    users: IUser[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private ownerIdentityService: OwnerIdentityService,
        private userService: UserService,
        private route: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.route.data.subscribe(({ ownerIdentity }) => {
            this.ownerIdentity = ownerIdentity.body ? ownerIdentity.body : ownerIdentity;
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
        if (this.ownerIdentity.id !== undefined) {
            this.subscribeToSaveResponse(this.ownerIdentityService.update(this.ownerIdentity));
        } else {
            this.subscribeToSaveResponse(this.ownerIdentityService.create(this.ownerIdentity));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IOwnerIdentity>>) {
        result.subscribe((res: HttpResponse<IOwnerIdentity>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
    get ownerIdentity() {
        return this._ownerIdentity;
    }

    set ownerIdentity(ownerIdentity: IOwnerIdentity) {
        this._ownerIdentity = ownerIdentity;
    }
}
