import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISubGenEvent } from 'app/shared/model/sub-gen-event.model';
import { SubGenEventService } from './sub-gen-event.service';
import { SubGenEventDeleteDialogComponent } from './sub-gen-event-delete-dialog.component';

@Component({
  selector: 'jhi-sub-gen-event',
  templateUrl: './sub-gen-event.component.html'
})
export class SubGenEventComponent implements OnInit, OnDestroy {
  subGenEvents?: ISubGenEvent[];
  eventSubscriber?: Subscription;

  constructor(
    protected subGenEventService: SubGenEventService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.subGenEventService.query().subscribe((res: HttpResponse<ISubGenEvent[]>) => (this.subGenEvents = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInSubGenEvents();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ISubGenEvent): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInSubGenEvents(): void {
    this.eventSubscriber = this.eventManager.subscribe('subGenEventListModification', () => this.loadAll());
  }

  delete(subGenEvent: ISubGenEvent): void {
    const modalRef = this.modalService.open(SubGenEventDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.subGenEvent = subGenEvent;
  }
}
