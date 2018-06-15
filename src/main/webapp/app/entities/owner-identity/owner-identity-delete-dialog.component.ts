import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOwnerIdentity } from 'app/shared/model/owner-identity.model';
import { OwnerIdentityService } from './owner-identity.service';

@Component({
    selector: 'jhi-owner-identity-delete-dialog',
    templateUrl: './owner-identity-delete-dialog.component.html'
})
export class OwnerIdentityDeleteDialogComponent {
    ownerIdentity: IOwnerIdentity;

    constructor(
        private ownerIdentityService: OwnerIdentityService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.ownerIdentityService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'ownerIdentityListModification',
                content: 'Deleted an ownerIdentity'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-owner-identity-delete-popup',
    template: ''
})
export class OwnerIdentityDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private route: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.route.data.subscribe(({ ownerIdentity }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(OwnerIdentityDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.ownerIdentity = ownerIdentity.body;
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
