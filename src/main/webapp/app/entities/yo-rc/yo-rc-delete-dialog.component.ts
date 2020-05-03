import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IYoRC } from 'app/shared/model/yo-rc.model';
import { YoRCService } from './yo-rc.service';

@Component({
  templateUrl: './yo-rc-delete-dialog.component.html'
})
export class YoRCDeleteDialogComponent {
  yoRC?: IYoRC;

  constructor(protected yoRCService: YoRCService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.yoRCService.delete(id).subscribe(() => {
      this.eventManager.broadcast('yoRCListModification');
      this.activeModal.close();
    });
  }
}
