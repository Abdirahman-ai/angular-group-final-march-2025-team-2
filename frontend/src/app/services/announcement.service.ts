import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Announcement } from '../models/announcement.model';

@Injectable({
  providedIn: 'root'
})
export class AnnouncementService {

  private baseUrl = 'http://localhost:8080'; // change if needed

  constructor(private http: HttpClient) {}

  getAnnouncements(companyId: number): Observable<Announcement[]> {
    return this.http.get<Announcement[]>(`${this.baseUrl}/company/${companyId}/announcements`);
  }

  createAnnouncement(companyId: number, payload: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/announcements/company/${companyId}`, payload);
  }  
}
