/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { JhonlineTestModule } from '../../../test.module';
import { GeneratorIdentityUpdateComponent } from 'app/entities/generator-identity/generator-identity-update.component';
import { GeneratorIdentityService } from 'app/entities/generator-identity/generator-identity.service';
import { GeneratorIdentity } from 'app/shared/model/generator-identity.model';

import { OwnerIdentityService } from 'app/entities/owner-identity';

describe('Component Tests', () => {
    describe('GeneratorIdentity Management Update Component', () => {
        let comp: GeneratorIdentityUpdateComponent;
        let fixture: ComponentFixture<GeneratorIdentityUpdateComponent>;
        let service: GeneratorIdentityService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhonlineTestModule],
                declarations: [GeneratorIdentityUpdateComponent],
                providers: [OwnerIdentityService, GeneratorIdentityService]
            })
                .overrideTemplate(GeneratorIdentityUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(GeneratorIdentityUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GeneratorIdentityService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new GeneratorIdentity(123);
                    spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({ body: entity })));
                    comp.generatorIdentity = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new GeneratorIdentity();
                    spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({ body: entity })));
                    comp.generatorIdentity = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
