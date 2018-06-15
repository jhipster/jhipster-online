import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEntityStats } from 'app/shared/model/entity-stats.model';

@Component({
    selector: 'jhi-entity-stats-detail',
    templateUrl: './entity-stats-detail.component.html'
})
export class EntityStatsDetailComponent implements OnInit {
    entityStats: IEntityStats;

    constructor(private route: ActivatedRoute) {}

    ngOnInit() {
        this.route.data.subscribe(({ entityStats }) => {
            this.entityStats = entityStats.body ? entityStats.body : entityStats;
        });
    }

    previousState() {
        window.history.back();
    }
}
