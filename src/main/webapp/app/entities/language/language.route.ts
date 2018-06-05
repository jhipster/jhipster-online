import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core';
import { Language } from 'app/shared/model/language.model';
import { LanguageService } from './language.service';
import { LanguageComponent } from './language.component';
import { LanguageDetailComponent } from './language-detail.component';
import { LanguageUpdateComponent } from './language-update.component';
import { LanguageDeletePopupComponent } from './language-delete-dialog.component';

@Injectable()
export class LanguageResolve implements Resolve<any> {
    constructor(private service: LanguageService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id);
        }
        return new Language();
    }
}

export const languageRoute: Routes = [
    {
        path: 'language',
        component: LanguageComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Languages'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'language/:id/view',
        component: LanguageDetailComponent,
        resolve: {
            language: LanguageResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Languages'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'language/new',
        component: LanguageUpdateComponent,
        resolve: {
            language: LanguageResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Languages'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'language/:id/edit',
        component: LanguageUpdateComponent,
        resolve: {
            language: LanguageResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Languages'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const languagePopupRoute: Routes = [
    {
        path: 'language/:id/delete',
        component: LanguageDeletePopupComponent,
        resolve: {
            language: LanguageResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Languages'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
