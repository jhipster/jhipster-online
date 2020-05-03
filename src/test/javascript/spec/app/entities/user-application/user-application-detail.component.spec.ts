/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhonlineTestModule } from '../../../test.module';
import { UserApplicationDetailComponent } from 'app/entities/user-application/user-application-detail.component';
import { UserApplication } from 'app/shared/model/user-application.model';

describe('Component Tests', () => {
    describe('UserApplication Management Detail Component', () => {
        let comp: UserApplicationDetailComponent;
        let fixture: ComponentFixture<UserApplicationDetailComponent>;
        const route = ({ data: of({ userApplication: new UserApplication(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhonlineTestModule],
                declarations: [UserApplicationDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(UserApplicationDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(UserApplicationDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.userApplication).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
