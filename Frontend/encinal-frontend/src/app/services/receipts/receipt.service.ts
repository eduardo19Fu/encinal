import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';

import { Receipt } from 'src/app/models/receipt';

import { global } from '../global';
import Swal from 'sweetalert2';
import { ReceiptType } from 'src/app/models/receipt-type';

@Injectable({
  providedIn: 'root'
})
export class ReceiptService {

  url: string;

  constructor(
    private httpClient: HttpClient
  ) {
    this.url = global.url;
  }

  getReceipts(): Observable<Receipt[]>{
    return this.httpClient.get<Receipt[]>(`${this.url}/receipts`);
  }

  getReceipt(id: number): Observable<any>{
    return this.httpClient.get<any>(`${this.url}/receipt/${id}`).pipe(
      catchError(e => {
        Swal.fire(e.error.message, e.error.error, 'error');
        return throwError(e);
      })
    );
  }

  create(receipt: Receipt): Observable<any>{
    return this.httpClient.post<any>(`${this.url}/receipts`, receipt).pipe(
      catchError(e => {
        Swal.fire(e.error.message, e.error.error, 'error');
        return throwError(e);
      })
    );
  }

  cancel(receipt: Receipt): Observable<any>{
    return this.httpClient.delete<any>(`${this.url}/receipts/cancel/${receipt.receiptId}`).pipe(
      catchError(e => {
        Swal.fire(e.error.message, e.error.error, 'error');
        return throwError(e);
      })
    );
  }

  getTypes(): Observable<ReceiptType[]>{
    return this.httpClient.get<ReceiptType[]>(`${this.url}/receipts/receipt-types`);
  }

  getType(id: number): Observable<any>{
    return this.httpClient.get<any>(`${this.url}/receipts/receipt-type/${id}`).pipe(
      catchError(e => {
        Swal.fire(e.error.message, e.error.error, 'error');
        return throwError(e);
      })
    );
  }

  /*********** RECEIPT PDF **************/
  getReceiptPDF(id: number): Observable<any>{
    const headers = new HttpHeaders();
    headers.append('Accept', 'application/pdf');
    const requestOptions: any = { headers, responseType: 'blob' };

    return this.httpClient.get<any>(`${this.url}/receipts/generate/${id}`, requestOptions).pipe(
      map((response: any) => {
        return{
          filename: 'receipt.pdf',
          data: new Blob([response], { type: 'application/pdf' })
        };
      })
    );
  }

}
