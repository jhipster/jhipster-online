/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { JhonlineTestModule } from '../../../test.module';
import { EntityStatsUpdateComponent } from 'app/entities/entity-stats/entity-stats-update.component';
import { EntityStatsService } from 'app/entities/entity-stats/entity-stats.service';
import { EntityStats } from 'app/shared/model/entity-stats.model';

describe('Component Tests', () => {
    describe('EntityStats Management Update Component', () => {
        let comp: EntityStatsUpdateComponent;
        let fixture: ComponentFixture<EntityStatsUpdateComponent>;
        let service: EntityStatsService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhonlineTestModule],
                declarations: [EntityStatsUpdateComponent]
            })
                .overrideTemplate(EntityStatsUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(EntityStatsUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EntityStatsService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new EntityStats(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.entityStats = entity;
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
                    const entity = new EntityStats();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.entityStats = entity;
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
