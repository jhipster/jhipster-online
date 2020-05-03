import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhonlineTestModule } from '../../../test.module';
import { JdlMetadataUpdateComponent } from 'app/entities/jdl-metadata/jdl-metadata-update.component';
import { JdlMetadataService } from 'app/entities/jdl-metadata/jdl-metadata.service';
import { JdlMetadata } from 'app/shared/model/jdl-metadata.model';

describe('Component Tests', () => {
  describe('JdlMetadata Management Update Component', () => {
    let comp: JdlMetadataUpdateComponent;
    let fixture: ComponentFixture<JdlMetadataUpdateComponent>;
    let service: JdlMetadataService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhonlineTestModule],
        declarations: [JdlMetadataUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(JdlMetadataUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(JdlMetadataUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(JdlMetadataService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new JdlMetadata(123);
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
        const entity = new JdlMetadata();
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
