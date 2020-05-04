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
import { Router } from '@angular/router';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { timer } from 'rxjs/observable/timer';
import { AccountService } from 'app/core/auth/account.service';
import { Principal } from 'app/core/auth/principal.service';

@Component({
  selector: 'jhi-account-delete-dialog',
  templateUrl: './delete-account-dialog.component.html'
})
export class DeleteAccountDialogComponent implements OnInit {
  showAlert?: boolean;

  constructor(private principal: Principal, public account: AccountService, public activeModal: NgbActiveModal, private router: Router) {}

  ngOnInit(): void {
    this.showAlert = false;
  }

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(): void {
    this.account.delete().subscribe(() => {
      this.principal.identity().then(() => {
        this.principal.authenticate(null);
        this.redirectToHomepage();
      });
    });
  }

  redirectToHomepage(): void {
    this.showAlert = true;
    timer(2000).subscribe(() => {
      this.activeModal.close();
      this.router.navigate(['/']);
    });
  }
}
