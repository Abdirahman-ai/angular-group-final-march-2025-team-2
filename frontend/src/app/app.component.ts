import { Component } from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'client';

  showNavbar = true;

  constructor(private router: Router){
    this.router.events.subscribe(event =>{
      if(event instanceof NavigationEnd){
        this.showNavbar = !['/login', '/select-company'].includes(event.urlAfterRedirects);
      }
    });
  }
}
