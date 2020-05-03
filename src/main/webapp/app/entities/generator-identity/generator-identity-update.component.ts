import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IGeneratorIdentity, GeneratorIdentity } from 'app/shared/model/generator-identity.model';
import { GeneratorIdentityService } from './generator-identity.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'jhi-generator-identity-update',
  templateUrl: './generator-identity-update.component.html'
})
export class GeneratorIdentityUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    host: [],
    guid: [],
    owner: []
  });

  constructor(
    protected generatorIdentityService: GeneratorIdentityService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ generatorIdentity }) => {
      this.updateForm(generatorIdentity);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(generatorIdentity: IGeneratorIdentity): void {
    this.editForm.patchValue({
      id: generatorIdentity.id,
      host: generatorIdentity.host,
      guid: generatorIdentity.guid,
      owner: generatorIdentity.owner
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const generatorIdentity = this.createFromForm();
    if (generatorIdentity.id !== undefined) {
      this.subscribeToSaveResponse(this.generatorIdentityService.update(generatorIdentity));
    } else {
      this.subscribeToSaveResponse(this.generatorIdentityService.create(generatorIdentity));
    }
  }

  private createFromForm(): IGeneratorIdentity {
    return {
      ...new GeneratorIdentity(),
      id: this.editForm.get(['id'])!.value,
      host: this.editForm.get(['host'])!.value,
      guid: this.editForm.get(['guid'])!.value,
      owner: this.editForm.get(['owner'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGeneratorIdentity>>): void {
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
