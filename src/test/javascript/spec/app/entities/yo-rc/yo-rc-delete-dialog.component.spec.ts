/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { JhonlineTestModule } from '../../../test.module';
import { YoRCDeleteDialogComponent } from 'app/entities/yo-rc/yo-rc-delete-dialog.component';
import { YoRCService } from 'app/entities/yo-rc/yo-rc.service';

describe('Component Tests', () => {
    describe('YoRC Management Delete Component', () => {
        let comp: YoRCDeleteDialogComponent;
        let fixture: ComponentFixture<YoRCDeleteDialogComponent>;
        let service: YoRCService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhonlineTestModule],
                declarations: [YoRCDeleteDialogComponent],
                providers: [YoRCService]
            })
                .overrideTemplate(YoRCDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(YoRCDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(YoRCService);
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
