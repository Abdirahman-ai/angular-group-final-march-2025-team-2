import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AnnouncementService } from '../../services/announcement.service';
import { Announcement } from 'src/app/models/announcement.model';
import { NavigationEnd } from '@angular/router';

@Component({
  selector: 'app-announcements',
  templateUrl: './announcements.component.html',
  styleUrls: ['./announcements.component.css']
})
export class AnnouncementsComponent implements OnInit {
  companyId!: number;
  announcements: Announcement[] = [];
  editingAnnouncement: Announcement | null = null;

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

    this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd && this.router.url === '/announcements') {
        this.loadAnnouncements?.();
      }
    });
  }

  ngOnInit(): void {
    const storedUser = localStorage.getItem('user');
    if (storedUser) {
      this.user = JSON.parse(storedUser);
      this.isAdmin = this.user?.admin === true;
    }

    if (!this.companyId) {
      const storedCompanyId = localStorage.getItem('companyId');
      if (storedCompanyId) {
        this.companyId = +storedCompanyId;
      }
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

    if (this.editingAnnouncement) {
      this.announcementService.updateAnnouncement(this.editingAnnouncement.id, payload).subscribe({
        next: () => {
          this.resetForm();
          this.loadAnnouncements();
        },
        error: (err) => console.error('Error updating announcement:', err)
      });
    }
    else {
      this.announcementService.createAnnouncement(this.companyId, payload).subscribe({
        next: () => {
          this.showForm = false;
          this.newAnnouncement = { title: '', message: '' };
          this.loadAnnouncements();
        },
        error: (err) => console.error('Error creating announcement:', err)
      });
    }
  }

  startEdit(ann: Announcement): void {
    this.editingAnnouncement = { ...ann };
    this.showForm = true;
    this.newAnnouncement = {
      title: ann.title,
      message: ann.message
    };
  }

  resetForm(): void {
    this.showForm = false;
    this.editingAnnouncement = null;
    this.newAnnouncement = { title: '', message: '' };
  }

  deleteAnnouncement(id: number): void {
    if (confirm('Are you sure you want to delete this announcement?')) {
      this.announcementService.deleteAnnouncement(id).subscribe({
        next: () => this.loadAnnouncements(),
        error: (err) => console.error('Error deleting announcement:', err)
      });
    }
  }
}
