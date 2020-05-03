import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ILanguage, Language } from 'app/shared/model/language.model';
import { LanguageService } from './language.service';
import { IYoRC } from 'app/shared/model/yo-rc.model';
import { YoRCService } from 'app/entities/yo-rc/yo-rc.service';

@Component({
  selector: 'jhi-language-update',
  templateUrl: './language-update.component.html'
})
export class LanguageUpdateComponent implements OnInit {
  isSaving = false;
  yorcs: IYoRC[] = [];

  editForm = this.fb.group({
    id: [],
    isoCode: [],
    yoRC: []
  });

  constructor(
    protected languageService: LanguageService,
    protected yoRCService: YoRCService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ language }) => {
      this.updateForm(language);

      this.yoRCService.query().subscribe((res: HttpResponse<IYoRC[]>) => (this.yorcs = res.body || []));
    });
  }

  updateForm(language: ILanguage): void {
    this.editForm.patchValue({
      id: language.id,
      isoCode: language.isoCode,
      yoRC: language.yoRC
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const language = this.createFromForm();
    if (language.id !== undefined) {
      this.subscribeToSaveResponse(this.languageService.update(language));
    } else {
      this.subscribeToSaveResponse(this.languageService.create(language));
    }
  }

  private createFromForm(): ILanguage {
    return {
      ...new Language(),
      id: this.editForm.get(['id'])!.value,
      isoCode: this.editForm.get(['isoCode'])!.value,
      yoRC: this.editForm.get(['yoRC'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILanguage>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IYoRC): any {
    return item.id;
  }
}
