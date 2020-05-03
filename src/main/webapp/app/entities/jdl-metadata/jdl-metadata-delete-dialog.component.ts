import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IJdlMetadata } from 'app/shared/model/jdl-metadata.model';
import { JdlMetadataService } from './jdl-metadata.service';

@Component({
  templateUrl: './jdl-metadata-delete-dialog.component.html'
})
export class JdlMetadataDeleteDialogComponent {
  jdlMetadata?: IJdlMetadata;

  constructor(
    protected jdlMetadataService: JdlMetadataService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.jdlMetadataService.delete(id).subscribe(() => {
      this.eventManager.broadcast('jdlMetadataListModification');
      this.activeModal.close();
    });
  }
}
