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
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhonlineTestModule } from '../../../test.module';
import { UserMgmtDetailComponent } from 'app/admin/user-management/user-management-detail.component';
import { User, PasswordResetService } from 'app/core';

describe('Component Tests', () => {
    describe('User Management Detail Component', () => {
        let comp: UserMgmtDetailComponent;
        let fixture: ComponentFixture<UserMgmtDetailComponent>;
        const route = ({
            data: of({ user: new User(1, 'user', 'first', 'last', 'first@last.com', true, 'en', ['ROLE_USER'], 'admin', null, null, null) })
        } as any) as ActivatedRoute;

        beforeEach(
            async(() => {
                TestBed.configureTestingModule({
                    imports: [JhonlineTestModule],
                    declarations: [UserMgmtDetailComponent],
                    providers: [
                        {
                            provide: ActivatedRoute,
                            useValue: route
                        },
                        PasswordResetService
                    ]
                })
                    .overrideTemplate(UserMgmtDetailComponent, '')
                    .compileComponents();
            })
        );

        beforeEach(() => {
            fixture = TestBed.createComponent(UserMgmtDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.user).toEqual(
                    jasmine.objectContaining({
                        id: 1,
                        login: 'user',
                        firstName: 'first',
                        lastName: 'last',
                        email: 'first@last.com',
                        activated: true,
                        langKey: 'en',
                        authorities: ['ROLE_USER'],
                        createdBy: 'admin',
                        createdDate: null,
                        lastModifiedBy: null,
                        lastModifiedDate: null,
                        password: null
                    })
                );
            });
        });
    });
});
