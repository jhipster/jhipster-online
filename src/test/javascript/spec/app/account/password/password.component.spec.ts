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
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of, throwError } from 'rxjs';

import { JhonlineTestModule } from '../../../test.module';
import { PasswordComponent } from 'app/account/password/password.component';
import { PasswordService } from 'app/account/password/password.service';
import { Principal } from 'app/core/auth/principal.service';
import { AccountService } from 'app/core/auth/account.service';

describe('Component Tests', () => {
    describe('PasswordComponent', () => {
        let comp: PasswordComponent;
        let fixture: ComponentFixture<PasswordComponent>;
        let service: PasswordService;

        beforeEach(
            async(() => {
                TestBed.configureTestingModule({
                    imports: [JhonlineTestModule],
                    declarations: [PasswordComponent],
                    providers: [Principal, AccountService, PasswordService]
                })
                    .overrideTemplate(PasswordComponent, '')
                    .compileComponents();
            })
        );

        beforeEach(() => {
            fixture = TestBed.createComponent(PasswordComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PasswordService);
        });

        it('should show error if passwords do not match', () => {
            // GIVEN
            comp.newPassword = 'password1';
            comp.confirmPassword = 'password2';
            // WHEN
            comp.changePassword();
            // THEN
            expect(comp.doNotMatch).toBe('ERROR');
            expect(comp.error).toBeNull();
            expect(comp.success).toBeNull();
        });

        it('should call Auth.changePassword when passwords match', () => {
            // GIVEN
            const passwordValues = {
                currentPassword: 'oldPassword',
                newPassword: 'myPassword'
            };

            spyOn(service, 'save').and.returnValue(of(new HttpResponse({ body: true })));
            comp.currentPassword = passwordValues.currentPassword;
            comp.newPassword = comp.confirmPassword = passwordValues.newPassword;

            // WHEN
            comp.changePassword();

            // THEN
            expect(service.save).toHaveBeenCalledWith(passwordValues.newPassword, passwordValues.currentPassword);
        });

        it('should set success to OK upon success', function() {
            // GIVEN
            spyOn(service, 'save').and.returnValue(of(new HttpResponse({ body: true })));
            comp.newPassword = comp.confirmPassword = 'myPassword';

            // WHEN
            comp.changePassword();

            // THEN
            expect(comp.doNotMatch).toBeNull();
            expect(comp.error).toBeNull();
            expect(comp.success).toBe('OK');
        });

        it('should notify of error if change password fails', function() {
            // GIVEN
            spyOn(service, 'save').and.returnValue(throwError('ERROR'));
            comp.newPassword = comp.confirmPassword = 'myPassword';

            // WHEN
            comp.changePassword();

            // THEN
            expect(comp.doNotMatch).toBeNull();
            expect(comp.success).toBeNull();
            expect(comp.error).toBe('ERROR');
        });
    });
});
