import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { Client } from 'src/app/models/client';
import { catchError } from 'rxjs/operators';

import { global } from '../global';
import Swal from 'sweetalert2';

@Injectable({
  providedIn: 'root'
})
export class ClientService {

  url: string;
  httpHeaders = new HttpHeaders().set('Content-Type', 'application/json');

  constructor(
    private httpClient: HttpClient
  ) {
    this.url = global.url;
  }

  getClients(): Observable<Client[]>{
    return this.httpClient.get<Client[]>(`${this.url}/clients`);
  }

  getClient(id: number): Observable<any>{
    return this.httpClient.get<any>(`${this.url}/clients/${id}`).pipe(
      catchError(e => {
        Swal.fire(e.error.message, 'No se pudo encontrar el registro deseado', 'error');
        return throwError(e);
      })
    );
  }

  getActiveCustomers(): Observable<any>{
    return this.httpClient.get<any>(`${this.url}/clients/active`).pipe(
      catchError(e => {
        Swal.fire(e.error.message, e.error.error, 'error');
        return throwError(e);
      })
    );
  }

  create(client: Client): Observable<any>{
    return this.httpClient.post<any>(`${this.url}/clients`, client).pipe(
      catchError(e => {
        Swal.fire(e.error.message, e.error.error, 'error');
        return throwError(e);
      })
    );
  }

  update(client: Client): Observable<any>{
    return this.httpClient.put<any>(`${this.url}/clients`, client).pipe(
      catchError(e => {
        Swal.fire(e.error.message, e.error.error, 'error');
        return throwError(e);
      })
    );
  }

  disable(client: Client): Observable<any>{
    return this.httpClient.put<any>(`${this.url}/clients/disable/${client.clientId}`, client).pipe(
      catchError(e => {
        Swal.fire(e.error.message, e.error.error, 'error');
        return throwError(e);
      })
    );
  }

  delete(client: Client): Observable<any>{
    return null;
  }
}
