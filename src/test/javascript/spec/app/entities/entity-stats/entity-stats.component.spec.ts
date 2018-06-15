/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhonlineTestModule } from '../../../test.module';
import { EntityStatsComponent } from 'app/entities/entity-stats/entity-stats.component';
import { EntityStatsService } from 'app/entities/entity-stats/entity-stats.service';
import { EntityStats } from 'app/shared/model/entity-stats.model';

describe('Component Tests', () => {
    describe('EntityStats Management Component', () => {
        let comp: EntityStatsComponent;
        let fixture: ComponentFixture<EntityStatsComponent>;
        let service: EntityStatsService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhonlineTestModule],
                declarations: [EntityStatsComponent],
                providers: [EntityStatsService]
            })
                .overrideTemplate(EntityStatsComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(EntityStatsComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EntityStatsService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                Observable.of(
                    new HttpResponse({
                        body: [new EntityStats(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.entityStats[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
