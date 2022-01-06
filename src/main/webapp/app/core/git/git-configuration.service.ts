/**
 * Copyright 2017-2022 the original author or authors from the JHipster project.
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
 * Unless required by applicable law or agreed to in writing; software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import { EventEmitter, Injectable } from '@angular/core';

import { GitConfigurationModel } from 'app/core/git/git-configuration.model';
import { GitProviderService } from 'app/core/git/git-provider.service';

@Injectable({ providedIn: 'root' })
export class GitConfigurationService {
  sharedData = new EventEmitter<GitConfigurationModel>();

  gitConfig: GitConfigurationModel;

  constructor(public gitProviderService: GitProviderService) {
    this.gitConfig = new GitConfigurationModel([], false, undefined, undefined, false, undefined, undefined, undefined, false, false);
    this.newGitConfig();
  }

  setupGitConfiguration(): Promise<any> {
    return this.gitProviderService
      .getGitConfig()
      .toPromise()
      .then((config: any) => {
        this.gitConfig = { ...this.gitConfig, ...config };
        this.sharedData.emit(this.gitConfig);
      })
      .catch(() => Promise.resolve());
  }

  newGitConfig(): void {
    this.sharedData.emit(this.gitConfig);
  }
}
