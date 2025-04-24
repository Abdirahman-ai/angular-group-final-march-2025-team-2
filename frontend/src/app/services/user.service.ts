import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { FullUser } from '../models/user.model';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private baseUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) {}

  getUsersByCompany(companyId: number): Observable<FullUser[]> {
    return this.http.get<FullUser[]>(`${this.baseUrl}/company/${companyId}/users`);
  }

  createUser(userData: any): Observable<FullUser> {
    return this.http.post<FullUser>(`${this.baseUrl}/users`, userData);
  }
}

