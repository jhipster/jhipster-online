import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhonlineTestModule } from '../../../test.module';
import { UserApplicationComponent } from 'app/entities/user-application/user-application.component';
import { UserApplicationService } from 'app/entities/user-application/user-application.service';
import { UserApplication } from 'app/shared/model/user-application.model';

describe('Component Tests', () => {
  describe('UserApplication Management Component', () => {
    let comp: UserApplicationComponent;
    let fixture: ComponentFixture<UserApplicationComponent>;
    let service: UserApplicationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhonlineTestModule],
        declarations: [UserApplicationComponent]
      })
        .overrideTemplate(UserApplicationComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserApplicationComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserApplicationService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new UserApplication(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.userApplications && comp.userApplications[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
