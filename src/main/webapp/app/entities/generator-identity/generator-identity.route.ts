import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IGeneratorIdentity, GeneratorIdentity } from 'app/shared/model/generator-identity.model';
import { GeneratorIdentityService } from './generator-identity.service';
import { GeneratorIdentityComponent } from './generator-identity.component';
import { GeneratorIdentityDetailComponent } from './generator-identity-detail.component';
import { GeneratorIdentityUpdateComponent } from './generator-identity-update.component';

@Injectable({ providedIn: 'root' })
export class GeneratorIdentityResolve implements Resolve<IGeneratorIdentity> {
  constructor(private service: GeneratorIdentityService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IGeneratorIdentity> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((generatorIdentity: HttpResponse<GeneratorIdentity>) => {
          if (generatorIdentity.body) {
            return of(generatorIdentity.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new GeneratorIdentity());
  }
}

export const generatorIdentityRoute: Routes = [
  {
    path: '',
    component: GeneratorIdentityComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'GeneratorIdentities'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: GeneratorIdentityDetailComponent,
    resolve: {
      generatorIdentity: GeneratorIdentityResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'GeneratorIdentities'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: GeneratorIdentityUpdateComponent,
    resolve: {
      generatorIdentity: GeneratorIdentityResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'GeneratorIdentities'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: GeneratorIdentityUpdateComponent,
    resolve: {
      generatorIdentity: GeneratorIdentityResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'GeneratorIdentities'
    },
    canActivate: [UserRouteAccessService]
  }
];
