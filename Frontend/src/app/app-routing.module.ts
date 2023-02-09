import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { ReportsComponent } from './components/reports/reports.component';
import { SearchResultComponent } from './components/search-result/search-result.component';
import { SearchComponent } from './components/search/search.component';
import { UploadComponent } from './components/upload/upload.component';

const routes: Routes = [
  {
    path: 'home',
    component:  HomeComponent
  },
  {
    path: 'upload',
    component:  UploadComponent
  },
  {
    path: 'reports',
    component:  ReportsComponent
  },
  {
    path: 'search',
    component: SearchComponent
  },
  {
    path: 'search-result',
    component: SearchResultComponent
  },
  {
    path: '**', redirectTo: 'home'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
