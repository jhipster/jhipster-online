import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhonlineTestModule } from '../../../test.module';
import { YoRCDetailComponent } from 'app/entities/yo-rc/yo-rc-detail.component';
import { YoRC } from 'app/shared/model/yo-rc.model';

describe('Component Tests', () => {
  describe('YoRC Management Detail Component', () => {
    let comp: YoRCDetailComponent;
    let fixture: ComponentFixture<YoRCDetailComponent>;
    const route = ({ data: of({ yoRC: new YoRC(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhonlineTestModule],
        declarations: [YoRCDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(YoRCDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(YoRCDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load yoRC on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.yoRC).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
