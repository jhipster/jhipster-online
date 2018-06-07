/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { JhonlineTestModule } from '../../../test.module';
import { OwnerIdentityDeleteDialogComponent } from 'app/entities/owner-identity/owner-identity-delete-dialog.component';
import { OwnerIdentityService } from 'app/entities/owner-identity/owner-identity.service';

describe('Component Tests', () => {
    describe('OwnerIdentity Management Delete Component', () => {
        let comp: OwnerIdentityDeleteDialogComponent;
        let fixture: ComponentFixture<OwnerIdentityDeleteDialogComponent>;
        let service: OwnerIdentityService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhonlineTestModule],
                declarations: [OwnerIdentityDeleteDialogComponent],
                providers: [OwnerIdentityService]
            })
                .overrideTemplate(OwnerIdentityDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(OwnerIdentityDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OwnerIdentityService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it(
                'Should call delete service on confirmDelete',
                inject(
                    [],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });
});
