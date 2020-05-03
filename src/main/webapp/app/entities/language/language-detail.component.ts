import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILanguage } from 'app/shared/model/language.model';

@Component({
  selector: 'jhi-language-detail',
  templateUrl: './language-detail.component.html'
})
export class LanguageDetailComponent implements OnInit {
  language: ILanguage | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ language }) => (this.language = language));
  }

  previousState(): void {
    window.history.back();
  }
}
