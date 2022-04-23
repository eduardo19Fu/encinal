import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import Swal from 'sweetalert2';

import { Transfer } from '../../models/transfer';
import { global } from '../global';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class TransferService {

  url: string;

  constructor(
    private httpClient: HttpClient
  ) {
    this.url = global.url;
  }

  getTransfers(): Observable<Transfer[]>{
    return this.httpClient.get<Transfer[]>(`${this.url}/transfers`);
  }

  getTransfer(id: number): Observable<any>{
    return this.httpClient.get<any>(`${this.url}/transfers/${id}`).pipe(
      catchError(e => {
        Swal.fire(e.error.message, e.error.error, 'error');
        return throwError(e);
      })
    );
  }

  create(transfer: Transfer): Observable<any>{
    return this.httpClient.post<any>(`${this.url}/transfers`, transfer).pipe(
      catchError(e => {
        Swal.fire(e.error.message, e.error.error, 'error');
        return throwError(e);
      })
    );
  }
}
