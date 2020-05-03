import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISubGenEvent, SubGenEvent } from 'app/shared/model/sub-gen-event.model';
import { SubGenEventService } from './sub-gen-event.service';
import { SubGenEventComponent } from './sub-gen-event.component';
import { SubGenEventDetailComponent } from './sub-gen-event-detail.component';
import { SubGenEventUpdateComponent } from './sub-gen-event-update.component';

@Injectable({ providedIn: 'root' })
export class SubGenEventResolve implements Resolve<ISubGenEvent> {
  constructor(private service: SubGenEventService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISubGenEvent> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((subGenEvent: HttpResponse<SubGenEvent>) => {
          if (subGenEvent.body) {
            return of(subGenEvent.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SubGenEvent());
  }
}

export const subGenEventRoute: Routes = [
  {
    path: '',
    component: SubGenEventComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'SubGenEvents'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SubGenEventDetailComponent,
    resolve: {
      subGenEvent: SubGenEventResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'SubGenEvents'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SubGenEventUpdateComponent,
    resolve: {
      subGenEvent: SubGenEventResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'SubGenEvents'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SubGenEventUpdateComponent,
    resolve: {
      subGenEvent: SubGenEventResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'SubGenEvents'
    },
    canActivate: [UserRouteAccessService]
  }
];
