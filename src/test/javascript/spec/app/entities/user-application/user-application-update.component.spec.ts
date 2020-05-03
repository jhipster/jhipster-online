import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhonlineTestModule } from '../../../test.module';
import { UserApplicationUpdateComponent } from 'app/entities/user-application/user-application-update.component';
import { UserApplicationService } from 'app/entities/user-application/user-application.service';
import { UserApplication } from 'app/shared/model/user-application.model';

describe('Component Tests', () => {
  describe('UserApplication Management Update Component', () => {
    let comp: UserApplicationUpdateComponent;
    let fixture: ComponentFixture<UserApplicationUpdateComponent>;
    let service: UserApplicationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhonlineTestModule],
        declarations: [UserApplicationUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(UserApplicationUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserApplicationUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserApplicationService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new UserApplication(123);
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
        const entity = new UserApplication();
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
