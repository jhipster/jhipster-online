import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { UserApplication } from 'app/shared/model/user-application.model';
import { UserApplicationService } from './user-application.service';
import { UserApplicationComponent } from './user-application.component';
import { UserApplicationDetailComponent } from './user-application-detail.component';
import { UserApplicationUpdateComponent } from './user-application-update.component';
import { UserApplicationDeletePopupComponent } from './user-application-delete-dialog.component';
import { IUserApplication } from 'app/shared/model/user-application.model';

@Injectable({ providedIn: 'root' })
export class UserApplicationResolve implements Resolve<IUserApplication> {
    constructor(private service: UserApplicationService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((userApplication: HttpResponse<UserApplication>) => userApplication.body));
        }
        return of(new UserApplication());
    }
}

export const userApplicationRoute: Routes = [
    {
        path: 'user-application',
        component: UserApplicationComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UserApplications'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'user-application/:id/view',
        component: UserApplicationDetailComponent,
        resolve: {
            userApplication: UserApplicationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UserApplications'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'user-application/new',
        component: UserApplicationUpdateComponent,
        resolve: {
            userApplication: UserApplicationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UserApplications'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'user-application/:id/edit',
        component: UserApplicationUpdateComponent,
        resolve: {
            userApplication: UserApplicationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UserApplications'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const userApplicationPopupRoute: Routes = [
    {
        path: 'user-application/:id/delete',
        component: UserApplicationDeletePopupComponent,
        resolve: {
            userApplication: UserApplicationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UserApplications'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
