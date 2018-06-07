/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { JhonlineTestModule } from '../../../test.module';
import { YoRCUpdateComponent } from 'app/entities/yo-rc/yo-rc-update.component';
import { YoRCService } from 'app/entities/yo-rc/yo-rc.service';
import { YoRC } from 'app/shared/model/yo-rc.model';

import { OwnerIdentityService } from 'app/entities/owner-identity';

describe('Component Tests', () => {
    describe('YoRC Management Update Component', () => {
        let comp: YoRCUpdateComponent;
        let fixture: ComponentFixture<YoRCUpdateComponent>;
        let service: YoRCService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhonlineTestModule],
                declarations: [YoRCUpdateComponent],
                providers: [OwnerIdentityService, YoRCService]
            })
                .overrideTemplate(YoRCUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(YoRCUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(YoRCService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new YoRC(123);
                    spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({ body: entity })));
                    comp.yoRC = entity;
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
                    const entity = new YoRC();
                    spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({ body: entity })));
                    comp.yoRC = entity;
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
