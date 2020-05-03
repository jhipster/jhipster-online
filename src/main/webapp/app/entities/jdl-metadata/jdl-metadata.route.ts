import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { JdlMetadata } from 'app/shared/model/jdl-metadata.model';
import { JdlMetadataService } from './jdl-metadata.service';
import { JdlMetadataComponent } from './jdl-metadata.component';
import { JdlMetadataDetailComponent } from './jdl-metadata-detail.component';
import { JdlMetadataUpdateComponent } from './jdl-metadata-update.component';
import { JdlMetadataDeletePopupComponent } from './jdl-metadata-delete-dialog.component';
import { IJdlMetadata } from 'app/shared/model/jdl-metadata.model';

@Injectable({ providedIn: 'root' })
export class JdlMetadataResolve implements Resolve<IJdlMetadata> {
    constructor(private service: JdlMetadataService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((jdlMetadata: HttpResponse<JdlMetadata>) => jdlMetadata.body));
        }
        return of(new JdlMetadata());
    }
}

export const jdlMetadataRoute: Routes = [
    {
        path: 'jdl-metadata',
        component: JdlMetadataComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'JdlMetadata'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'jdl-metadata/:id/view',
        component: JdlMetadataDetailComponent,
        resolve: {
            jdlMetadata: JdlMetadataResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'JdlMetadata'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'jdl-metadata/new',
        component: JdlMetadataUpdateComponent,
        resolve: {
            jdlMetadata: JdlMetadataResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'JdlMetadata'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'jdl-metadata/:id/edit',
        component: JdlMetadataUpdateComponent,
        resolve: {
            jdlMetadata: JdlMetadataResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'JdlMetadata'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const jdlMetadataPopupRoute: Routes = [
    {
        path: 'jdl-metadata/:id/delete',
        component: JdlMetadataDeletePopupComponent,
        resolve: {
            jdlMetadata: JdlMetadataResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'JdlMetadata'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
