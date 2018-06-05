/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs/observable/of';

import { JhonlineTestModule } from '../../../test.module';
import { LanguageDetailComponent } from 'app/entities/language/language-detail.component';
import { Language } from 'app/shared/model/language.model';

describe('Component Tests', () => {
    describe('Language Management Detail Component', () => {
        let comp: LanguageDetailComponent;
        let fixture: ComponentFixture<LanguageDetailComponent>;
        const route = ({ data: of({ language: new Language(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhonlineTestModule],
                declarations: [LanguageDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(LanguageDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(LanguageDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.language).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
