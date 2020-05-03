import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IUserApplication, UserApplication } from 'app/shared/model/user-application.model';
import { UserApplicationService } from './user-application.service';
import { UserApplicationComponent } from './user-application.component';
import { UserApplicationDetailComponent } from './user-application-detail.component';
import { UserApplicationUpdateComponent } from './user-application-update.component';

@Injectable({ providedIn: 'root' })
export class UserApplicationResolve implements Resolve<IUserApplication> {
  constructor(private service: UserApplicationService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUserApplication> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((userApplication: HttpResponse<UserApplication>) => {
          if (userApplication.body) {
            return of(userApplication.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new UserApplication());
  }
}

export const userApplicationRoute: Routes = [
  {
    path: '',
    component: UserApplicationComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'UserApplications'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: UserApplicationDetailComponent,
    resolve: {
      userApplication: UserApplicationResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'UserApplications'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: UserApplicationUpdateComponent,
    resolve: {
      userApplication: UserApplicationResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'UserApplications'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: UserApplicationUpdateComponent,
    resolve: {
      userApplication: UserApplicationResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'UserApplications'
    },
    canActivate: [UserRouteAccessService]
  }
];
