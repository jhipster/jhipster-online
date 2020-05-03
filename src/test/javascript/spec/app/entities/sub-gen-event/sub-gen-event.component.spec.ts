/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhonlineTestModule } from '../../../test.module';
import { SubGenEventComponent } from 'app/entities/sub-gen-event/sub-gen-event.component';
import { SubGenEventService } from 'app/entities/sub-gen-event/sub-gen-event.service';
import { SubGenEvent } from 'app/shared/model/sub-gen-event.model';

describe('Component Tests', () => {
    describe('SubGenEvent Management Component', () => {
        let comp: SubGenEventComponent;
        let fixture: ComponentFixture<SubGenEventComponent>;
        let service: SubGenEventService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhonlineTestModule],
                declarations: [SubGenEventComponent],
                providers: []
            })
                .overrideTemplate(SubGenEventComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SubGenEventComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SubGenEventService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new SubGenEvent(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.subGenEvents[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
