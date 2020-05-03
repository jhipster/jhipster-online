import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhonlineTestModule } from '../../../test.module';
import { GeneratorIdentityUpdateComponent } from 'app/entities/generator-identity/generator-identity-update.component';
import { GeneratorIdentityService } from 'app/entities/generator-identity/generator-identity.service';
import { GeneratorIdentity } from 'app/shared/model/generator-identity.model';

describe('Component Tests', () => {
  describe('GeneratorIdentity Management Update Component', () => {
    let comp: GeneratorIdentityUpdateComponent;
    let fixture: ComponentFixture<GeneratorIdentityUpdateComponent>;
    let service: GeneratorIdentityService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhonlineTestModule],
        declarations: [GeneratorIdentityUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(GeneratorIdentityUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GeneratorIdentityUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GeneratorIdentityService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new GeneratorIdentity(123);
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
        const entity = new GeneratorIdentity();
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
