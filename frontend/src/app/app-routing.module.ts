import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login.component';
import { SelectCompanyComponent } from './pages/select-company/select-company.component';
import { AnnouncementsComponent } from './components/announcements/Announcements.component';
import { UsersComponent } from './pages/users/users.component';

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'select-company', component: SelectCompanyComponent },
  { path: 'announcements', component: AnnouncementsComponent },
  { path: 'users', component: UsersComponent },
  { path: '', redirectTo: 'login', pathMatch: 'full' }
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
