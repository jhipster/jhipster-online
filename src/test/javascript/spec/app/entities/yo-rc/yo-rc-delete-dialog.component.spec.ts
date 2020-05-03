import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { JhonlineTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { YoRCDeleteDialogComponent } from 'app/entities/yo-rc/yo-rc-delete-dialog.component';
import { YoRCService } from 'app/entities/yo-rc/yo-rc.service';

describe('Component Tests', () => {
  describe('YoRC Management Delete Component', () => {
    let comp: YoRCDeleteDialogComponent;
    let fixture: ComponentFixture<YoRCDeleteDialogComponent>;
    let service: YoRCService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhonlineTestModule],
        declarations: [YoRCDeleteDialogComponent]
      })
        .overrideTemplate(YoRCDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(YoRCDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(YoRCService);
      mockEventManager = TestBed.get(JhiEventManager);
      mockActiveModal = TestBed.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.closeSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));

      it('Should not call delete service on clear', () => {
        // GIVEN
        spyOn(service, 'delete');

        // WHEN
        comp.cancel();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
      });
    });
  });
});
