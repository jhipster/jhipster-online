import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { YoRC } from 'app/shared/model/yo-rc.model';
import { YoRCService } from './yo-rc.service';
import { YoRCComponent } from './yo-rc.component';
import { YoRCDetailComponent } from './yo-rc-detail.component';
import { YoRCUpdateComponent } from './yo-rc-update.component';
import { YoRCDeletePopupComponent } from './yo-rc-delete-dialog.component';
import { IYoRC } from 'app/shared/model/yo-rc.model';

@Injectable({ providedIn: 'root' })
export class YoRCResolve implements Resolve<IYoRC> {
    constructor(private service: YoRCService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((yoRC: HttpResponse<YoRC>) => yoRC.body));
        }
        return of(new YoRC());
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
