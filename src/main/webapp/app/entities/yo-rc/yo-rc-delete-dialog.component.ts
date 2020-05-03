import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IYoRC } from 'app/shared/model/yo-rc.model';
import { YoRCService } from './yo-rc.service';

@Component({
    selector: 'jhi-yo-rc-delete-dialog',
    templateUrl: './yo-rc-delete-dialog.component.html'
})
export class YoRCDeleteDialogComponent {
    yoRC: IYoRC;

    constructor(private yoRCService: YoRCService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.yoRCService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'yoRCListModification',
                content: 'Deleted an yoRC'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-yo-rc-delete-popup',
    template: ''
})
export class YoRCDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ yoRC }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(YoRCDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.yoRC = yoRC;
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
