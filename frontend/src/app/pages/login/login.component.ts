import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  credentials = {
    username: '',
    password: ''
  };

  constructor(private http: HttpClient, private router: Router) {}

  onSubmit() {
    this.http.post('http://localhost:8080/users/login', this.credentials)
    .subscribe({
      next: (user: any) => {
        localStorage.setItem('user', JSON.stringify(user));
        alert('Login successful! 🎉');
        this.router.navigate(['/select-company']);
      },
      error: () => {
        alert('Invalid login');
      }
    });
  }
}
