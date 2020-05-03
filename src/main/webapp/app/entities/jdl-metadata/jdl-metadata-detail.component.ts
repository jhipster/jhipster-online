import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IJdlMetadata } from 'app/shared/model/jdl-metadata.model';

@Component({
  selector: 'jhi-jdl-metadata-detail',
  templateUrl: './jdl-metadata-detail.component.html'
})
export class JdlMetadataDetailComponent implements OnInit {
  jdlMetadata: IJdlMetadata | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ jdlMetadata }) => (this.jdlMetadata = jdlMetadata));
  }

  previousState(): void {
    window.history.back();
  }
}
