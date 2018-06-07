/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { JhonlineTestModule } from '../../../test.module';
import { TestFrameworkDeleteDialogComponent } from 'app/entities/test-framework/test-framework-delete-dialog.component';
import { TestFrameworkService } from 'app/entities/test-framework/test-framework.service';

describe('Component Tests', () => {
    describe('TestFramework Management Delete Component', () => {
        let comp: TestFrameworkDeleteDialogComponent;
        let fixture: ComponentFixture<TestFrameworkDeleteDialogComponent>;
        let service: TestFrameworkService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhonlineTestModule],
                declarations: [TestFrameworkDeleteDialogComponent],
                providers: [TestFrameworkService]
            })
                .overrideTemplate(TestFrameworkDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TestFrameworkDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TestFrameworkService);
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
