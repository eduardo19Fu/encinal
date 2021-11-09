import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { Receipt } from 'src/app/models/receipt';

import { global } from '../global';
import Swal from 'sweetalert2';

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

}
