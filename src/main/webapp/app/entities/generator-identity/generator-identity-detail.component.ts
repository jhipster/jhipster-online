import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGeneratorIdentity } from 'app/shared/model/generator-identity.model';

@Component({
    selector: 'jhi-generator-identity-detail',
    templateUrl: './generator-identity-detail.component.html'
})
export class GeneratorIdentityDetailComponent implements OnInit {
    generatorIdentity: IGeneratorIdentity;

    constructor(private route: ActivatedRoute) {}

    ngOnInit() {
        this.route.data.subscribe(({ generatorIdentity }) => {
            this.generatorIdentity = generatorIdentity.body ? generatorIdentity.body : generatorIdentity;
        });
    }

    previousState() {
        window.history.back();
    }
}
