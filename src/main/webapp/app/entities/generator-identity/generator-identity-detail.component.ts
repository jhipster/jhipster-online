import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGeneratorIdentity } from 'app/shared/model/generator-identity.model';

@Component({
  selector: 'jhi-generator-identity-detail',
  templateUrl: './generator-identity-detail.component.html'
})
export class GeneratorIdentityDetailComponent implements OnInit {
  generatorIdentity: IGeneratorIdentity | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ generatorIdentity }) => (this.generatorIdentity = generatorIdentity));
  }

  previousState(): void {
    window.history.back();
  }
}
