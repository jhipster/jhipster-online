/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs/observable/of';

import { JhonlineTestModule } from '../../../test.module';
import { TestFrameworkDetailComponent } from 'app/entities/test-framework/test-framework-detail.component';
import { TestFramework } from 'app/shared/model/test-framework.model';

describe('Component Tests', () => {
    describe('TestFramework Management Detail Component', () => {
        let comp: TestFrameworkDetailComponent;
        let fixture: ComponentFixture<TestFrameworkDetailComponent>;
        const route = ({ data: of({ testFramework: new TestFramework(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhonlineTestModule],
                declarations: [TestFrameworkDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(TestFrameworkDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TestFrameworkDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.testFramework).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
