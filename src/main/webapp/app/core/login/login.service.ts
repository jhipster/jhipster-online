/**
 * Copyright 2017-2023 the original author or authors from the JHipster project.
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
import { Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { Account } from 'app/core/user/account.model';
import { AccountService } from 'app/core/auth/account.service';
import { AuthServerProvider } from 'app/core/auth/auth-jwt.service';
import { Login } from './login.model';

@Injectable({ providedIn: 'root' })
export class LoginService {
  constructor(private accountService: AccountService, private authServerProvider: AuthServerProvider) {}

  login(credentials: Login): Observable<Account | null> {
    return this.authServerProvider.login(credentials).pipe(mergeMap(() => this.accountService.identity(true)));
  }

  logout(): void {
    this.authServerProvider.logout().subscribe({ complete: () => this.accountService.authenticate(null) });
  }
}
