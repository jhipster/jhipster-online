import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IYoRC } from 'app/shared/model/yo-rc.model';
import { YoRCService } from './yo-rc.service';
import { YoRCDeleteDialogComponent } from './yo-rc-delete-dialog.component';

@Component({
  selector: 'jhi-yo-rc',
  templateUrl: './yo-rc.component.html'
})
export class YoRCComponent implements OnInit, OnDestroy {
  yoRCS?: IYoRC[];
  eventSubscriber?: Subscription;

  constructor(protected yoRCService: YoRCService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.yoRCService.query().subscribe((res: HttpResponse<IYoRC[]>) => (this.yoRCS = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInYoRCS();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IYoRC): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInYoRCS(): void {
    this.eventSubscriber = this.eventManager.subscribe('yoRCListModification', () => this.loadAll());
  }

  delete(yoRC: IYoRC): void {
    const modalRef = this.modalService.open(YoRCDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.yoRC = yoRC;
  }
}
