import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IYoRC, YoRC } from 'app/shared/model/yo-rc.model';
import { YoRCService } from './yo-rc.service';
import { IGeneratorIdentity } from 'app/shared/model/generator-identity.model';
import { GeneratorIdentityService } from 'app/entities/generator-identity/generator-identity.service';

@Component({
  selector: 'jhi-yo-rc-update',
  templateUrl: './yo-rc-update.component.html'
})
export class YoRCUpdateComponent implements OnInit {
  isSaving = false;
  generatoridentities: IGeneratorIdentity[] = [];

  editForm = this.fb.group({
    id: [],
    jhipsterVersion: [],
    creationDate: [],
    gitProvider: [],
    nodeVersion: [],
    os: [],
    arch: [],
    cpu: [],
    cores: [],
    memory: [],
    userLanguage: [],
    year: [],
    month: [],
    week: [],
    day: [],
    hour: [],
    serverPort: [],
    authenticationType: [],
    cacheProvider: [],
    enableHibernateCache: [],
    websocket: [],
    databaseType: [],
    devDatabaseType: [],
    prodDatabaseType: [],
    searchEngine: [],
    messageBroker: [],
    serviceDiscoveryType: [],
    buildTool: [],
    enableSwaggerCodegen: [],
    clientFramework: [],
    useSass: [],
    clientPackageManager: [],
    applicationType: [],
    jhiPrefix: [],
    enableTranslation: [],
    nativeLanguage: [],
    hasProtractor: [],
    hasGatling: [],
    hasCucumber: [],
    owner: []
  });

  constructor(
    protected yoRCService: YoRCService,
    protected generatorIdentityService: GeneratorIdentityService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ yoRC }) => {
      if (!yoRC.id) {
        const today = moment().startOf('day');
        yoRC.creationDate = today;
      }

      this.updateForm(yoRC);

      this.generatorIdentityService
        .query()
        .subscribe((res: HttpResponse<IGeneratorIdentity[]>) => (this.generatoridentities = res.body || []));
    });
  }

  updateForm(yoRC: IYoRC): void {
    this.editForm.patchValue({
      id: yoRC.id,
      jhipsterVersion: yoRC.jhipsterVersion,
      creationDate: yoRC.creationDate ? yoRC.creationDate.format(DATE_TIME_FORMAT) : null,
      gitProvider: yoRC.gitProvider,
      nodeVersion: yoRC.nodeVersion,
      os: yoRC.os,
      arch: yoRC.arch,
      cpu: yoRC.cpu,
      cores: yoRC.cores,
      memory: yoRC.memory,
      userLanguage: yoRC.userLanguage,
      year: yoRC.year,
      month: yoRC.month,
      week: yoRC.week,
      day: yoRC.day,
      hour: yoRC.hour,
      serverPort: yoRC.serverPort,
      authenticationType: yoRC.authenticationType,
      cacheProvider: yoRC.cacheProvider,
      enableHibernateCache: yoRC.enableHibernateCache,
      websocket: yoRC.websocket,
      databaseType: yoRC.databaseType,
      devDatabaseType: yoRC.devDatabaseType,
      prodDatabaseType: yoRC.prodDatabaseType,
      searchEngine: yoRC.searchEngine,
      messageBroker: yoRC.messageBroker,
      serviceDiscoveryType: yoRC.serviceDiscoveryType,
      buildTool: yoRC.buildTool,
      enableSwaggerCodegen: yoRC.enableSwaggerCodegen,
      clientFramework: yoRC.clientFramework,
      useSass: yoRC.useSass,
      clientPackageManager: yoRC.clientPackageManager,
      applicationType: yoRC.applicationType,
      jhiPrefix: yoRC.jhiPrefix,
      enableTranslation: yoRC.enableTranslation,
      nativeLanguage: yoRC.nativeLanguage,
      hasProtractor: yoRC.hasProtractor,
      hasGatling: yoRC.hasGatling,
      hasCucumber: yoRC.hasCucumber,
      owner: yoRC.owner
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const yoRC = this.createFromForm();
    if (yoRC.id !== undefined) {
      this.subscribeToSaveResponse(this.yoRCService.update(yoRC));
    } else {
      this.subscribeToSaveResponse(this.yoRCService.create(yoRC));
    }
  }

  private createFromForm(): IYoRC {
    return {
      ...new YoRC(),
      id: this.editForm.get(['id'])!.value,
      jhipsterVersion: this.editForm.get(['jhipsterVersion'])!.value,
      creationDate: this.editForm.get(['creationDate'])!.value
        ? moment(this.editForm.get(['creationDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      gitProvider: this.editForm.get(['gitProvider'])!.value,
      nodeVersion: this.editForm.get(['nodeVersion'])!.value,
      os: this.editForm.get(['os'])!.value,
      arch: this.editForm.get(['arch'])!.value,
      cpu: this.editForm.get(['cpu'])!.value,
      cores: this.editForm.get(['cores'])!.value,
      memory: this.editForm.get(['memory'])!.value,
      userLanguage: this.editForm.get(['userLanguage'])!.value,
      year: this.editForm.get(['year'])!.value,
      month: this.editForm.get(['month'])!.value,
      week: this.editForm.get(['week'])!.value,
      day: this.editForm.get(['day'])!.value,
      hour: this.editForm.get(['hour'])!.value,
      serverPort: this.editForm.get(['serverPort'])!.value,
      authenticationType: this.editForm.get(['authenticationType'])!.value,
      cacheProvider: this.editForm.get(['cacheProvider'])!.value,
      enableHibernateCache: this.editForm.get(['enableHibernateCache'])!.value,
      websocket: this.editForm.get(['websocket'])!.value,
      databaseType: this.editForm.get(['databaseType'])!.value,
      devDatabaseType: this.editForm.get(['devDatabaseType'])!.value,
      prodDatabaseType: this.editForm.get(['prodDatabaseType'])!.value,
      searchEngine: this.editForm.get(['searchEngine'])!.value,
      messageBroker: this.editForm.get(['messageBroker'])!.value,
      serviceDiscoveryType: this.editForm.get(['serviceDiscoveryType'])!.value,
      buildTool: this.editForm.get(['buildTool'])!.value,
      enableSwaggerCodegen: this.editForm.get(['enableSwaggerCodegen'])!.value,
      clientFramework: this.editForm.get(['clientFramework'])!.value,
      useSass: this.editForm.get(['useSass'])!.value,
      clientPackageManager: this.editForm.get(['clientPackageManager'])!.value,
      applicationType: this.editForm.get(['applicationType'])!.value,
      jhiPrefix: this.editForm.get(['jhiPrefix'])!.value,
      enableTranslation: this.editForm.get(['enableTranslation'])!.value,
      nativeLanguage: this.editForm.get(['nativeLanguage'])!.value,
      hasProtractor: this.editForm.get(['hasProtractor'])!.value,
      hasGatling: this.editForm.get(['hasGatling'])!.value,
      hasCucumber: this.editForm.get(['hasCucumber'])!.value,
      owner: this.editForm.get(['owner'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IYoRC>>): void {
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
