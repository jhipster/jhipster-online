import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { EntityStats } from 'app/shared/model/entity-stats.model';
import { EntityStatsService } from './entity-stats.service';
import { EntityStatsComponent } from './entity-stats.component';
import { EntityStatsDetailComponent } from './entity-stats-detail.component';
import { EntityStatsUpdateComponent } from './entity-stats-update.component';
import { EntityStatsDeletePopupComponent } from './entity-stats-delete-dialog.component';
import { IEntityStats } from 'app/shared/model/entity-stats.model';

@Injectable({ providedIn: 'root' })
export class EntityStatsResolve implements Resolve<IEntityStats> {
    constructor(private service: EntityStatsService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((entityStats: HttpResponse<EntityStats>) => entityStats.body));
        }
        return of(new EntityStats());
    }
}

export const entityStatsRoute: Routes = [
    {
        path: 'entity-stats',
        component: EntityStatsComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EntityStats'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'entity-stats/:id/view',
        component: EntityStatsDetailComponent,
        resolve: {
            entityStats: EntityStatsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EntityStats'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'entity-stats/new',
        component: EntityStatsUpdateComponent,
        resolve: {
            entityStats: EntityStatsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EntityStats'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'entity-stats/:id/edit',
        component: EntityStatsUpdateComponent,
        resolve: {
            entityStats: EntityStatsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EntityStats'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const entityStatsPopupRoute: Routes = [
    {
        path: 'entity-stats/:id/delete',
        component: EntityStatsDeletePopupComponent,
        resolve: {
            entityStats: EntityStatsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EntityStats'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
