import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from 'src/app/services/user.service';
import { FullUser } from 'src/app/models/user.model';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {
  companyId!: number;
  users: FullUser[] = [];
  isAdmin = false;

  constructor(private router: Router, private userService: UserService) {
    const nav = this.router.getCurrentNavigation();
    const state = nav?.extras?.state;
    if (state) {
      this.companyId = state['companyId'];
      this.isAdmin = state['user']?.admin || false;
    }
  }

  ngOnInit(): void {
    const rawUser = localStorage.getItem('user');
    const rawCompanyId = localStorage.getItem('companyId');

    if (!rawUser || !rawCompanyId) {
      console.error(' Missing user or companyId in localStorage.');
      return;
    }

    this.companyId = parseInt(rawCompanyId, 10);
    const parsedUser = JSON.parse(rawUser);
    this.isAdmin = parsedUser?.admin || false;

    this.userService.getUsersByCompany(this.companyId).subscribe({
      next: (data) => this.users = data,
      error: (err) => console.error('Failed to load users:', err)
    });
  }

showUserForm = false;

newUser = {
  firstName: '',
  lastName: '',
  email: '',
  phone: '',
  username: '',
  password: '',
  isAdmin: false
};

toggleUserForm() {
  this.showUserForm = !this.showUserForm;
}

isFormValid(): boolean {
  const u = this.newUser;
  return !!(u.firstName.trim() && u.lastName.trim() && u.email.trim() &&
            u.phone.trim() && u.username.trim() && u.password.trim());
}


createUser() {
  const payload = {
    profile: {
      firstName: this.newUser.firstName,
      lastName: this.newUser.lastName,
      email: this.newUser.email,
      phone: this.newUser.phone
    },
    credentials: {
      username: this.newUser.username,
      password: this.newUser.password
    },
    admin: this.newUser.isAdmin
  };

  this.userService.createUser(payload).subscribe({
    next: (user) => {
      this.users.push(user);
      this.users.sort((a, b) => a.profile.firstName.localeCompare(b.profile.firstName));
      this.toggleUserForm();
      this.newUser = {
        firstName: '',
        lastName: '',
        email: '',
        phone: '',
        username: '',
        password: '',
        isAdmin: false
      };
    },
    error: (err) => console.error('User creation failed', err)
  });
}
}
