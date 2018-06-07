/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhonlineTestModule } from '../../../test.module';
import { TestFrameworkComponent } from 'app/entities/test-framework/test-framework.component';
import { TestFrameworkService } from 'app/entities/test-framework/test-framework.service';
import { TestFramework } from 'app/shared/model/test-framework.model';

describe('Component Tests', () => {
    describe('TestFramework Management Component', () => {
        let comp: TestFrameworkComponent;
        let fixture: ComponentFixture<TestFrameworkComponent>;
        let service: TestFrameworkService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhonlineTestModule],
                declarations: [TestFrameworkComponent],
                providers: [TestFrameworkService]
            })
                .overrideTemplate(TestFrameworkComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TestFrameworkComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TestFrameworkService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                Observable.of(
                    new HttpResponse({
                        body: [new TestFramework(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.testFrameworks[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
