import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISubGenEvent } from 'app/shared/model/sub-gen-event.model';
import { SubGenEventService } from './sub-gen-event.service';

@Component({
  templateUrl: './sub-gen-event-delete-dialog.component.html'
})
export class SubGenEventDeleteDialogComponent {
  subGenEvent?: ISubGenEvent;

  constructor(
    protected subGenEventService: SubGenEventService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.subGenEventService.delete(id).subscribe(() => {
      this.eventManager.broadcast('subGenEventListModification');
      this.activeModal.close();
    });
  }
}
