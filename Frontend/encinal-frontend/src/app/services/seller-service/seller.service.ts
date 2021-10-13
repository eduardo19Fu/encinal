import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { Seller } from 'src/app/models/seller';

import { global } from '../global';
import Swal from 'sweetalert2';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class SellerService {

  url: string;
  httpHeaders = new HttpHeaders().set('Content-Type', 'application/json');

  constructor(
    private httpClient: HttpClient
  ) {
    this.url = global.url;
  }

  getSellers(): Observable<Seller[]> {
    return this.httpClient.get<Seller[]>(`${this.url}/sellers`);
  }

  getSeller(id: number): Observable<any> {
    return this.httpClient.get<any>(`${this.url}/sellers/${id}`).pipe(
      catchError(e => {
        Swal.fire('Error', e.error.error, 'error');
        return throwError(e);
      })
    );
  }

  create(seller: Seller): Observable<any>{
    return this.httpClient.post<any>(`${this.url}/sellers`, seller).pipe(
      catchError(e => {
        Swal.fire('Error', e.error.error, 'error');
        return throwError(e);
      })
    );
  }

  update(seller: Seller): Observable<any>{
    return this.httpClient.put<any>(`${this.url}/sellers`, seller).pipe(
      catchError(e => {
        Swal.fire('Error', e.error.error, 'error');
        return throwError(e);
      })
    );
  }
}
