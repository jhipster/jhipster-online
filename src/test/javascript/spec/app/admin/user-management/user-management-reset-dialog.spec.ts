import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ActivatedRoute } from '@angular/router';
import { JhiEventManager } from 'ng-jhipster';
import { of } from 'rxjs/observable/of';

import { UserService, PasswordResetService, User } from 'app/core';
import { UserMgmtResetDialogComponent } from 'app/admin/user-management/user-management-reset-dialog.component';
import { JhonlineTestModule } from '../../../test.module';

describe('Component Tests', () => {
    describe('User Management Reset Component', () => {
        let comp: UserMgmtResetDialogComponent;
        let fixture: ComponentFixture<UserMgmtResetDialogComponent>;
        let userService: UserService;
        let passwordResetService: PasswordResetService;
        let mockEventManager: any;
        let mockActiveModal: any;
        const route = ({
            data: of({ user: new User(1, 'user', 'first', 'last', 'first@last.com', true, 'en', ['ROLE_USER'], 'admin', null, null, null) })
        } as any) as ActivatedRoute;

        beforeEach(
            async(() => {
                TestBed.configureTestingModule({
                    imports: [JhonlineTestModule],
                    declarations: [UserMgmtResetDialogComponent],
                    providers: [
                        UserService,
                        PasswordResetService,
                        {
                            provide: ActivatedRoute,
                            useValue: route
                        }
                    ]
                })
                    .overrideTemplate(UserMgmtResetDialogComponent, '')
                    .compileComponents();
            })
        );

        beforeEach(() => {
            fixture = TestBed.createComponent(UserMgmtResetDialogComponent);
            userService = fixture.debugElement.injector.get(UserService);
            passwordResetService = fixture.debugElement.injector.get(PasswordResetService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
            comp = fixture.componentInstance;
            comp.ngOnInit();
        });

        it('should define its initial state', () => {
            expect(comp.showClipboardSuccess).toEqual(false);
            expect(comp.showClipboardError).toEqual(false);

            expect(comp.user).toEqual(
                jasmine.objectContaining({
                    id: 1,
                    login: 'user',
                    firstName: 'first',
                    lastName: 'last',
                    email: 'first@last.com',
                    activated: true,
                    langKey: 'en',
                    authorities: ['ROLE_USER'],
                    createdBy: 'admin',
                    createdDate: null,
                    lastModifiedBy: null,
                    lastModifiedDate: null,
                    password: null
                })
            );
        });
    });
});
