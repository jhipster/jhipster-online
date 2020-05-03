import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IJdlMetadata } from 'app/shared/model/jdl-metadata.model';

@Component({
    selector: 'jhi-jdl-metadata-detail',
    templateUrl: './jdl-metadata-detail.component.html'
})
export class JdlMetadataDetailComponent implements OnInit {
    jdlMetadata: IJdlMetadata;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ jdlMetadata }) => {
            this.jdlMetadata = jdlMetadata;
        });
    }

    previousState() {
        window.history.back();
    }
}
