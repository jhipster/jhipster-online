import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEntityStats } from 'app/shared/model/entity-stats.model';
import { EntityStatsService } from './entity-stats.service';

@Component({
    selector: 'jhi-entity-stats-delete-dialog',
    templateUrl: './entity-stats-delete-dialog.component.html'
})
export class EntityStatsDeleteDialogComponent {
    entityStats: IEntityStats;

    constructor(
        private entityStatsService: EntityStatsService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.entityStatsService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'entityStatsListModification',
                content: 'Deleted an entityStats'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-entity-stats-delete-popup',
    template: ''
})
export class EntityStatsDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ entityStats }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(EntityStatsDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.entityStats = entityStats;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
