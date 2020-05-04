import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhonlineSharedModule } from 'app/shared/shared.module';

import { ConfigurationComponent } from './configuration.component';

import { configurationRoute } from './configuration.route';
import { NgJhipsterModule } from 'ng-jhipster';
import { FormsModule } from '@angular/forms';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';

@NgModule({
  imports: [JhonlineSharedModule, RouterModule.forChild([configurationRoute]), NgJhipsterModule, FormsModule, FontAwesomeModule],
  declarations: [ConfigurationComponent]
})
export class ConfigurationModule {}
