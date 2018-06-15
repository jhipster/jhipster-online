/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhonlineTestModule } from '../../../test.module';
import { YoRCComponent } from 'app/entities/yo-rc/yo-rc.component';
import { YoRCService } from 'app/entities/yo-rc/yo-rc.service';
import { YoRC } from 'app/shared/model/yo-rc.model';

describe('Component Tests', () => {
    describe('YoRC Management Component', () => {
        let comp: YoRCComponent;
        let fixture: ComponentFixture<YoRCComponent>;
        let service: YoRCService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhonlineTestModule],
                declarations: [YoRCComponent],
                providers: [YoRCService]
            })
                .overrideTemplate(YoRCComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(YoRCComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(YoRCService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                Observable.of(
                    new HttpResponse({
                        body: [new YoRC(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.yoRCS[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
