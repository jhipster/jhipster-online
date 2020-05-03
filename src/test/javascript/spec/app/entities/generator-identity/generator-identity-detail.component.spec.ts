/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhonlineTestModule } from '../../../test.module';
import { GeneratorIdentityDetailComponent } from 'app/entities/generator-identity/generator-identity-detail.component';
import { GeneratorIdentity } from 'app/shared/model/generator-identity.model';

describe('Component Tests', () => {
    describe('GeneratorIdentity Management Detail Component', () => {
        let comp: GeneratorIdentityDetailComponent;
        let fixture: ComponentFixture<GeneratorIdentityDetailComponent>;
        const route = ({ data: of({ generatorIdentity: new GeneratorIdentity(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhonlineTestModule],
                declarations: [GeneratorIdentityDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(GeneratorIdentityDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(GeneratorIdentityDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.generatorIdentity).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
