import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IYoRC, YoRC } from 'app/shared/model/yo-rc.model';
import { YoRCService } from './yo-rc.service';
import { YoRCComponent } from './yo-rc.component';
import { YoRCDetailComponent } from './yo-rc-detail.component';
import { YoRCUpdateComponent } from './yo-rc-update.component';

@Injectable({ providedIn: 'root' })
export class YoRCResolve implements Resolve<IYoRC> {
  constructor(private service: YoRCService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IYoRC> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((yoRC: HttpResponse<YoRC>) => {
          if (yoRC.body) {
            return of(yoRC.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new YoRC());
  }
}

export const yoRCRoute: Routes = [
  {
    path: '',
    component: YoRCComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'YoRCS'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: YoRCDetailComponent,
    resolve: {
      yoRC: YoRCResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'YoRCS'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: YoRCUpdateComponent,
    resolve: {
      yoRC: YoRCResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'YoRCS'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: YoRCUpdateComponent,
    resolve: {
      yoRC: YoRCResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'YoRCS'
    },
    canActivate: [UserRouteAccessService]
  }
];
