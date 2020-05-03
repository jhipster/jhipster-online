import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IUserApplication, UserApplication } from 'app/shared/model/user-application.model';
import { UserApplicationService } from './user-application.service';
import { AlertError } from 'app/shared/alert/alert-error.model';

@Component({
  selector: 'jhi-user-application-update',
  templateUrl: './user-application-update.component.html'
})
export class UserApplicationUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [],
    shared: [],
    sharedLink: [],
    userId: [],
    configuration: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected userApplicationService: UserApplicationService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userApplication }) => {
      this.updateForm(userApplication);
    });
  }

  updateForm(userApplication: IUserApplication): void {
    this.editForm.patchValue({
      id: userApplication.id,
      name: userApplication.name,
      shared: userApplication.shared,
      sharedLink: userApplication.sharedLink,
      userId: userApplication.userId,
      configuration: userApplication.configuration
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('jhonlineApp.error', { message: err.message })
      );
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const userApplication = this.createFromForm();
    if (userApplication.id !== undefined) {
      this.subscribeToSaveResponse(this.userApplicationService.update(userApplication));
    } else {
      this.subscribeToSaveResponse(this.userApplicationService.create(userApplication));
    }
  }

  private createFromForm(): IUserApplication {
    return {
      ...new UserApplication(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      shared: this.editForm.get(['shared'])!.value,
      sharedLink: this.editForm.get(['sharedLink'])!.value,
      userId: this.editForm.get(['userId'])!.value,
      configuration: this.editForm.get(['configuration'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUserApplication>>): void {
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
}
