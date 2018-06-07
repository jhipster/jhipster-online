/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { JhonlineTestModule } from '../../../test.module';
import { TestFrameworkUpdateComponent } from 'app/entities/test-framework/test-framework-update.component';
import { TestFrameworkService } from 'app/entities/test-framework/test-framework.service';
import { TestFramework } from 'app/shared/model/test-framework.model';

import { YoRCService } from 'app/entities/yo-rc';

describe('Component Tests', () => {
    describe('TestFramework Management Update Component', () => {
        let comp: TestFrameworkUpdateComponent;
        let fixture: ComponentFixture<TestFrameworkUpdateComponent>;
        let service: TestFrameworkService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhonlineTestModule],
                declarations: [TestFrameworkUpdateComponent],
                providers: [YoRCService, TestFrameworkService]
            })
                .overrideTemplate(TestFrameworkUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TestFrameworkUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TestFrameworkService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new TestFramework(123);
                    spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({ body: entity })));
                    comp.testFramework = entity;
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
                    const entity = new TestFramework();
                    spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({ body: entity })));
                    comp.testFramework = entity;
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
