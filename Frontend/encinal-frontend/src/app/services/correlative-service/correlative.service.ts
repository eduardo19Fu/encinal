import { Injectable } from '@angular/core';
import { global } from '../global';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { Correlative } from 'src/app/models/correlative';
import { catchError } from 'rxjs/operators';

import Swal from 'sweetalert2';

@Injectable({
  providedIn: 'root'
})
export class CorrelativeService {

  url: string;

  constructor(
    private httpClient: HttpClient
  ) {
    this.url = global.url;
  }

  getCorrelatives(): Observable<Correlative[]>{
    return this.httpClient.get<Correlative[]>(`${this.url}/correlatives`);
  }

  getCorrelative(id: number): Observable<any>{
    return this.httpClient.get<any>(`${this.url}/correlatives/${id}`).pipe(
      catchError(e => {
        Swal.fire(e.error.message, e.error.error, 'error');
        return throwError(e);
      })
    );
  }

  create(correlative: Correlative): Observable<any>{
    return this.httpClient.post<any>(`${this.url}/correlatives`, correlative).pipe(
      catchError(e => {
        Swal.fire(e.error.message, e.error.error, 'error');
        return throwError(e);
      })
    );
  }

  update(correlative: Correlative): Observable<any>{
    return this.httpClient.put<any>(`${this.url}/correlatives`, correlative).pipe(
      catchError(e => {
        Swal.fire(e.error.message, e.error.error, 'error');
        return throwError(e);
      })
    );
  }

  cancel(id: number): Observable<any>{
    return null;
  }
}
