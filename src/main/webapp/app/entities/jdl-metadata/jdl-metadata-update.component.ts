import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IJdlMetadata, JdlMetadata } from 'app/shared/model/jdl-metadata.model';
import { JdlMetadataService } from './jdl-metadata.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'jhi-jdl-metadata-update',
  templateUrl: './jdl-metadata-update.component.html'
})
export class JdlMetadataUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    key: [null, [Validators.required]],
    name: [],
    isPublic: [],
    user: [null, Validators.required]
  });

  constructor(
    protected jdlMetadataService: JdlMetadataService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ jdlMetadata }) => {
      this.updateForm(jdlMetadata);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(jdlMetadata: IJdlMetadata): void {
    this.editForm.patchValue({
      id: jdlMetadata.id,
      key: jdlMetadata.key,
      name: jdlMetadata.name,
      isPublic: jdlMetadata.isPublic,
      user: jdlMetadata.user
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const jdlMetadata = this.createFromForm();
    if (jdlMetadata.id !== undefined) {
      this.subscribeToSaveResponse(this.jdlMetadataService.update(jdlMetadata));
    } else {
      this.subscribeToSaveResponse(this.jdlMetadataService.create(jdlMetadata));
    }
  }

  private createFromForm(): IJdlMetadata {
    return {
      ...new JdlMetadata(),
      id: this.editForm.get(['id'])!.value,
      key: this.editForm.get(['key'])!.value,
      name: this.editForm.get(['name'])!.value,
      isPublic: this.editForm.get(['isPublic'])!.value,
      user: this.editForm.get(['user'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IJdlMetadata>>): void {
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

  trackById(index: number, item: IUser): any {
    return item.id;
  }
}
