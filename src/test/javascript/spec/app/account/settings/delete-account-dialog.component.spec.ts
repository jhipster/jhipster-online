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
import { ComponentFixture, TestBed, waitForAsync, inject, fakeAsync } from '@angular/core/testing';

import { JhonlineTestModule } from '../../../test.module';
import { DeleteAccountDialogComponent } from 'app/account/settings/delete-account-dialog.component';

describe('Component Tests', () => {
  describe('User Management Delete Component', () => {
    // eslint-disable-next-line @typescript-eslint/no-unused-vars
    let comp: DeleteAccountDialogComponent;
    let fixture: ComponentFixture<DeleteAccountDialogComponent>;

    beforeEach(
      waitForAsync(() => {
        TestBed.configureTestingModule({
          imports: [JhonlineTestModule],
          declarations: [DeleteAccountDialogComponent]
        })
          .overrideTemplate(DeleteAccountDialogComponent, '')
          .compileComponents();
      })
    );

    beforeEach(() => {
      fixture = TestBed.createComponent(DeleteAccountDialogComponent);
      comp = fixture.componentInstance;
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          // WHEN
          // comp.confirmDelete();
          // tick();
          //
          // // THEN
          // expect(service.delete).toHaveBeenCalled();
          // expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          // expect(comp.showAlert).toEqual(true);
        })
      ));
    });
  });
});
