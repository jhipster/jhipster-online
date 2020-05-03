import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhonlineTestModule } from '../../../test.module';
import { EntityStatsDetailComponent } from 'app/entities/entity-stats/entity-stats-detail.component';
import { EntityStats } from 'app/shared/model/entity-stats.model';

describe('Component Tests', () => {
  describe('EntityStats Management Detail Component', () => {
    let comp: EntityStatsDetailComponent;
    let fixture: ComponentFixture<EntityStatsDetailComponent>;
    const route = ({ data: of({ entityStats: new EntityStats(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhonlineTestModule],
        declarations: [EntityStatsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(EntityStatsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EntityStatsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load entityStats on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.entityStats).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
