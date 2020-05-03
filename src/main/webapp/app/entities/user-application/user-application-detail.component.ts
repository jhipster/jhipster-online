import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IUserApplication } from 'app/shared/model/user-application.model';

@Component({
  selector: 'jhi-user-application-detail',
  templateUrl: './user-application-detail.component.html'
})
export class UserApplicationDetailComponent implements OnInit {
  userApplication: IUserApplication | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userApplication }) => (this.userApplication = userApplication));
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  previousState(): void {
    window.history.back();
  }
}
