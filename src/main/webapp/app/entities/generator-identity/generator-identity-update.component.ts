import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { JhiAlertService } from 'ng-jhipster';

import { IGeneratorIdentity } from 'app/shared/model/generator-identity.model';
import { GeneratorIdentityService } from './generator-identity.service';
import { IOwnerIdentity } from 'app/shared/model/owner-identity.model';
import { OwnerIdentityService } from 'app/entities/owner-identity';

@Component({
    selector: 'jhi-generator-identity-update',
    templateUrl: './generator-identity-update.component.html'
})
export class GeneratorIdentityUpdateComponent implements OnInit {
    private _generatorIdentity: IGeneratorIdentity;
    isSaving: boolean;

    owneridentities: IOwnerIdentity[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private generatorIdentityService: GeneratorIdentityService,
        private ownerIdentityService: OwnerIdentityService,
        private route: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.route.data.subscribe(({ generatorIdentity }) => {
            this.generatorIdentity = generatorIdentity.body ? generatorIdentity.body : generatorIdentity;
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
        if (this.generatorIdentity.id !== undefined) {
            this.subscribeToSaveResponse(this.generatorIdentityService.update(this.generatorIdentity));
        } else {
            this.subscribeToSaveResponse(this.generatorIdentityService.create(this.generatorIdentity));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IGeneratorIdentity>>) {
        result.subscribe((res: HttpResponse<IGeneratorIdentity>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
    get generatorIdentity() {
        return this._generatorIdentity;
    }

    set generatorIdentity(generatorIdentity: IGeneratorIdentity) {
        this._generatorIdentity = generatorIdentity;
    }
}
