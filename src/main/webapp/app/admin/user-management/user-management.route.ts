/**
 * Copyright 2017-2020 the original author or authors from the JHipster Online project.
 *
 * This file is part of the JHipster Online project, see https://github.com/jhipster/jhipster-online
 * for more information.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, Routes, CanActivate } from '@angular/router';

import { Observable, of } from 'rxjs';
import { JhiResolvePagingParams } from 'ng-jhipster';

import { UserManagementComponent } from './user-management.component';
import { IUser, User } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { Principal } from 'app/core/auth/principal.service';

@Injectable({ providedIn: 'root' })
export class UserResolve implements CanActivate {
  constructor(private principal: Principal) {}

  canActivate(): any {
    return this.principal.identity().then(() => this.principal.hasAnyAuthority(['ROLE_ADMIN']));
  }
}

@Injectable({ providedIn: 'root' })
export class UserManagementResolve implements Resolve<IUser> {
  constructor(private service: UserService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUser> {
    const id = route.params['login'];
    if (id) {
      return this.service.find(id);
    }
    return of(new User());
  }
}

export const userMgmtRoute: Routes = [
  {
    path: 'user-management',
    component: UserManagementComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      pageTitle: 'Users',
      defaultSort: 'id,asc'
    }
  },
  {
    path: 'user-management/:login/view',
    component: UserManagementComponent,
    resolve: {
      user: UserManagementResolve
    },
    data: {
      pageTitle: 'Users'
    }
  },
  {
    path: 'user-management/new',
    component: UserManagementComponent,
    resolve: {
      user: UserManagementResolve
    }
  },
  {
    path: 'user-management/:login/edit',
    component: UserManagementComponent,
    resolve: {
      user: UserManagementResolve
    }
  }
];
