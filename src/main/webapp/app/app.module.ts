import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { JhonlineSharedModule } from 'app/shared/shared.module';
import { JhonlineCoreModule } from 'app/core/core.module';
import { JhonlineAppRoutingModule } from './app-routing.module';
import { JhonlineHomeModule } from './home/home.module';
import { JhonlineEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    JhonlineSharedModule,
    JhonlineCoreModule,
    JhonlineHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    JhonlineEntityModule,
    JhonlineAppRoutingModule
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent]
})
export class JhonlineAppModule {}
