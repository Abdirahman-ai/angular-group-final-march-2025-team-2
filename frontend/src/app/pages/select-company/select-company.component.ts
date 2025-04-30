import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CompanyService } from 'src/app/services/company.service';

@Component({
  selector: 'app-select-company',
  templateUrl: './select-company.component.html',
  styleUrls: ['./select-company.component.css']
})
export class SelectCompanyComponent implements OnInit {
  companies: any[] = [];
  selectedCompanyId: number | null = null;
  currentUser: any = null;
  isAdmin: boolean = false;

  showCreateForm = false;
  newCompany = {
    name: '',
    description: ''
  };

  constructor(
    private companyService: CompanyService,
    private router: Router
  ) {}

  ngOnInit(): void {
    const rawUser = localStorage.getItem('user');
    if (rawUser) {
      this.currentUser = JSON.parse(rawUser);
      this.isAdmin = this.currentUser?.admin === true;
    }

    this.companyService.getAllCompanies().subscribe(data => {
      this.companies = data;
    });
  }

  continue(): void {
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

  openCompanyForm(): void {
    this.showCreateForm = true;
  }

  closeCompanyForm(): void {
    this.showCreateForm = false;
    this.newCompany = { name: '', description: '' };
  }

  createCompany(): void {
    this.companyService.createCompany(this.newCompany).subscribe({
      next: (createdCompany) => {
        this.companies.push(createdCompany);
        this.closeCompanyForm();
      },
      error: (err) => console.error('Failed to create company:', err)
    });
  }
}
