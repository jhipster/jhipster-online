import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IYoRC } from 'app/shared/model/yo-rc.model';

@Component({
    selector: 'jhi-yo-rc-detail',
    templateUrl: './yo-rc-detail.component.html'
})
export class YoRCDetailComponent implements OnInit {
    yoRC: IYoRC;

    constructor(private route: ActivatedRoute) {}

    ngOnInit() {
        this.route.data.subscribe(({ yoRC }) => {
            this.yoRC = yoRC.body ? yoRC.body : yoRC;
        });
    }

    previousState() {
        window.history.back();
    }
}
