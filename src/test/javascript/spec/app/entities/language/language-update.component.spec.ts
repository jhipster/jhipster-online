import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhonlineTestModule } from '../../../test.module';
import { LanguageUpdateComponent } from 'app/entities/language/language-update.component';
import { LanguageService } from 'app/entities/language/language.service';
import { Language } from 'app/shared/model/language.model';

describe('Component Tests', () => {
  describe('Language Management Update Component', () => {
    let comp: LanguageUpdateComponent;
    let fixture: ComponentFixture<LanguageUpdateComponent>;
    let service: LanguageService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhonlineTestModule],
        declarations: [LanguageUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(LanguageUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LanguageUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LanguageService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Language(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Language();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
