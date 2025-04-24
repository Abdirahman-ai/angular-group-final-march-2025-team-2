import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './pages/login/login.component';
import { SelectCompanyComponent } from './pages/select-company/select-company.component';
import { AnnouncementsComponent } from './components/announcements/Announcements.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { TeamsComponent } from './pages/teams/teams.component';
import { ProjectsComponent } from './pages/projects/projects.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    SelectCompanyComponent,
    AnnouncementsComponent,
    NavbarComponent,
    TeamsComponent,
    ProjectsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
