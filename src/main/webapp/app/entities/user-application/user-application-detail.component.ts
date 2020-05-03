import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IUserApplication } from 'app/shared/model/user-application.model';

@Component({
    selector: 'jhi-user-application-detail',
    templateUrl: './user-application-detail.component.html'
})
export class UserApplicationDetailComponent implements OnInit {
    userApplication: IUserApplication;

    constructor(private dataUtils: JhiDataUtils, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
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
    previousState() {
        window.history.back();
    }
}
