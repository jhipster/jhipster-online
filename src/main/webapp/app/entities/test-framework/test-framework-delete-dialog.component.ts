import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITestFramework } from 'app/shared/model/test-framework.model';
import { TestFrameworkService } from './test-framework.service';

@Component({
    selector: 'jhi-test-framework-delete-dialog',
    templateUrl: './test-framework-delete-dialog.component.html'
})
export class TestFrameworkDeleteDialogComponent {
    testFramework: ITestFramework;

    constructor(
        private testFrameworkService: TestFrameworkService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.testFrameworkService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'testFrameworkListModification',
                content: 'Deleted an testFramework'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-test-framework-delete-popup',
    template: ''
})
export class TestFrameworkDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private route: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.route.data.subscribe(({ testFramework }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(TestFrameworkDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.testFramework = testFramework.body;
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
