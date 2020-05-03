/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { JhonlineTestModule } from '../../../test.module';
import { JdlMetadataDeleteDialogComponent } from 'app/entities/jdl-metadata/jdl-metadata-delete-dialog.component';
import { JdlMetadataService } from 'app/entities/jdl-metadata/jdl-metadata.service';

describe('Component Tests', () => {
    describe('JdlMetadata Management Delete Component', () => {
        let comp: JdlMetadataDeleteDialogComponent;
        let fixture: ComponentFixture<JdlMetadataDeleteDialogComponent>;
        let service: JdlMetadataService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhonlineTestModule],
                declarations: [JdlMetadataDeleteDialogComponent]
            })
                .overrideTemplate(JdlMetadataDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(JdlMetadataDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(JdlMetadataService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
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
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
