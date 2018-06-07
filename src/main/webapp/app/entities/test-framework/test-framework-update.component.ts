import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { JhiAlertService } from 'ng-jhipster';

import { ITestFramework } from 'app/shared/model/test-framework.model';
import { TestFrameworkService } from './test-framework.service';
import { IYoRC } from 'app/shared/model/yo-rc.model';
import { YoRCService } from 'app/entities/yo-rc';

@Component({
    selector: 'jhi-test-framework-update',
    templateUrl: './test-framework-update.component.html'
})
export class TestFrameworkUpdateComponent implements OnInit {
    private _testFramework: ITestFramework;
    isSaving: boolean;

    yorcs: IYoRC[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private testFrameworkService: TestFrameworkService,
        private yoRCService: YoRCService,
        private route: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.route.data.subscribe(({ testFramework }) => {
            this.testFramework = testFramework.body ? testFramework.body : testFramework;
        });
        this.yoRCService.query().subscribe(
            (res: HttpResponse<IYoRC[]>) => {
                this.yorcs = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.testFramework.id !== undefined) {
            this.subscribeToSaveResponse(this.testFrameworkService.update(this.testFramework));
        } else {
            this.subscribeToSaveResponse(this.testFrameworkService.create(this.testFramework));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ITestFramework>>) {
        result.subscribe((res: HttpResponse<ITestFramework>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackYoRCById(index: number, item: IYoRC) {
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
    get testFramework() {
        return this._testFramework;
    }

    set testFramework(testFramework: ITestFramework) {
        this._testFramework = testFramework;
    }
}
