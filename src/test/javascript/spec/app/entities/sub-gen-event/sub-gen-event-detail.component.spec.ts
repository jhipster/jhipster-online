/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs/observable/of';

import { JhonlineTestModule } from '../../../test.module';
import { SubGenEventDetailComponent } from 'app/entities/sub-gen-event/sub-gen-event-detail.component';
import { SubGenEvent } from 'app/shared/model/sub-gen-event.model';

describe('Component Tests', () => {
    describe('SubGenEvent Management Detail Component', () => {
        let comp: SubGenEventDetailComponent;
        let fixture: ComponentFixture<SubGenEventDetailComponent>;
        const route = ({ data: of({ subGenEvent: new SubGenEvent(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhonlineTestModule],
                declarations: [SubGenEventDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(SubGenEventDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SubGenEventDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.subGenEvent).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
