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
import { Component, OnDestroy, OnInit } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Subscription } from 'rxjs';
import { mergeMap, map } from 'rxjs/operators';
import { ActivatedRoute, Router } from '@angular/router';
import { JhiEventManager } from 'ng-jhipster';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/user/account.model';
import { UserService } from 'app/core/user/user.service';
import { User } from 'app/core/user/user.model';
import { UserManagementDeleteDialogComponent } from './user-management-delete-dialog.component';
import { UserMgmtResetDialogComponent } from 'app/admin/user-management/user-management-reset-dialog.component';

@Component({
  selector: 'jhi-user-mgmt',
  templateUrl: './user-management.component.html'
})
export class UserManagementComponent implements OnInit, OnDestroy {
  currentAccount: Account | null = null;
  users: User[] | null = null;
  userListSubscription?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  previousPage!: number;
  ascending!: boolean;

  constructor(
    private userService: UserService,
    private accountService: AccountService,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private eventManager: JhiEventManager,
    private modalService: NgbModal
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data
      .pipe(
        mergeMap(data =>
          this.accountService.identity().pipe(
            map(account => {
              this.page = data.pagingParams.page;
              this.previousPage = data.pagingParams.page;
              this.ascending = data.pagingParams.ascending;
              this.predicate = data.pagingParams.predicate;
              this.currentAccount = account;
              this.loadAll();
              this.userListSubscription = this.eventManager.subscribe('userListModification', () => this.loadAll());
            })
          )
        )
      )
      .subscribe();
  }

  ngOnDestroy(): void {
    if (this.userListSubscription) {
      this.eventManager.destroy(this.userListSubscription);
    }
  }

  setActive(user: User, isActivated: boolean): void {
    this.userService.update({ ...user, activated: isActivated }).subscribe(() => this.loadAll());
  }

  trackIdentity(index: number, item: User): any {
    return item.id;
  }

  loadPage(page: number): void {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  transition(): void {
    this.router.navigate(['./'], {
      relativeTo: this.activatedRoute.parent,
      queryParams: {
        page: this.page,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.loadAll();
  }

  deleteUser(user: User): void {
    const modalRef = this.modalService.open(UserManagementDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.user = user;
  }

  resetPassword(user: User): void {
    const modalRef = this.modalService.open(UserMgmtResetDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.user = user;
  }

  private loadAll(): void {
    this.userService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<User[]>) => this.onSuccess(res.body, res.headers));
  }

  private sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  private onSuccess(users: User[] | null, headers: HttpHeaders): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.users = users;
  }
}
