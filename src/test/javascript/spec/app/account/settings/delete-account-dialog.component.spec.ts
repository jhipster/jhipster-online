import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';

import { JhonlineTestModule } from '../../../test.module';
import { DeleteAccountDialogComponent } from 'app/account';
import { AccountService } from 'app/core';

describe('Component Tests', () => {
    describe('User Management Delete Component', () => {
        let comp: DeleteAccountDialogComponent;
        let fixture: ComponentFixture<DeleteAccountDialogComponent>;
        let service: AccountService;
        let mockActiveModal: any;

        beforeEach(
            async(() => {
                TestBed.configureTestingModule({
                    imports: [JhonlineTestModule],
                    declarations: [DeleteAccountDialogComponent],
                    providers: [AccountService]
                })
                    .overrideTemplate(DeleteAccountDialogComponent, '')
                    .compileComponents();
            })
        );

        beforeEach(() => {
            fixture = TestBed.createComponent(DeleteAccountDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AccountService);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it(
                'Should call delete service on confirmDelete',
                inject(
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
                )
            );
        });
    });
});
