import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { PaymentAgreement } from 'src/app/models/payment-agreement';
import { Client } from 'src/app/models/client';

import { global } from '../global';
import Swal from 'sweetalert2';


@Injectable({
  providedIn: 'root'
})
export class PaymentAgreementService {

  url: string;
  httpHeaders = new HttpHeaders().set('Content-Type', 'application/json');

  constructor(
    private httpClient: HttpClient
  ) {
    this.url = global.url;
  }

  getPaymentAgreements(): Observable<PaymentAgreement[]> {
    return this.httpClient.get<PaymentAgreement[]>(`${this.url}/payment-agreements`);
  }

  getPaymentAgreement(id: number): Observable<any> {
    return this.httpClient.get<any>(`${this.url}/payment-agreement/${id}`).pipe(
      catchError(e => {
        Swal.fire(e.error.message, e.error.error, 'error');
        return throwError(e);
      })
    );
  }

  getActivePaymentAgreements(client: Client): Observable<any>{
    return this.httpClient.get<any>(`${this.url}/payment-agreements/client/${client.clientId}`).pipe(
      catchError(e => {
        Swal.fire(e.error.message, e.error.error, 'error');
        return throwError(e);
      })
    );
  }

  create(paymentAgreement: PaymentAgreement): Observable<any> {
    return this.httpClient.post<any>(`${this.url}/payment-agreements`, paymentAgreement).pipe(
      catchError(e => {
        Swal.fire(e.error.message, e.error.error, 'error');
        return throwError(e);
      })
    );
  }
}
