import { ComponentFixture, TestBed, async, inject, fakeAsync } from '@angular/core/testing';

import { JhonlineTestModule } from '../../../test.module';
import { DeleteAccountDialogComponent } from 'app/account/settings/delete-account-dialog.component';

describe('Component Tests', () => {
  describe('User Management Delete Component', () => {
    // eslint-disable-next-line @typescript-eslint/no-unused-vars
    let comp: DeleteAccountDialogComponent;
    let fixture: ComponentFixture<DeleteAccountDialogComponent>;

    beforeEach(async(() => {
      TestBed.configureTestingModule({
        imports: [JhonlineTestModule],
        declarations: [DeleteAccountDialogComponent]
      })
        .overrideTemplate(DeleteAccountDialogComponent, '')
        .compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(DeleteAccountDialogComponent);
      comp = fixture.componentInstance;
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          // WHEN
          // comp.confirmDelete();
          // tick();
          //
          // // THEN
          // expect(service.delete).toHaveBeenCalled();
          // expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          // expect(comp.showAlert).toEqual(true);
        })
      ));
    });
  });
});
