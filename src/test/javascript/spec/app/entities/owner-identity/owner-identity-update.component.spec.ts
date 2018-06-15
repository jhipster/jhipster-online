/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { JhonlineTestModule } from '../../../test.module';
import { OwnerIdentityUpdateComponent } from 'app/entities/owner-identity/owner-identity-update.component';
import { OwnerIdentityService } from 'app/entities/owner-identity/owner-identity.service';
import { OwnerIdentity } from 'app/shared/model/owner-identity.model';

import { UserService } from 'app/core';

describe('Component Tests', () => {
    describe('OwnerIdentity Management Update Component', () => {
        let comp: OwnerIdentityUpdateComponent;
        let fixture: ComponentFixture<OwnerIdentityUpdateComponent>;
        let service: OwnerIdentityService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhonlineTestModule],
                declarations: [OwnerIdentityUpdateComponent],
                providers: [UserService, OwnerIdentityService]
            })
                .overrideTemplate(OwnerIdentityUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(OwnerIdentityUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OwnerIdentityService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new OwnerIdentity(123);
                    spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({ body: entity })));
                    comp.ownerIdentity = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new OwnerIdentity();
                    spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({ body: entity })));
                    comp.ownerIdentity = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
