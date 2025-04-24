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

  createProject(teamId: number, project: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/projects/team/${teamId}`, project);
  }

  updateProject(projectId: number, updatedProject: any): Observable<any> {
    return this.http.put(`${this.baseUrl}/projects/${projectId}`, updatedProject);
  }
  
}
