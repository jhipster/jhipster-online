import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISubGenEvent } from 'app/shared/model/sub-gen-event.model';
import { SubGenEventService } from './sub-gen-event.service';

@Component({
    selector: 'jhi-sub-gen-event-delete-dialog',
    templateUrl: './sub-gen-event-delete-dialog.component.html'
})
export class SubGenEventDeleteDialogComponent {
    subGenEvent: ISubGenEvent;

    constructor(
        private subGenEventService: SubGenEventService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.subGenEventService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'subGenEventListModification',
                content: 'Deleted an subGenEvent'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-sub-gen-event-delete-popup',
    template: ''
})
export class SubGenEventDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ subGenEvent }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(SubGenEventDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.subGenEvent = subGenEvent;
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
