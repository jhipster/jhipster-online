import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IEntityStats } from 'app/shared/model/entity-stats.model';
import { EntityStatsService } from './entity-stats.service';
import { EntityStatsDeleteDialogComponent } from './entity-stats-delete-dialog.component';

@Component({
  selector: 'jhi-entity-stats',
  templateUrl: './entity-stats.component.html'
})
export class EntityStatsComponent implements OnInit, OnDestroy {
  entityStats?: IEntityStats[];
  eventSubscriber?: Subscription;

  constructor(
    protected entityStatsService: EntityStatsService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.entityStatsService.query().subscribe((res: HttpResponse<IEntityStats[]>) => (this.entityStats = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInEntityStats();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IEntityStats): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInEntityStats(): void {
    this.eventSubscriber = this.eventManager.subscribe('entityStatsListModification', () => this.loadAll());
  }

  delete(entityStats: IEntityStats): void {
    const modalRef = this.modalService.open(EntityStatsDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.entityStats = entityStats;
  }
}
