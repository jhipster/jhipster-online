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
import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { Subject } from 'rxjs';

import { JhonlineTestModule } from '../../../test.module';
import { GeneratorComponent } from 'app/home/generator/generator.component';
import { GeneratorService } from 'app/home/generator/generator.service';
import { GitConfigurationService } from 'app/core/git/git-configuration.service';
import { GitConfigurationModel } from 'app/core/git/git-configuration.model';

describe('Component Tests', () => {
  describe('GeneratorComponent', () => {
    let comp: GeneratorComponent;
    let fixture: ComponentFixture<GeneratorComponent>;

    beforeEach(
      waitForAsync(() => {
        TestBed.configureTestingModule({
          imports: [JhonlineTestModule],
          declarations: [GeneratorComponent],
          providers: [
            GeneratorService,
            {
              provide: GitConfigurationService,
              useValue: {
                gitConfig: new GitConfigurationModel([], false),
                sharedData: new Subject<GitConfigurationModel>()
              }
            }
          ]
        })
          .overrideTemplate(GeneratorComponent, '')
          .compileComponents();
      })
    );

    beforeEach(() => {
      fixture = TestBed.createComponent(GeneratorComponent);
      comp = fixture.componentInstance;
      comp.ngOnInit();
    });

    it('should disable client generation options when applicationType is microservice', () => {
      comp.model.testFrameworks = ['gatling', 'cypress', 'protractor'];
      comp.model.applicationType = 'microservice';

      comp.changeApplicationType();

      expect(comp.model.serverPort).toBe(8081);
      expect(comp.model.serviceDiscoveryType).toBe('consul');
      expect(comp.model.skipClient).toBe(true);
      expect(comp.model.clientFramework).toBe('no');
      expect(comp.model.withAdminUi).toBe(false);
      expect(comp.model.skipUserManagement).toBe(true);
      expect(comp.model.testFrameworks).toEqual(['gatling']);

      comp.changeAuthenticationType();
      expect(comp.model.clientFramework).toBe('no');

      comp.model.databaseType = 'couchbase';
      comp.changeDatabaseType();
      expect(comp.model.prodDatabaseType).toBe('couchbase');
      expect(comp.model.devDatabaseType).toBe('couchbase');
      expect(comp.model.cacheProvider).toBe('no');
      expect(comp.model.enableHibernateCache).toBe(false);

      comp.checkModelBeforeSubmit();
      expect(comp.model.skipClient).toBe(true);
      expect(comp.model.clientFramework).toBe('no');
      expect(comp.model.withAdminUi).toBe(false);
    });

    it('should restore default client framework when switching from microservice back to monolith', () => {
      comp.model.applicationType = 'microservice';
      comp.changeApplicationType();

      comp.model.applicationType = 'monolith';
      comp.changeApplicationType();

      expect(comp.model.serverPort).toBe(8080);
      expect(comp.model.skipClient).toBe(false);
      expect(comp.model.clientFramework).toBe('angularX');
      expect(comp.model.withAdminUi).toBe(true);
    });

    it('should update dev and prod database types for neo4j', () => {
      comp.model.databaseType = 'neo4j';
      comp.changeDatabaseType();

      expect(comp.model.prodDatabaseType).toBe('neo4j');
      expect(comp.model.devDatabaseType).toBe('neo4j');
      expect(comp.model.cacheProvider).toBe('no');
      expect(comp.model.enableHibernateCache).toBe(false);
    });
  });
});
