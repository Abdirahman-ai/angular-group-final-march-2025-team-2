import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Team } from '../models/team.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TeamService {

  private baseUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) { }

  createTeam(companyId: number, team: Partial<Team>): Observable<Team> {
    return this.http.post<Team>(`${this.baseUrl}/team/${companyId}/teams`, team);
  }

  addUserToTeam(teamId: number, userId: number): Observable<Team> {
    return this.http.post<Team>(`${this.baseUrl}/team/${teamId}/add-user/${userId}`, {});
  }

  removeUserFromTeam(teamId: number, userId: number): Observable<Team> {
    return this.http.post<Team>(`${this.baseUrl}/team/${teamId}/remove-user/${userId}`, {});
  }

  getTeams(companyId: number): Observable<Team[]> {
    return this.http.get<Team[]>(`${this.baseUrl}/company/${companyId}/teams`);
  }

  getProjectsForTeam(teamId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/projects/team/${teamId}`);
  }
}
