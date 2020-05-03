import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { SubGenEvent } from 'app/shared/model/sub-gen-event.model';
import { SubGenEventService } from './sub-gen-event.service';
import { SubGenEventComponent } from './sub-gen-event.component';
import { SubGenEventDetailComponent } from './sub-gen-event-detail.component';
import { SubGenEventUpdateComponent } from './sub-gen-event-update.component';
import { SubGenEventDeletePopupComponent } from './sub-gen-event-delete-dialog.component';
import { ISubGenEvent } from 'app/shared/model/sub-gen-event.model';

@Injectable({ providedIn: 'root' })
export class SubGenEventResolve implements Resolve<ISubGenEvent> {
    constructor(private service: SubGenEventService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((subGenEvent: HttpResponse<SubGenEvent>) => subGenEvent.body));
        }
        return of(new SubGenEvent());
    }
}

export const subGenEventRoute: Routes = [
    {
        path: 'sub-gen-event',
        component: SubGenEventComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SubGenEvents'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'sub-gen-event/:id/view',
        component: SubGenEventDetailComponent,
        resolve: {
            subGenEvent: SubGenEventResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SubGenEvents'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'sub-gen-event/new',
        component: SubGenEventUpdateComponent,
        resolve: {
            subGenEvent: SubGenEventResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SubGenEvents'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'sub-gen-event/:id/edit',
        component: SubGenEventUpdateComponent,
        resolve: {
            subGenEvent: SubGenEventResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SubGenEvents'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const subGenEventPopupRoute: Routes = [
    {
        path: 'sub-gen-event/:id/delete',
        component: SubGenEventDeletePopupComponent,
        resolve: {
            subGenEvent: SubGenEventResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SubGenEvents'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
