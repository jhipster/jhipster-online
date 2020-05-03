import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IJdlMetadata, JdlMetadata } from 'app/shared/model/jdl-metadata.model';
import { JdlMetadataService } from './jdl-metadata.service';
import { JdlMetadataComponent } from './jdl-metadata.component';
import { JdlMetadataDetailComponent } from './jdl-metadata-detail.component';
import { JdlMetadataUpdateComponent } from './jdl-metadata-update.component';

@Injectable({ providedIn: 'root' })
export class JdlMetadataResolve implements Resolve<IJdlMetadata> {
  constructor(private service: JdlMetadataService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IJdlMetadata> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((jdlMetadata: HttpResponse<JdlMetadata>) => {
          if (jdlMetadata.body) {
            return of(jdlMetadata.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new JdlMetadata());
  }
}

export const jdlMetadataRoute: Routes = [
  {
    path: '',
    component: JdlMetadataComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'JdlMetadata'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: JdlMetadataDetailComponent,
    resolve: {
      jdlMetadata: JdlMetadataResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'JdlMetadata'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: JdlMetadataUpdateComponent,
    resolve: {
      jdlMetadata: JdlMetadataResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'JdlMetadata'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: JdlMetadataUpdateComponent,
    resolve: {
      jdlMetadata: JdlMetadataResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'JdlMetadata'
    },
    canActivate: [UserRouteAccessService]
  }
];
