import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login.component';
import { SelectCompanyComponent } from './pages/select-company/select-company.component';
import { AnnouncementsComponent } from './components/announcements/Announcements.component';
import { TeamsComponent } from './pages/teams/teams.component';
import { ProjectsComponent } from './pages/projects/projects.component';

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'select-company', component: SelectCompanyComponent },
  { path: 'announcements', component: AnnouncementsComponent },
  { path: 'teams', component: TeamsComponent },
  { path: 'teams/:teamId/projects', component: ProjectsComponent },
  { path: '', redirectTo: 'login', pathMatch: 'full' }
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
