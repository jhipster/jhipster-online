/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhonlineTestModule } from '../../../test.module';
import { JdlMetadataDetailComponent } from 'app/entities/jdl-metadata/jdl-metadata-detail.component';
import { JdlMetadata } from 'app/shared/model/jdl-metadata.model';

describe('Component Tests', () => {
    describe('JdlMetadata Management Detail Component', () => {
        let comp: JdlMetadataDetailComponent;
        let fixture: ComponentFixture<JdlMetadataDetailComponent>;
        const route = ({ data: of({ jdlMetadata: new JdlMetadata(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhonlineTestModule],
                declarations: [JdlMetadataDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(JdlMetadataDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(JdlMetadataDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.jdlMetadata).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
