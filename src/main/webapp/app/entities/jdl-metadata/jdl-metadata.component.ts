import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IJdlMetadata } from 'app/shared/model/jdl-metadata.model';
import { JdlMetadataService } from './jdl-metadata.service';
import { JdlMetadataDeleteDialogComponent } from './jdl-metadata-delete-dialog.component';

@Component({
  selector: 'jhi-jdl-metadata',
  templateUrl: './jdl-metadata.component.html'
})
export class JdlMetadataComponent implements OnInit, OnDestroy {
  jdlMetadata?: IJdlMetadata[];
  eventSubscriber?: Subscription;

  constructor(
    protected jdlMetadataService: JdlMetadataService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.jdlMetadataService.query().subscribe((res: HttpResponse<IJdlMetadata[]>) => (this.jdlMetadata = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInJdlMetadata();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IJdlMetadata): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInJdlMetadata(): void {
    this.eventSubscriber = this.eventManager.subscribe('jdlMetadataListModification', () => this.loadAll());
  }

  delete(jdlMetadata: IJdlMetadata): void {
    const modalRef = this.modalService.open(JdlMetadataDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.jdlMetadata = jdlMetadata;
  }
}
