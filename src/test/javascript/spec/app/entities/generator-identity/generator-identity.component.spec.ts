/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhonlineTestModule } from '../../../test.module';
import { GeneratorIdentityComponent } from 'app/entities/generator-identity/generator-identity.component';
import { GeneratorIdentityService } from 'app/entities/generator-identity/generator-identity.service';
import { GeneratorIdentity } from 'app/shared/model/generator-identity.model';

describe('Component Tests', () => {
    describe('GeneratorIdentity Management Component', () => {
        let comp: GeneratorIdentityComponent;
        let fixture: ComponentFixture<GeneratorIdentityComponent>;
        let service: GeneratorIdentityService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhonlineTestModule],
                declarations: [GeneratorIdentityComponent],
                providers: []
            })
                .overrideTemplate(GeneratorIdentityComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(GeneratorIdentityComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GeneratorIdentityService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new GeneratorIdentity(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.generatorIdentities[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
