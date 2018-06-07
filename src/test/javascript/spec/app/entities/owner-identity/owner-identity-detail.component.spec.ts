/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs/observable/of';

import { JhonlineTestModule } from '../../../test.module';
import { OwnerIdentityDetailComponent } from 'app/entities/owner-identity/owner-identity-detail.component';
import { OwnerIdentity } from 'app/shared/model/owner-identity.model';

describe('Component Tests', () => {
    describe('OwnerIdentity Management Detail Component', () => {
        let comp: OwnerIdentityDetailComponent;
        let fixture: ComponentFixture<OwnerIdentityDetailComponent>;
        const route = ({ data: of({ ownerIdentity: new OwnerIdentity(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhonlineTestModule],
                declarations: [OwnerIdentityDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(OwnerIdentityDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(OwnerIdentityDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.ownerIdentity).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
