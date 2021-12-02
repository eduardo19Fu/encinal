import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient, HttpParams } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
// import { DatePipe } from '@angular/common';

import { Sale } from 'src/app/models/sale';
import { SaleType } from 'src/app/models/sale-type';
import { global } from '../global';

import Swal from 'sweetalert2';
import * as moment from 'moment';

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
    // const datepipe: DatePipe = new DatePipe('es');

    // const date1 = datepipe.transform(initDate, 'yyyy-MM-dd');
    // const date2 = datepipe.transform(endDate, 'yyyy-MM-dd');

    const date1 = moment(initDate).format('yyyy-MM-DD');
    const date2 = moment(endDate).format('yyyy-MM-DD');

    console.log(date1);
    console.log(date2);

    const httpParams = new HttpParams()
      .set('initDate', date1)
      .set('endDate', date2);

    return this.httpClient.get<Sale[]>(`${this.url}/sales`, { params: httpParams });
  }

  getSalesByBlock(blockId: number): Observable<Sale[]>{
    const httpParams = new HttpParams()
      .set('idManzana', blockId.toString());

    return this.httpClient.get<Sale[]>(`${this.url}/sales`, { params: httpParams });
  }

  getSalesByBlockAndDate(blockId: number, initDate: Date, endDate: Date): Observable<Sale[]>{
    // const datepipe: DatePipe = new DatePipe('es');

    // const date1 = datepipe.transform(initDate, 'yyyy-MM-dd');
    // const date2 = datepipe.transform(endDate, 'yyyy-MM-dd');

    const date1 = moment(initDate).format('yyyy-MM-DD');
    const date2 = moment(endDate).format('yyyy-MM-DD');

    const httpParams = new HttpParams()
      .set('idManzana', blockId.toString())
      .set('initDate', date1)
      .set('endDate', date2);

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

  cancel(id: number): Observable<any>{
    return this.httpClient.delete<any>(`${this.url}/sales/cancel/${id}`).pipe(
      catchError(e => {
        Swal.fire(e.error.message, e.error.error, 'error');
        return throwError(e);
      })
    );
  }

  reportDailySales(): Observable<any>{
    return this.httpClient.get<any>(`${this.url}/sales/daily-sales`).pipe(
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
