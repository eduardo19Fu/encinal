import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { IdentificationType } from '../../models/identification-type';
import { catchError } from 'rxjs/operators';

import { global } from '../global';
import Swal from 'sweetalert2';

@Injectable({
  providedIn: 'root'
})
export class IdentificationTypeService {

  url: string;
  httpHeaders = new HttpHeaders().set('Content-Type', 'application/json');

  constructor(
    private httpClient: HttpClient
  ) {
    this.url = global.url;
  }

  getIdentificationsTypes(): Observable<IdentificationType[]>{
    return this.httpClient.get<IdentificationType[]>(`${this.url}/identifications`, {headers: this.httpHeaders});
  }

  getIdentificationType(id: number): Observable<IdentificationType>{
    return this.httpClient.get<IdentificationType>(`${this.url}/identifications/${id}`, {headers: this.httpHeaders});
  }

  create(identificationType: IdentificationType): Observable<any>{
    return this.httpClient.post<any>(`${this.url}/identifications`, identificationType, {headers: this.httpHeaders}).pipe(
      catchError(e => {
        Swal.fire(e.error.message, e.error.error, 'error');
        return throwError(e);
      })
    );
  }

  delete(id: number): Observable<any>{
    return this.httpClient.delete<any>(`${this.url}/identifications/${id}`, {headers: this.httpHeaders}).pipe(
      catchError(e => {
        Swal.fire(e.error.message, e.error.error);
        return throwError(e);
      })
    );
  }
}
