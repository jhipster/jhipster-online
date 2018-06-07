import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core';
import { TestFramework } from 'app/shared/model/test-framework.model';
import { TestFrameworkService } from './test-framework.service';
import { TestFrameworkComponent } from './test-framework.component';
import { TestFrameworkDetailComponent } from './test-framework-detail.component';
import { TestFrameworkUpdateComponent } from './test-framework-update.component';
import { TestFrameworkDeletePopupComponent } from './test-framework-delete-dialog.component';

@Injectable()
export class TestFrameworkResolve implements Resolve<any> {
    constructor(private service: TestFrameworkService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id);
        }
        return new TestFramework();
    }
}

export const testFrameworkRoute: Routes = [
    {
        path: 'test-framework',
        component: TestFrameworkComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TestFrameworks'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'test-framework/:id/view',
        component: TestFrameworkDetailComponent,
        resolve: {
            testFramework: TestFrameworkResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TestFrameworks'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'test-framework/new',
        component: TestFrameworkUpdateComponent,
        resolve: {
            testFramework: TestFrameworkResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TestFrameworks'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'test-framework/:id/edit',
        component: TestFrameworkUpdateComponent,
        resolve: {
            testFramework: TestFrameworkResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TestFrameworks'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const testFrameworkPopupRoute: Routes = [
    {
        path: 'test-framework/:id/delete',
        component: TestFrameworkDeletePopupComponent,
        resolve: {
            testFramework: TestFrameworkResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TestFrameworks'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
