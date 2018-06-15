import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core';
import { OwnerIdentity } from 'app/shared/model/owner-identity.model';
import { OwnerIdentityService } from './owner-identity.service';
import { OwnerIdentityComponent } from './owner-identity.component';
import { OwnerIdentityDetailComponent } from './owner-identity-detail.component';
import { OwnerIdentityUpdateComponent } from './owner-identity-update.component';
import { OwnerIdentityDeletePopupComponent } from './owner-identity-delete-dialog.component';

@Injectable()
export class OwnerIdentityResolve implements Resolve<any> {
    constructor(private service: OwnerIdentityService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id);
        }
        return new OwnerIdentity();
    }
}

export const ownerIdentityRoute: Routes = [
    {
        path: 'owner-identity',
        component: OwnerIdentityComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OwnerIdentities'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'owner-identity/:id/view',
        component: OwnerIdentityDetailComponent,
        resolve: {
            ownerIdentity: OwnerIdentityResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OwnerIdentities'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'owner-identity/new',
        component: OwnerIdentityUpdateComponent,
        resolve: {
            ownerIdentity: OwnerIdentityResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OwnerIdentities'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'owner-identity/:id/edit',
        component: OwnerIdentityUpdateComponent,
        resolve: {
            ownerIdentity: OwnerIdentityResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OwnerIdentities'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const ownerIdentityPopupRoute: Routes = [
    {
        path: 'owner-identity/:id/delete',
        component: OwnerIdentityDeletePopupComponent,
        resolve: {
            ownerIdentity: OwnerIdentityResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OwnerIdentities'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
