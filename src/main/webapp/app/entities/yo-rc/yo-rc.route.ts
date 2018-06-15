import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core';
import { YoRC } from 'app/shared/model/yo-rc.model';
import { YoRCService } from './yo-rc.service';
import { YoRCComponent } from './yo-rc.component';
import { YoRCDetailComponent } from './yo-rc-detail.component';
import { YoRCUpdateComponent } from './yo-rc-update.component';
import { YoRCDeletePopupComponent } from './yo-rc-delete-dialog.component';

@Injectable()
export class YoRCResolve implements Resolve<any> {
    constructor(private service: YoRCService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id);
        }
        return new YoRC();
    }
}

export const yoRCRoute: Routes = [
    {
        path: 'yo-rc',
        component: YoRCComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'YoRCS'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'yo-rc/:id/view',
        component: YoRCDetailComponent,
        resolve: {
            yoRC: YoRCResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'YoRCS'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'yo-rc/new',
        component: YoRCUpdateComponent,
        resolve: {
            yoRC: YoRCResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'YoRCS'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'yo-rc/:id/edit',
        component: YoRCUpdateComponent,
        resolve: {
            yoRC: YoRCResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'YoRCS'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const yoRCPopupRoute: Routes = [
    {
        path: 'yo-rc/:id/delete',
        component: YoRCDeletePopupComponent,
        resolve: {
            yoRC: YoRCResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'YoRCS'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
