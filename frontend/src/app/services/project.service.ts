import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Project } from '../models/project.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProjectService {

  private baseUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) { }

  getProjectsForTeam(teamId: number): Observable<Project[]> {
    return this.http.get<Project[]>(`${this.baseUrl}/projects/team/${teamId}`);
  }
}
