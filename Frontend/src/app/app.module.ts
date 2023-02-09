import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HomeComponent } from './components/home/home.component';
import { ReportsComponent } from './components/reports/reports.component';
import { UploadComponent } from './components/upload/upload.component';
import { MatButtonModule } from "@angular/material/button";
import { MatToolbarModule } from "@angular/material/toolbar";
import { HttpClientModule } from "@angular/common/http";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatInputModule } from "@angular/material/input";
import { MatTableModule } from "@angular/material/table";
import { MatSelectModule } from '@angular/material/select';
import { MatOptionModule } from '@angular/material/core';
import { SearchComponent } from './components/search/search.component';
import { MatCardModule } from "@angular/material/card";
import { SearchResultComponent } from './components/search-result/search-result.component'

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    ReportsComponent,
    UploadComponent,
    SearchComponent,
    SearchResultComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatButtonModule,
    MatToolbarModule,
    HttpClientModule,
    MatFormFieldModule,
    MatInputModule,
    MatTableModule,
    MatCardModule,
    MatSelectModule,
    MatOptionModule

  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
