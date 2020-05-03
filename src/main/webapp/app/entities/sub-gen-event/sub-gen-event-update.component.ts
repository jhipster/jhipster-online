import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ISubGenEvent, SubGenEvent } from 'app/shared/model/sub-gen-event.model';
import { SubGenEventService } from './sub-gen-event.service';
import { IGeneratorIdentity } from 'app/shared/model/generator-identity.model';
import { GeneratorIdentityService } from 'app/entities/generator-identity/generator-identity.service';

@Component({
  selector: 'jhi-sub-gen-event-update',
  templateUrl: './sub-gen-event-update.component.html'
})
export class SubGenEventUpdateComponent implements OnInit {
  isSaving = false;
  generatoridentities: IGeneratorIdentity[] = [];

  editForm = this.fb.group({
    id: [],
    year: [],
    month: [],
    week: [],
    day: [],
    hour: [],
    source: [],
    type: [],
    event: [],
    date: [],
    owner: []
  });

  constructor(
    protected subGenEventService: SubGenEventService,
    protected generatorIdentityService: GeneratorIdentityService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ subGenEvent }) => {
      if (!subGenEvent.id) {
        const today = moment().startOf('day');
        subGenEvent.date = today;
      }

      this.updateForm(subGenEvent);

      this.generatorIdentityService
        .query()
        .subscribe((res: HttpResponse<IGeneratorIdentity[]>) => (this.generatoridentities = res.body || []));
    });
  }

  updateForm(subGenEvent: ISubGenEvent): void {
    this.editForm.patchValue({
      id: subGenEvent.id,
      year: subGenEvent.year,
      month: subGenEvent.month,
      week: subGenEvent.week,
      day: subGenEvent.day,
      hour: subGenEvent.hour,
      source: subGenEvent.source,
      type: subGenEvent.type,
      event: subGenEvent.event,
      date: subGenEvent.date ? subGenEvent.date.format(DATE_TIME_FORMAT) : null,
      owner: subGenEvent.owner
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const subGenEvent = this.createFromForm();
    if (subGenEvent.id !== undefined) {
      this.subscribeToSaveResponse(this.subGenEventService.update(subGenEvent));
    } else {
      this.subscribeToSaveResponse(this.subGenEventService.create(subGenEvent));
    }
  }

  private createFromForm(): ISubGenEvent {
    return {
      ...new SubGenEvent(),
      id: this.editForm.get(['id'])!.value,
      year: this.editForm.get(['year'])!.value,
      month: this.editForm.get(['month'])!.value,
      week: this.editForm.get(['week'])!.value,
      day: this.editForm.get(['day'])!.value,
      hour: this.editForm.get(['hour'])!.value,
      source: this.editForm.get(['source'])!.value,
      type: this.editForm.get(['type'])!.value,
      event: this.editForm.get(['event'])!.value,
      date: this.editForm.get(['date'])!.value ? moment(this.editForm.get(['date'])!.value, DATE_TIME_FORMAT) : undefined,
      owner: this.editForm.get(['owner'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISubGenEvent>>): void {
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

  trackById(index: number, item: IGeneratorIdentity): any {
    return item.id;
  }
}
