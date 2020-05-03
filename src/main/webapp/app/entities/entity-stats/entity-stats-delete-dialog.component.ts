import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEntityStats } from 'app/shared/model/entity-stats.model';
import { EntityStatsService } from './entity-stats.service';

@Component({
  templateUrl: './entity-stats-delete-dialog.component.html'
})
export class EntityStatsDeleteDialogComponent {
  entityStats?: IEntityStats;

  constructor(
    protected entityStatsService: EntityStatsService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.entityStatsService.delete(id).subscribe(() => {
      this.eventManager.broadcast('entityStatsListModification');
      this.activeModal.close();
    });
  }
}
