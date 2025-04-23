import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AnnouncementService } from '../../services/announcement.service';
import { Announcement } from 'src/app/models/announcement.model';

@Component({
  selector: 'app-announcements',
  templateUrl: './announcements.component.html',
  styleUrls: ['./announcements.component.css']
})
export class AnnouncementsComponent implements OnInit {
  companyId!: number;
  announcements: Announcement[] = [];

  showForm = false;
  newAnnouncement = {
    title: '',
    message: ''
  };

  user: any;
  isAdmin: boolean = false;

  constructor(
    private router: Router,
    private announcementService: AnnouncementService
  ) {
    const nav = this.router.getCurrentNavigation();
    this.companyId = nav?.extras?.state?.['companyId'];
  }

  ngOnInit(): void {
    const storedUser = localStorage.getItem('user');
    if (storedUser) {
      this.user = JSON.parse(storedUser);
      this.isAdmin = this.user?.admin === true;
    }

    if (this.companyId) {
      this.loadAnnouncements();
    }
  }

  loadAnnouncements(): void {
    this.announcementService.getAnnouncements(this.companyId).subscribe({
      next: (data) => this.announcements = data,
      error: (err) => console.error('Error fetching announcements:', err)
    });
  }

  submitAnnouncement(): void {
    const payload = {
      title: this.newAnnouncement.title,
      message: this.newAnnouncement.message,
      author: this.user,
      companyId: this.companyId
    };

    this.announcementService.createAnnouncement(payload).subscribe({
      next: () => {
        this.showForm = false;
        this.newAnnouncement = { title: '', message: '' };
        this.loadAnnouncements();
      },
      error: (err) => console.error('Error creating announcement:', err)
    });
  }
}
