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
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable, of } from 'rxjs';

import { JhonlineTestModule } from '../../../test.module';
import { UserMgmtUpdateComponent } from 'app/admin/user-management/user-management-update.component';
import { UserService, User } from 'app/core';

describe('Component Tests', () => {
    describe('User Management Update Component', () => {
        let comp: UserMgmtUpdateComponent;
        let fixture: ComponentFixture<UserMgmtUpdateComponent>;
        let service: UserService;
        const route = ({
            data: of({ user: new User(1, 'user', 'first', 'last', 'first@last.com', true, 'en', ['ROLE_USER'], 'admin', null, null, null) })
        } as any) as ActivatedRoute;

        beforeEach(
            async(() => {
                TestBed.configureTestingModule({
                    imports: [JhonlineTestModule],
                    declarations: [UserMgmtUpdateComponent],
                    providers: [
                        UserService,
                        {
                            provide: ActivatedRoute,
                            useValue: route
                        }
                    ]
                })
                    .overrideTemplate(UserMgmtUpdateComponent, '')
                    .compileComponents();
            })
        );

        beforeEach(() => {
            fixture = TestBed.createComponent(UserMgmtUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UserService);
        });

        describe('OnInit', () => {
            it(
                'Should load authorities and language on init',
                inject(
                    [],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'authorities').and.returnValue(of(['USER']));

                        // WHEN
                        comp.ngOnInit();

                        // THEN
                        expect(service.authorities).toHaveBeenCalled();
                        expect(comp.authorities).toEqual(['USER']);
                    })
                )
            );
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing user',
                inject(
                    [],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new User(123);
                        spyOn(service, 'update').and.returnValue(
                            of(
                                new HttpResponse({
                                    body: entity
                                })
                            )
                        );
                        comp.user = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                    })
                )
            );

            it(
                'Should call create service on save for new user',
                inject(
                    [],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new User();
                        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                        comp.user = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                    })
                )
            );
        });
    });
});
