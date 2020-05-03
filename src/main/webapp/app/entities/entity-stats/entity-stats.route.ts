import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IEntityStats, EntityStats } from 'app/shared/model/entity-stats.model';
import { EntityStatsService } from './entity-stats.service';
import { EntityStatsComponent } from './entity-stats.component';
import { EntityStatsDetailComponent } from './entity-stats-detail.component';
import { EntityStatsUpdateComponent } from './entity-stats-update.component';

@Injectable({ providedIn: 'root' })
export class EntityStatsResolve implements Resolve<IEntityStats> {
  constructor(private service: EntityStatsService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEntityStats> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((entityStats: HttpResponse<EntityStats>) => {
          if (entityStats.body) {
            return of(entityStats.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new EntityStats());
  }
}

export const entityStatsRoute: Routes = [
  {
    path: '',
    component: EntityStatsComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'EntityStats'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EntityStatsDetailComponent,
    resolve: {
      entityStats: EntityStatsResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'EntityStats'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EntityStatsUpdateComponent,
    resolve: {
      entityStats: EntityStatsResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'EntityStats'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EntityStatsUpdateComponent,
    resolve: {
      entityStats: EntityStatsResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'EntityStats'
    },
    canActivate: [UserRouteAccessService]
  }
];
