import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IGeneratorIdentity } from 'app/shared/model/generator-identity.model';
import { GeneratorIdentityService } from './generator-identity.service';

@Component({
  templateUrl: './generator-identity-delete-dialog.component.html'
})
export class GeneratorIdentityDeleteDialogComponent {
  generatorIdentity?: IGeneratorIdentity;

  constructor(
    protected generatorIdentityService: GeneratorIdentityService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.generatorIdentityService.delete(id).subscribe(() => {
      this.eventManager.broadcast('generatorIdentityListModification');
      this.activeModal.close();
    });
  }
}
