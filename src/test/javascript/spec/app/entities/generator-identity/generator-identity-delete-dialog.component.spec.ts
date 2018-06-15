/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { JhonlineTestModule } from '../../../test.module';
import { GeneratorIdentityDeleteDialogComponent } from 'app/entities/generator-identity/generator-identity-delete-dialog.component';
import { GeneratorIdentityService } from 'app/entities/generator-identity/generator-identity.service';

describe('Component Tests', () => {
    describe('GeneratorIdentity Management Delete Component', () => {
        let comp: GeneratorIdentityDeleteDialogComponent;
        let fixture: ComponentFixture<GeneratorIdentityDeleteDialogComponent>;
        let service: GeneratorIdentityService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhonlineTestModule],
                declarations: [GeneratorIdentityDeleteDialogComponent],
                providers: [GeneratorIdentityService]
            })
                .overrideTemplate(GeneratorIdentityDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(GeneratorIdentityDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GeneratorIdentityService);
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
