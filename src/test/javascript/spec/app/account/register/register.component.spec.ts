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
import { ComponentFixture, TestBed, async, inject, tick, fakeAsync } from '@angular/core/testing';
import { Observable, of, throwError } from 'rxjs';

import { JhonlineTestModule } from '../../../test.module';
import { EMAIL_ALREADY_USED_TYPE, LOGIN_ALREADY_USED_TYPE } from 'app/shared';
import { Register } from 'app/account/register/register.service';
import { RegisterComponent } from 'app/account/register/register.component';
import { PasswordResetService } from 'app/core';

describe('Component Tests', () => {
    describe('RegisterComponent', () => {
        let fixture: ComponentFixture<RegisterComponent>;
        let comp: RegisterComponent;

        beforeEach(
            async(() => {
                TestBed.configureTestingModule({
                    imports: [JhonlineTestModule],
                    declarations: [RegisterComponent],
                    providers: [Register, PasswordResetService]
                })
                    .overrideTemplate(RegisterComponent, '')
                    .compileComponents();
            })
        );

        beforeEach(() => {
            fixture = TestBed.createComponent(RegisterComponent);
            comp = fixture.componentInstance;
            comp.ngOnInit();
        });

        it('should ensure the two passwords entered match', () => {
            comp.registerAccount.password = 'password';
            comp.confirmPassword = 'non-matching';

            comp.register();

            expect(comp.doNotMatch).toEqual('ERROR');
        });

        it(
            'should update success to OK after creating an account',
            inject(
                [Register],
                fakeAsync((service: Register) => {
                    spyOn(service, 'save').and.returnValue(of({}));
                    comp.registerAccount.password = comp.confirmPassword = 'password';

                    comp.register();
                    tick();

                    expect(service.save).toHaveBeenCalledWith({
                        password: 'password',
                        langKey: 'en'
                    });
                    expect(comp.success).toEqual(true);
                    expect(comp.registerAccount.langKey).toEqual('en');
                    expect(comp.errorUserExists).toBeNull();
                    expect(comp.errorEmailExists).toBeNull();
                    expect(comp.error).toBeNull();
                })
            )
        );

        it(
            'should notify of user existence upon 400/login already in use',
            inject(
                [Register],
                fakeAsync((service: Register) => {
                    spyOn(service, 'save').and.returnValue(
                        throwError({
                            status: 400,
                            error: { type: LOGIN_ALREADY_USED_TYPE }
                        })
                    );
                    comp.registerAccount.password = comp.confirmPassword = 'password';

                    comp.register();
                    tick();

                    expect(comp.errorUserExists).toEqual('ERROR');
                    expect(comp.errorEmailExists).toBeNull();
                    expect(comp.error).toBeNull();
                })
            )
        );

        it(
            'should notify of email existence upon 400/email address already in use',
            inject(
                [Register],
                fakeAsync((service: Register) => {
                    spyOn(service, 'save').and.returnValue(
                        throwError({
                            status: 400,
                            error: { type: EMAIL_ALREADY_USED_TYPE }
                        })
                    );
                    comp.registerAccount.password = comp.confirmPassword = 'password';

                    comp.register();
                    tick();

                    expect(comp.errorEmailExists).toEqual('ERROR');
                    expect(comp.errorUserExists).toBeNull();
                    expect(comp.error).toBeNull();
                })
            )
        );

        it(
            'should notify of generic error',
            inject(
                [Register],
                fakeAsync((service: Register) => {
                    spyOn(service, 'save').and.returnValue(
                        throwError({
                            status: 503
                        })
                    );
                    comp.registerAccount.password = comp.confirmPassword = 'password';

                    comp.register();
                    tick();

                    expect(comp.errorUserExists).toBeNull();
                    expect(comp.errorEmailExists).toBeNull();
                    expect(comp.error).toEqual('ERROR');
                })
            )
        );
    });
});
