import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhonlineTestModule } from '../../../test.module';
import { LanguageComponent } from 'app/entities/language/language.component';
import { LanguageService } from 'app/entities/language/language.service';
import { Language } from 'app/shared/model/language.model';

describe('Component Tests', () => {
  describe('Language Management Component', () => {
    let comp: LanguageComponent;
    let fixture: ComponentFixture<LanguageComponent>;
    let service: LanguageService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhonlineTestModule],
        declarations: [LanguageComponent]
      })
        .overrideTemplate(LanguageComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LanguageComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LanguageService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Language(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.languages && comp.languages[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
