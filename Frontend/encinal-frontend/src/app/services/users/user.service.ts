import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { global } from '../global';
import Swal from 'sweetalert2';
import { Observable, throwError } from 'rxjs';
import { User } from 'src/app/models/user';
import { catchError } from 'rxjs/operators';
import { Role } from '../../models/role';
import { UserAuxiliar } from '../../models/user-auxiliar';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  url: string;
  httpHeaders = new HttpHeaders().set('Content-Type', 'application/json');

  constructor(
    private httpClient: HttpClient
  ) {
    this.url = global.url;
  }

  getUsers(): Observable<User[]>{
    return this.httpClient.get<User[]>(`${this.url}/users`);
  }

  getUser(id: number): Observable<any>{
    return this.httpClient.get<any>(`${this.url}/users/${id}`).pipe(
      catchError(e => {
        Swal.fire(e.error.message, e.error.error, 'error');
        return throwError(e);
      })
    );
  }

  create(user: UserAuxiliar): Observable<any>{
    return this.httpClient.post<any>(`${this.url}/users`, user).pipe(
      catchError(e => {
        Swal.fire(e.error.message, e.error.error, 'error');
        return throwError(e);
      })
    );
  }

  update(user: UserAuxiliar): Observable<any>{
    return this.httpClient.put<any>(`${this.url}/users/${user.userId}`, user).pipe(
      catchError(e => {
        Swal.fire(e.error.message, e.error.error, 'error');
        return throwError(e);
      })
      );
  }

  delete(user: User): Observable<any>{
    return this.httpClient.put<any>(`${this.url}/users/${user.userId}`, user).pipe(
      catchError(e => {
        Swal.fire(e.error.message, e.error.error, 'error');
        return throwError(e);
      })
    );
  }

  /*** ROLES ***/

  getRoles(): Observable<Role[]>{
    return this.httpClient.get<Role[]>(`${this.url}/roles`);
  }
}
