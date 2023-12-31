/**
 * Copyright 2017-2024 the original author or authors from the JHipster project.
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
import { Component } from '@angular/core';

import { GitConfigurationModel } from 'app/core/git/git-configuration.model';
import { GitConfigurationService } from 'app/core/git/git-configuration.service';
import { LoginModalService } from 'app/core/login/login-modal.service';
import { AccountService } from 'app/core/auth/account.service';

@Component({
  selector: 'jhi-welcome',
  templateUrl: './welcome.component.html',
  styleUrls: ['welcome.scss']
})
export class WelcomeComponent {
  gitConfig: GitConfigurationModel;

  constructor(
    private accountService: AccountService,
    private gitConfigurationService: GitConfigurationService,
    private loginModalService: LoginModalService
  ) {
    this.gitConfig = this.gitConfigurationService.gitConfig;
  }

  login(): void {
    this.loginModalService.open();
  }

  isAuthenticated(): boolean {
    return this.accountService.isAuthenticated();
  }
}
