import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'jdl-metadata',
        loadChildren: () => import('./jdl-metadata/jdl-metadata.module').then(m => m.JhonlineJdlMetadataModule)
      },
      {
        path: 'sub-gen-event',
        loadChildren: () => import('./sub-gen-event/sub-gen-event.module').then(m => m.JhonlineSubGenEventModule)
      },
      {
        path: 'entity-stats',
        loadChildren: () => import('./entity-stats/entity-stats.module').then(m => m.JhonlineEntityStatsModule)
      },
      {
        path: 'language',
        loadChildren: () => import('./language/language.module').then(m => m.JhonlineLanguageModule)
      },
      {
        path: 'generator-identity',
        loadChildren: () => import('./generator-identity/generator-identity.module').then(m => m.JhonlineGeneratorIdentityModule)
      },
      {
        path: 'user-application',
        loadChildren: () => import('./user-application/user-application.module').then(m => m.JhonlineUserApplicationModule)
      },
      {
        path: 'yo-rc',
        loadChildren: () => import('./yo-rc/yo-rc.module').then(m => m.JhonlineYoRCModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class JhonlineEntityModule {}
