import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEntityStats } from 'app/shared/model/entity-stats.model';

@Component({
    selector: 'jhi-entity-stats-detail',
    templateUrl: './entity-stats-detail.component.html'
})
export class EntityStatsDetailComponent implements OnInit {
    entityStats: IEntityStats;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ entityStats }) => {
            this.entityStats = entityStats;
        });
    }

    previousState() {
        window.history.back();
    }
}
