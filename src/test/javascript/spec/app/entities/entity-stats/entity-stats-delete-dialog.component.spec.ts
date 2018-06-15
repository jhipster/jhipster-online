/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { JhonlineTestModule } from '../../../test.module';
import { EntityStatsDeleteDialogComponent } from 'app/entities/entity-stats/entity-stats-delete-dialog.component';
import { EntityStatsService } from 'app/entities/entity-stats/entity-stats.service';

describe('Component Tests', () => {
    describe('EntityStats Management Delete Component', () => {
        let comp: EntityStatsDeleteDialogComponent;
        let fixture: ComponentFixture<EntityStatsDeleteDialogComponent>;
        let service: EntityStatsService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhonlineTestModule],
                declarations: [EntityStatsDeleteDialogComponent],
                providers: [EntityStatsService]
            })
                .overrideTemplate(EntityStatsDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(EntityStatsDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EntityStatsService);
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
