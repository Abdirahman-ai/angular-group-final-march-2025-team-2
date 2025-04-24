import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-select-company',
  templateUrl: './select-company.component.html',
  styleUrls: ['./select-company.component.css']
})
export class SelectCompanyComponent implements OnInit {
  companies: any[] = [];
  selectedCompanyId: number | null = null;
  currentUser: any = null;

  constructor(private http: HttpClient, private router: Router) {
    const rawUser = localStorage.getItem('user');
    if (rawUser) {
      this.currentUser = JSON.parse(rawUser);
    }
  }

  ngOnInit(): void {
    this.http.get<any[]>('http://localhost:8080/company')
      .subscribe(data => {
        this.companies = data;
      });
  }

  continue() {
    if (this.selectedCompanyId && this.currentUser) {
      localStorage.setItem('companyId', this.selectedCompanyId.toString());
      localStorage.setItem('user', JSON.stringify(this.currentUser));
      
      this.router.navigate(['/announcements'], {
        state: {
          companyId: this.selectedCompanyId,
          user: this.currentUser
        }
      });
    }
  }
}