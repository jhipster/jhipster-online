import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ActivatedRoute } from '@angular/router';
import { JhiEventManager } from 'ng-jhipster';
import { of } from 'rxjs/observable/of';

import { UserService, PasswordResetService, User } from 'app/core';
import { UserMgmtResetDialogComponent } from 'app/admin/user-management/user-management-reset-dialog.component';
import { JhonlineTestModule } from '../../../test.module';
import { Observable } from 'rxjs/Observable';
import { HttpResponse } from '@angular/common/http';

describe('Component Tests', () => {
    describe('User Management Reset Component', () => {
        let comp: UserMgmtResetDialogComponent;
        let fixture: ComponentFixture<UserMgmtResetDialogComponent>;
        let service: PasswordResetService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(
            async(() => {
                TestBed.configureTestingModule({
                    imports: [JhonlineTestModule],
                    declarations: [UserMgmtResetDialogComponent],
                    providers: [PasswordResetService]
                })
                    .overrideTemplate(UserMgmtResetDialogComponent, '')
                    .compileComponents();
            })
        );

        beforeEach(() => {
            fixture = TestBed.createComponent(UserMgmtResetDialogComponent);
            service = fixture.debugElement.injector.get(PasswordResetService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
            comp = fixture.componentInstance;

            spyOn(service, 'getResetLink').and.returnValue(Observable.of('http://dummy-link.com/'));

            comp.ngOnInit();
        });

        it('should define its initial state', () => {
            expect(comp.showClipboardSuccess).toEqual(false);
            expect(comp.showClipboardError).toEqual(false);
            expect(comp.isGeneratingLink).toEqual(false);
            expect(comp.resetLink).toEqual('');
        });

        it('should generate a reset link', () => {
            comp.generateResetLink('real@email.com');
            expect(comp.resetLink).toEqual('http://dummy-link.com/');
        });
    });
});
