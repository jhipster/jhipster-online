import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUserApplication } from 'app/shared/model/user-application.model';
import { UserApplicationService } from './user-application.service';

@Component({
  templateUrl: './user-application-delete-dialog.component.html'
})
export class UserApplicationDeleteDialogComponent {
  userApplication?: IUserApplication;

  constructor(
    protected userApplicationService: UserApplicationService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.userApplicationService.delete(id).subscribe(() => {
      this.eventManager.broadcast('userApplicationListModification');
      this.activeModal.close();
    });
  }
}
