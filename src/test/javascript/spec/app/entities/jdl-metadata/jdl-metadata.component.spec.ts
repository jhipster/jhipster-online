import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhonlineTestModule } from '../../../test.module';
import { JdlMetadataComponent } from 'app/entities/jdl-metadata/jdl-metadata.component';
import { JdlMetadataService } from 'app/entities/jdl-metadata/jdl-metadata.service';
import { JdlMetadata } from 'app/shared/model/jdl-metadata.model';

describe('Component Tests', () => {
  describe('JdlMetadata Management Component', () => {
    let comp: JdlMetadataComponent;
    let fixture: ComponentFixture<JdlMetadataComponent>;
    let service: JdlMetadataService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhonlineTestModule],
        declarations: [JdlMetadataComponent]
      })
        .overrideTemplate(JdlMetadataComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(JdlMetadataComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(JdlMetadataService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new JdlMetadata(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.jdlMetadata && comp.jdlMetadata[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
