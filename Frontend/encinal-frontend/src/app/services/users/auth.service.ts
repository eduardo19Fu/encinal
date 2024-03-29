import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

import { User } from '../../models/user';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  // tslint:disable-next-line: variable-name
  private _user: User;
  // tslint:disable-next-line: variable-name
  private _token: string;

  url: string;

  constructor(
    private httpClient: HttpClient
  ) {
    this.url = 'http://localhost:8180';
    // this.url = 'https://encinal-oficial.herokuapp.com';
    // this.url = 'https://condadoelencinal.com:8180';
  }

  public get user(): User{
    if (this._user != null){
      return this._user;
    } else if (this._user == null && sessionStorage.getItem('user')){
      this._user = JSON.parse(sessionStorage.getItem('user')) as User;
      return this._user;
    }
    return new User();
  }

  public get token(): string{
    if (this._token != null){
      return this._token;
    } else if (this._token == null && sessionStorage.getItem('token')){
      this._token = sessionStorage.getItem('token');
      return this._token;
    }
    return null;
  }

  login(user: User): Observable<any>{
    const urlEndpoint = this.url + '/oauth/token';
    const credentials = btoa('encinalfrontend' + ':' + 'pangosoftfees2021');
    const httpHeaders = new HttpHeaders({ 'Content-Type': 'application/x-www-form-urlencoded', Authorization: 'Basic ' + credentials });

    const params = new URLSearchParams();
    params.set('grant_type', 'password');
    params.set('username', user.username);
    params.set('password', user.password);

    return this.httpClient.post<any>(urlEndpoint, params.toString(), {headers: httpHeaders});
  }

  guardarToken(accessToken: string): void {
    this._token = accessToken;
    sessionStorage.setItem('token', this._token);
  }

  guardarUsuario(accessToken: string): void {

    const payload = this.obtenerDatos(accessToken);
    this._user = new User();
    this._user.userId = payload.idUsuario;
    this._user.firstName = payload.primerNombre;
    this._user.middleName = payload.segundoNombre;
    this._user.lastName = payload.apellido;
    this._user.username = payload.user_name;
    this._user.roles = payload.authorities;

    sessionStorage.setItem('user', JSON.stringify(this._user));
  }

  obtenerDatos(accessToken: string): any{
    if (accessToken != null){
      return JSON.parse(atob(accessToken.split('.')[1]));
    }

    return null;
  }

  isAuthenticated(): boolean{
    const payload = this.obtenerDatos(this.token);
    if (payload != null && payload.user_name && payload.user_name.length > 0){
      return true;
    }
    return false;
  }

  logout(): void{
    this._token = null;
    this._user = null;
    sessionStorage.clear();
    sessionStorage.removeItem('token');
    sessionStorage.removeItem('user');
  }

  hasRole(role: string): boolean{
    if (this.user.roles.includes(role)){
      return true;
    }
    return false;
  }
}
