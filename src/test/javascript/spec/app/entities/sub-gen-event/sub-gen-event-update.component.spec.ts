/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { JhonlineTestModule } from '../../../test.module';
import { SubGenEventUpdateComponent } from 'app/entities/sub-gen-event/sub-gen-event-update.component';
import { SubGenEventService } from 'app/entities/sub-gen-event/sub-gen-event.service';
import { SubGenEvent } from 'app/shared/model/sub-gen-event.model';

import { OwnerIdentityService } from 'app/entities/owner-identity';

describe('Component Tests', () => {
    describe('SubGenEvent Management Update Component', () => {
        let comp: SubGenEventUpdateComponent;
        let fixture: ComponentFixture<SubGenEventUpdateComponent>;
        let service: SubGenEventService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhonlineTestModule],
                declarations: [SubGenEventUpdateComponent],
                providers: [OwnerIdentityService, SubGenEventService]
            })
                .overrideTemplate(SubGenEventUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SubGenEventUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SubGenEventService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new SubGenEvent(123);
                    spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({ body: entity })));
                    comp.subGenEvent = entity;
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
                    const entity = new SubGenEvent();
                    spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({ body: entity })));
                    comp.subGenEvent = entity;
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
