import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { User } from '../../models/user';
import { AuthService } from '../../services/users/auth.service';

import Swal from 'sweetalert2';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  title: string;
  usernameTitle: string;
  passwordTitle: string;

  user: User;

  constructor(
    private authService: AuthService,
    private router: Router
  ) {
    this.title = 'Iniciar Sesión';
    this.usernameTitle = 'Usuario';
    this.passwordTitle = 'Contraseña';
    this.user = new User();
  }

  ngOnInit(): void {
    this.authService.logout();
  }

  login(): void{
    if (this.user.username == null || this.user.password == null){
      Swal.fire('Error', 'Username o Password se encuentran vacíos', 'error');
      return;
    }

    this.authService.login(this.user).subscribe(
      response => {
        console.log(response);
        const payload = JSON.parse(atob(response.access_token.split('.')[1]));
        console.log(payload);

        this.authService.guardarUsuario(response.access_token);
        this.authService.guardarToken(response.access_token);

        window.location.href = '/home';
      },
      error => {
        if (error.status === 400) {
          Swal.fire('Error de Autenticación', 'Usuario y/o contraseña incorrectos', 'error');
        }
      }
    );
  }

}
