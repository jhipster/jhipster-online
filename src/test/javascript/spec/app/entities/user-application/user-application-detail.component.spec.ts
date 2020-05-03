import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { JhonlineTestModule } from '../../../test.module';
import { UserApplicationDetailComponent } from 'app/entities/user-application/user-application-detail.component';
import { UserApplication } from 'app/shared/model/user-application.model';

describe('Component Tests', () => {
  describe('UserApplication Management Detail Component', () => {
    let comp: UserApplicationDetailComponent;
    let fixture: ComponentFixture<UserApplicationDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ userApplication: new UserApplication(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhonlineTestModule],
        declarations: [UserApplicationDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(UserApplicationDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UserApplicationDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load userApplication on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.userApplication).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });

    describe('byteSize', () => {
      it('Should call byteSize from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'byteSize');
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.byteSize(fakeBase64);

        // THEN
        expect(dataUtils.byteSize).toBeCalledWith(fakeBase64);
      });
    });

    describe('openFile', () => {
      it('Should call openFile from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'openFile');
        const fakeContentType = 'fake content type';
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.openFile(fakeContentType, fakeBase64);

        // THEN
        expect(dataUtils.openFile).toBeCalledWith(fakeContentType, fakeBase64);
      });
    });
  });
});
