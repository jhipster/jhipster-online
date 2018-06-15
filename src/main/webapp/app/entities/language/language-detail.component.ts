import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILanguage } from 'app/shared/model/language.model';

@Component({
    selector: 'jhi-language-detail',
    templateUrl: './language-detail.component.html'
})
export class LanguageDetailComponent implements OnInit {
    language: ILanguage;

    constructor(private route: ActivatedRoute) {}

    ngOnInit() {
        this.route.data.subscribe(({ language }) => {
            this.language = language.body ? language.body : language;
        });
    }

    previousState() {
        window.history.back();
    }
}
