/**
 * Copyright 2017-2024 the original author or authors from the JHipster project.
 *
 * This file is part of the JHipster Online project, see https://github.com/jhipster/jhipster-online
 * for more information.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import { waitForAsync, ComponentFixture, TestBed } from '@angular/core/testing';
import { Router, RouterEvent, NavigationEnd } from '@angular/router';
import { Title } from '@angular/platform-browser';
import { Subject } from 'rxjs';

import { MainComponent } from 'app/layouts/main/main.component';
import { JhonlineTestModule } from '../../../test.module';
import { MockRouter } from '../../../helpers/mock-route.service';

describe('Component Tests', () => {
  describe('MainComponent', () => {
    let comp: MainComponent;
    let fixture: ComponentFixture<MainComponent>;
    let router: MockRouter;
    const routerEventsSubject = new Subject<RouterEvent>();
    let titleService: Title;

    beforeEach(
      waitForAsync(() => {
        TestBed.configureTestingModule({
          imports: [JhonlineTestModule],
          declarations: [MainComponent],
          providers: [Title]
        })
          .overrideTemplate(MainComponent, '')
          .compileComponents();
      })
    );

    beforeEach(() => {
      fixture = TestBed.createComponent(MainComponent);
      comp = fixture.componentInstance;
      router = TestBed.inject(Router) as any;
      router.setEvents(routerEventsSubject.asObservable());
      titleService = TestBed.inject(Title);
    });

    describe('page title', () => {
      let routerState: any;
      const defaultPageTitle = 'Jhonline';
      const parentRoutePageTitle = 'parentTitle';
      const childRoutePageTitle = 'childTitle';
      const navigationEnd = new NavigationEnd(1, '', '');

      beforeEach(() => {
        routerState = { snapshot: { root: {} } };
        router.setRouterState(routerState);
        spyOn(titleService, 'setTitle');
        comp.ngOnInit();
      });

      describe('navigation end', () => {
        it('should set page title to default title if pageTitle is missing on routes', () => {
          // WHEN
          routerEventsSubject.next(navigationEnd);

          // THEN
          expect(titleService.setTitle).toHaveBeenCalledWith(defaultPageTitle);
        });

        it('should set page title to root route pageTitle if there is no child routes', () => {
          // GIVEN
          routerState.snapshot.root.data = { pageTitle: parentRoutePageTitle };

          // WHEN
          routerEventsSubject.next(navigationEnd);

          // THEN
          expect(titleService.setTitle).toHaveBeenCalledWith(parentRoutePageTitle);
        });

        it('should set page title to child route pageTitle if child routes exist and pageTitle is set for child route', () => {
          // GIVEN
          routerState.snapshot.root.data = { pageTitle: parentRoutePageTitle };
          routerState.snapshot.root.firstChild = { data: { pageTitle: childRoutePageTitle } };

          // WHEN
          routerEventsSubject.next(navigationEnd);

          // THEN
          expect(titleService.setTitle).toHaveBeenCalledWith(childRoutePageTitle);
        });

        it('should set page title to parent route pageTitle if child routes exists but pageTitle is not set for child route data', () => {
          // GIVEN
          routerState.snapshot.root.data = { pageTitle: parentRoutePageTitle };
          routerState.snapshot.root.firstChild = { data: {} };

          // WHEN
          routerEventsSubject.next(navigationEnd);

          // THEN
          expect(titleService.setTitle).toHaveBeenCalledWith(parentRoutePageTitle);
        });

        it('should set page title to parent route pageTitle if child routes exists but data is not set for child route', () => {
          // GIVEN
          routerState.snapshot.root.data = { pageTitle: parentRoutePageTitle };
          routerState.snapshot.root.firstChild = {};

          // WHEN
          routerEventsSubject.next(navigationEnd);

          // THEN
          expect(titleService.setTitle).toHaveBeenCalledWith(parentRoutePageTitle);
        });
      });
    });
  });
});
