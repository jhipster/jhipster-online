import { ComponentFixture, TestBed, async, inject, fakeAsync } from '@angular/core/testing';
import { of } from 'rxjs';

import { JhonlineTestModule } from '../../../test.module';
import { DeleteAccountDialogComponent } from 'app/account/settings/delete-account-dialog.component';
import { AccountService } from 'app/core/auth/account.service';

describe('Component Tests', () => {
  describe('User Management Delete Component', () => {
    let fixture: ComponentFixture<DeleteAccountDialogComponent>;
    let service: AccountService;

    beforeEach(async(() => {
      TestBed.configureTestingModule({
        imports: [JhonlineTestModule],
        declarations: [DeleteAccountDialogComponent],
        providers: [AccountService]
      })
        .overrideTemplate(DeleteAccountDialogComponent, '')
        .compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(DeleteAccountDialogComponent);
      service = fixture.debugElement.injector.get(AccountService);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

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
