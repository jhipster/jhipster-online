import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISubGenEvent } from 'app/shared/model/sub-gen-event.model';

@Component({
  selector: 'jhi-sub-gen-event-detail',
  templateUrl: './sub-gen-event-detail.component.html'
})
export class SubGenEventDetailComponent implements OnInit {
  subGenEvent: ISubGenEvent | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ subGenEvent }) => (this.subGenEvent = subGenEvent));
  }

  previousState(): void {
    window.history.back();
  }
}
