/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhonlineTestModule } from '../../../test.module';
import { OwnerIdentityComponent } from 'app/entities/owner-identity/owner-identity.component';
import { OwnerIdentityService } from 'app/entities/owner-identity/owner-identity.service';
import { OwnerIdentity } from 'app/shared/model/owner-identity.model';

describe('Component Tests', () => {
    describe('OwnerIdentity Management Component', () => {
        let comp: OwnerIdentityComponent;
        let fixture: ComponentFixture<OwnerIdentityComponent>;
        let service: OwnerIdentityService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhonlineTestModule],
                declarations: [OwnerIdentityComponent],
                providers: [OwnerIdentityService]
            })
                .overrideTemplate(OwnerIdentityComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(OwnerIdentityComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OwnerIdentityService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                Observable.of(
                    new HttpResponse({
                        body: [new OwnerIdentity(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.ownerIdentities[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
