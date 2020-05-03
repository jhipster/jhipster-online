import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILanguage } from 'app/shared/model/language.model';
import { LanguageService } from './language.service';

@Component({
  templateUrl: './language-delete-dialog.component.html'
})
export class LanguageDeleteDialogComponent {
  language?: ILanguage;

  constructor(protected languageService: LanguageService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.languageService.delete(id).subscribe(() => {
      this.eventManager.broadcast('languageListModification');
      this.activeModal.close();
    });
  }
}
