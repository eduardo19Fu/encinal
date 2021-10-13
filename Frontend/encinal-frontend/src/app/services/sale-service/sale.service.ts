import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient, HttpParams } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { Sale } from 'src/app/models/sale';
import { SaleType } from 'src/app/models/sale-type';
import { global } from '../global';

import Swal from 'sweetalert2';

@Injectable({
  providedIn: 'root'
})
export class SaleService {

  url: string;
  httpHeaders = new HttpHeaders().set('Content-Type', 'application/json');

  constructor(
    private httpClient: HttpClient
  ) {
    this.url = global.url;
  }

  getSales(): Observable<Sale[]> {
    return this.httpClient.get<Sale[]>(`${this.url}/sales`);
  }

  getSalesByDate(initDate: Date, endDate: Date): Observable<Sale[]> {
    const httpParams = new HttpParams()
      .set('initDate', initDate.toString())
      .set('endDate', endDate.toString());

    return this.httpClient.get<Sale[]>(`${this.url}/sales`, { params: httpParams });
  }

  create(sale: Sale): Observable<any> {
    return this.httpClient.post<any>(`${this.url}/sales`, sale).pipe(
      catchError(e => {
        Swal.fire(e.error.message, e.error.error, 'error');
        return throwError(e);
      })
    );
  }

  // List Sale Types
  getSaleTypes(): Observable<SaleType[]> {
    return this.httpClient.get<SaleType[]>(`${this.url}/sales/sales-types`);
  }
}
