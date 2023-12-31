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
import { waitForAsync, ComponentFixture, TestBed } from '@angular/core/testing';

import { PasswordResetService } from 'app/core/auth/password-reset.service';
import { UserMgmtResetDialogComponent } from 'app/admin/user-management/user-management-reset-dialog.component';
import { JhonlineTestModule } from '../../../test.module';

import { of } from 'rxjs';

describe('Component Tests', () => {
  describe('User Management Reset Component', () => {
    let comp: UserMgmtResetDialogComponent;
    let fixture: ComponentFixture<UserMgmtResetDialogComponent>;
    let service: PasswordResetService;

    beforeEach(
      waitForAsync(() => {
        TestBed.configureTestingModule({
          imports: [JhonlineTestModule],
          declarations: [UserMgmtResetDialogComponent],
          providers: [PasswordResetService]
        })
          .overrideTemplate(UserMgmtResetDialogComponent, '')
          .compileComponents();
      })
    );

    beforeEach(() => {
      fixture = TestBed.createComponent(UserMgmtResetDialogComponent);
      service = fixture.debugElement.injector.get(PasswordResetService);
      comp = fixture.componentInstance;

      spyOn(service, 'getResetLink').and.returnValue(of('https://www.jhipster.tech/'));
    });

    it('should define its initial state', () => {
      expect(comp.showClipboardSuccess).toEqual(false);
      expect(comp.showClipboardError).toEqual(false);
      expect(comp.isGeneratingLink).toEqual(false);
      expect(comp.resetLink).toEqual('');
    });

    it('should generate a reset link', () => {
      comp.generateResetLink('real@email.com');
      expect(comp.resetLink).toEqual('https://www.jhipster.tech/');
    });
  });
});
