import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILanguage } from 'app/shared/model/language.model';

@Component({
    selector: 'jhi-language-detail',
    templateUrl: './language-detail.component.html'
})
export class LanguageDetailComponent implements OnInit {
    language: ILanguage;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ language }) => {
            this.language = language;
        });
    }

    previousState() {
        window.history.back();
    }
}
