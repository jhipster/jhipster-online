import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IGeneratorIdentity } from 'app/shared/model/generator-identity.model';
import { GeneratorIdentityService } from './generator-identity.service';
import { GeneratorIdentityDeleteDialogComponent } from './generator-identity-delete-dialog.component';

@Component({
  selector: 'jhi-generator-identity',
  templateUrl: './generator-identity.component.html'
})
export class GeneratorIdentityComponent implements OnInit, OnDestroy {
  generatorIdentities?: IGeneratorIdentity[];
  eventSubscriber?: Subscription;

  constructor(
    protected generatorIdentityService: GeneratorIdentityService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.generatorIdentityService
      .query()
      .subscribe((res: HttpResponse<IGeneratorIdentity[]>) => (this.generatorIdentities = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInGeneratorIdentities();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IGeneratorIdentity): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInGeneratorIdentities(): void {
    this.eventSubscriber = this.eventManager.subscribe('generatorIdentityListModification', () => this.loadAll());
  }

  delete(generatorIdentity: IGeneratorIdentity): void {
    const modalRef = this.modalService.open(GeneratorIdentityDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.generatorIdentity = generatorIdentity;
  }
}
