import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITestFramework } from 'app/shared/model/test-framework.model';

@Component({
    selector: 'jhi-test-framework-detail',
    templateUrl: './test-framework-detail.component.html'
})
export class TestFrameworkDetailComponent implements OnInit {
    testFramework: ITestFramework;

    constructor(private route: ActivatedRoute) {}

    ngOnInit() {
        this.route.data.subscribe(({ testFramework }) => {
            this.testFramework = testFramework.body ? testFramework.body : testFramework;
        });
    }

    previousState() {
        window.history.back();
    }
}
