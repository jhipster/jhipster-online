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
import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

import { JdlMetadata } from './jdl-metadata.model';
import { JdlMetadataService } from './jdl-metadata.service';
import { Principal } from 'app/core/auth/principal.service';

@Component({
    selector: 'jhi-jdl-metadata',
    templateUrl: './jdl-metadata.component.html'
})
export class JdlMetadataComponent implements OnInit, OnDestroy {
    jdlMetadata: JdlMetadata[];
    currentAccount: any;
    eventSubscriber: Subscription;
    jdlRefresh: boolean;
    predicate: string;
    ascending: boolean;

    constructor(
        private jdlMetadataService: JdlMetadataService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
        this.predicate = 'name';
        this.ascending = true;
    }

    loadAll() {
        this.jdlRefresh = true;
        this.jdlMetadataService
            .query({
                sort: this.sort()
            })
            .subscribe(
                res => {
                    this.jdlMetadata = res;
                    this.jdlRefresh = false;
                },
                res => {
                    this.onError(res.json);
                    this.jdlRefresh = false;
                }
            );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInJdlMetadata();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: JdlMetadata) {
        return item.id;
    }

    registerChangeInJdlMetadata() {
        this.eventSubscriber = this.eventManager.subscribe('jdlMetadataListModification', () => this.loadAll());
    }

    sort(): string[] {
        return [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    }

    getSortingIcon(fieldName: string): 'sort' | 'sort-up' | 'sort-down' {
        if (fieldName === this.predicate) {
            return this.ascending ? 'sort-up' : 'sort-down';
        } else {
            return 'sort';
        }
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
