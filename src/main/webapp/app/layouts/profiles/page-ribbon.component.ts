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
import { Component, OnInit } from '@angular/core';
import { ProfileService } from './profile.service';
import { ProfileInfo } from 'app/layouts/profiles/profile-info.model';

@Component({
  selector: 'jhi-page-ribbon',
  template: `
    <div class="ribbon" *ngIf="ribbonEnv">
      <a href="">{{ ribbonEnv }}</a>
    </div>
  `,
  styleUrls: ['page-ribbon.scss']
})
export class PageRibbonComponent implements OnInit {
  profileInfo: ProfileInfo;
  ribbonEnv: string;

  constructor(private profileService: ProfileService) {}

  ngOnInit(): void {
    this.profileService.getProfileInfo().subscribe((profileInfo: any) => {
      this.profileInfo = profileInfo;
      this.ribbonEnv = profileInfo.ribbonEnv;
    });
  }
}
