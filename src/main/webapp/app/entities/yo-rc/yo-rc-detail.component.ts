import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IYoRC } from 'app/shared/model/yo-rc.model';

@Component({
    selector: 'jhi-yo-rc-detail',
    templateUrl: './yo-rc-detail.component.html'
})
export class YoRCDetailComponent implements OnInit {
    yoRC: IYoRC;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ yoRC }) => {
            this.yoRC = yoRC;
        });
    }

    previousState() {
        window.history.back();
    }
}
