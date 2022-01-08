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
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/user/account.model';
import { DeleteAccountDialogComponent } from 'app/account/settings/delete-account-dialog.component';

@Component({
  selector: 'jhi-settings',
  templateUrl: './settings.component.html'
})
export class SettingsComponent implements OnInit {
  account!: Account;
  success = false;
  settingsForm = this.fb.group({
    firstName: [undefined, [Validators.required, Validators.minLength(1), Validators.maxLength(50)]],
    lastName: [undefined, [Validators.required, Validators.minLength(1), Validators.maxLength(50)]],
    email: [undefined, [Validators.required, Validators.minLength(5), Validators.maxLength(254), Validators.email]]
  });

  constructor(private accountService: AccountService, private fb: FormBuilder, private modalService: NgbModal) {}

  ngOnInit(): void {
    this.accountService.identity().subscribe(account => {
      if (account) {
        this.settingsForm.patchValue({
          firstName: account.firstName,
          lastName: account.lastName,
          email: account.email
        });

        this.account = account;
      }
    });
  }
  deleteAccount(): void {
    this.modalService.open(DeleteAccountDialogComponent, { size: 'lg', backdrop: 'static' });
  }
  save(): void {
    this.success = false;

    this.account.firstName = this.settingsForm.get('firstName')!.value;
    this.account.lastName = this.settingsForm.get('lastName')!.value;
    this.account.email = this.settingsForm.get('email')!.value;

    this.accountService.save(this.account).subscribe(() => {
      this.success = true;

      this.accountService.authenticate(this.account);
    });
  }
}
