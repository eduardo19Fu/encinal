import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/models/user';
import { AuthService } from '../../services/users/auth.service';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent implements OnInit {

  public user: User;

  constructor(
    public router: Router,
    public authService: AuthService
  ) {
    this.user = new User();
  }

  ngOnInit(): void {
    if (sessionStorage.getItem('user') != null){
      this.user = this.authService.user;
    }
  }

  logout(): void{
    this.authService.logout();
  }

}
