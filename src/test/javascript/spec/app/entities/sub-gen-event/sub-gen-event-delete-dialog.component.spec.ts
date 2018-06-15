/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { JhonlineTestModule } from '../../../test.module';
import { SubGenEventDeleteDialogComponent } from 'app/entities/sub-gen-event/sub-gen-event-delete-dialog.component';
import { SubGenEventService } from 'app/entities/sub-gen-event/sub-gen-event.service';

describe('Component Tests', () => {
    describe('SubGenEvent Management Delete Component', () => {
        let comp: SubGenEventDeleteDialogComponent;
        let fixture: ComponentFixture<SubGenEventDeleteDialogComponent>;
        let service: SubGenEventService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhonlineTestModule],
                declarations: [SubGenEventDeleteDialogComponent],
                providers: [SubGenEventService]
            })
                .overrideTemplate(SubGenEventDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SubGenEventDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SubGenEventService);
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
