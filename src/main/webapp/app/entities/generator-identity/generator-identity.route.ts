import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core';
import { GeneratorIdentity } from 'app/shared/model/generator-identity.model';
import { GeneratorIdentityService } from './generator-identity.service';
import { GeneratorIdentityComponent } from './generator-identity.component';
import { GeneratorIdentityDetailComponent } from './generator-identity-detail.component';
import { GeneratorIdentityUpdateComponent } from './generator-identity-update.component';
import { GeneratorIdentityDeletePopupComponent } from './generator-identity-delete-dialog.component';

@Injectable()
export class GeneratorIdentityResolve implements Resolve<any> {
    constructor(private service: GeneratorIdentityService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id);
        }
        return new GeneratorIdentity();
    }
}

export const generatorIdentityRoute: Routes = [
    {
        path: 'generator-identity',
        component: GeneratorIdentityComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'GeneratorIdentities'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'generator-identity/:id/view',
        component: GeneratorIdentityDetailComponent,
        resolve: {
            generatorIdentity: GeneratorIdentityResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'GeneratorIdentities'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'generator-identity/new',
        component: GeneratorIdentityUpdateComponent,
        resolve: {
            generatorIdentity: GeneratorIdentityResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'GeneratorIdentities'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'generator-identity/:id/edit',
        component: GeneratorIdentityUpdateComponent,
        resolve: {
            generatorIdentity: GeneratorIdentityResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'GeneratorIdentities'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const generatorIdentityPopupRoute: Routes = [
    {
        path: 'generator-identity/:id/delete',
        component: GeneratorIdentityDeletePopupComponent,
        resolve: {
            generatorIdentity: GeneratorIdentityResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'GeneratorIdentities'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
