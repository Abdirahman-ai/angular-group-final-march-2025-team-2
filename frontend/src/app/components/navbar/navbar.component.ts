import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
  user: any;
  isAdmin: boolean = false;

  ngOnInit(): void {
    const storedUser = localStorage.getItem('user');
    if (storedUser) {
      this.user = JSON.parse(storedUser);
      this.isAdmin = this.user.isAdmin === true;
    }
  }

  logout(): void {
    localStorage.clear();
    window.location.href = '/login'; // force refresh
  }
}
