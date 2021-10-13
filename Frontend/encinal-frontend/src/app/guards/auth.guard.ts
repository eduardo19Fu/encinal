import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';

import { AuthService } from '../services/users/auth.service';

import Swal from 'sweetalert2';


@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(
    private authService: AuthService,
    private router: Router
  ){}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
      if (this.authService.isAuthenticated()){

        if (this.isTokenExpirado()){
          this.authService.logout();
          this.router.navigate(['/login']);
          Swal.fire('Sesión Finalizada', 'El tiempo de la sesión iniciada ha caducado, por favor vuelva a iniciar sesión.', 'warning');
          return false;
        }

        return true;
      }
      this.router.navigate(['/login']);
      return false;
  }

  isTokenExpirado(): boolean{
    const token = this.authService.token;
    const payload = this.authService.obtenerDatos(token);
    const now = new Date().getTime() / 1000;

    if (payload.exp < now){
      return true;
    }

    return false;
  }
}
