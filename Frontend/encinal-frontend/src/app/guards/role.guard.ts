import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';

import { AuthService } from '../services/users/auth.service';

import Swal from 'sweetalert2';

@Injectable({
  providedIn: 'root'
})
export class RoleGuard implements CanActivate {

  constructor(
    private authService: AuthService,
    private router: Router
  ) { }

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
      if (!this.authService.isAuthenticated()) {
        this.router.navigate(['/login']);
        return false;
      }

      // LUGAR DONDE SE RECIBEN LOS ROLES DEL DATA DESDE LA URL PARA SER COMPROBADOS
      // tslint:disable-next-line: no-string-literal
      const roles = route.data['role'] as string[];
      let hasRole = false;

      roles.forEach(role => {
        if (this.authService.hasRole(role)) {
          hasRole = true;
        }
      });
      if (hasRole) { return true; }

      Swal.fire('Acceso no autorizado', `¡El usuario "${this.authService.user.username}" no posee los permisos necesarios!`, 'warning');
      this.router.navigate(['/home']);
      return false;
    }
}
