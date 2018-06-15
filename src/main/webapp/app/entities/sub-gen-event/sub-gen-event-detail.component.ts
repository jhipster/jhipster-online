import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISubGenEvent } from 'app/shared/model/sub-gen-event.model';

@Component({
    selector: 'jhi-sub-gen-event-detail',
    templateUrl: './sub-gen-event-detail.component.html'
})
export class SubGenEventDetailComponent implements OnInit {
    subGenEvent: ISubGenEvent;

    constructor(private route: ActivatedRoute) {}

    ngOnInit() {
        this.route.data.subscribe(({ subGenEvent }) => {
            this.subGenEvent = subGenEvent.body ? subGenEvent.body : subGenEvent;
        });
    }

    previousState() {
        window.history.back();
    }
}
