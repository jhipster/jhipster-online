import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IEntityStats, EntityStats } from 'app/shared/model/entity-stats.model';
import { EntityStatsService } from './entity-stats.service';
import { IGeneratorIdentity } from 'app/shared/model/generator-identity.model';
import { GeneratorIdentityService } from 'app/entities/generator-identity/generator-identity.service';

@Component({
  selector: 'jhi-entity-stats-update',
  templateUrl: './entity-stats-update.component.html'
})
export class EntityStatsUpdateComponent implements OnInit {
  isSaving = false;
  generatoridentities: IGeneratorIdentity[] = [];

  editForm = this.fb.group({
    id: [],
    year: [],
    month: [],
    week: [],
    day: [],
    hour: [],
    fields: [],
    relationships: [],
    pagination: [],
    dto: [],
    service: [],
    fluentMethods: [],
    date: [],
    owner: []
  });

  constructor(
    protected entityStatsService: EntityStatsService,
    protected generatorIdentityService: GeneratorIdentityService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ entityStats }) => {
      if (!entityStats.id) {
        const today = moment().startOf('day');
        entityStats.date = today;
      }

      this.updateForm(entityStats);

      this.generatorIdentityService
        .query()
        .subscribe((res: HttpResponse<IGeneratorIdentity[]>) => (this.generatoridentities = res.body || []));
    });
  }

  updateForm(entityStats: IEntityStats): void {
    this.editForm.patchValue({
      id: entityStats.id,
      year: entityStats.year,
      month: entityStats.month,
      week: entityStats.week,
      day: entityStats.day,
      hour: entityStats.hour,
      fields: entityStats.fields,
      relationships: entityStats.relationships,
      pagination: entityStats.pagination,
      dto: entityStats.dto,
      service: entityStats.service,
      fluentMethods: entityStats.fluentMethods,
      date: entityStats.date ? entityStats.date.format(DATE_TIME_FORMAT) : null,
      owner: entityStats.owner
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const entityStats = this.createFromForm();
    if (entityStats.id !== undefined) {
      this.subscribeToSaveResponse(this.entityStatsService.update(entityStats));
    } else {
      this.subscribeToSaveResponse(this.entityStatsService.create(entityStats));
    }
  }

  private createFromForm(): IEntityStats {
    return {
      ...new EntityStats(),
      id: this.editForm.get(['id'])!.value,
      year: this.editForm.get(['year'])!.value,
      month: this.editForm.get(['month'])!.value,
      week: this.editForm.get(['week'])!.value,
      day: this.editForm.get(['day'])!.value,
      hour: this.editForm.get(['hour'])!.value,
      fields: this.editForm.get(['fields'])!.value,
      relationships: this.editForm.get(['relationships'])!.value,
      pagination: this.editForm.get(['pagination'])!.value,
      dto: this.editForm.get(['dto'])!.value,
      service: this.editForm.get(['service'])!.value,
      fluentMethods: this.editForm.get(['fluentMethods'])!.value,
      date: this.editForm.get(['date'])!.value ? moment(this.editForm.get(['date'])!.value, DATE_TIME_FORMAT) : undefined,
      owner: this.editForm.get(['owner'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEntityStats>>): void {
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
