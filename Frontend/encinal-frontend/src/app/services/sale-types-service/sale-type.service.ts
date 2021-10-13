import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { SaleType } from 'src/app/models/sale-type';
import { global } from '../global';
import Swal from 'sweetalert2';

@Injectable({
  providedIn: 'root'
})
export class SaleTypeService {

  url: string;
  httpHeaders = new HttpHeaders().set('Content-Type', 'application/json');

  constructor(
    private httpClient: HttpClient
  ) {
    this.url = global.url;
  }

  getSaleTypes(): Observable<SaleType[]>{
    return this.httpClient.get<SaleType[]>(`${this.url}/sale-types`);
  }

  getSaleType(id: number): Observable<any>{
    return this.httpClient.get<any>(`${this.url}/sale-types/${id}`).pipe(
      catchError(e => {
        Swal.fire(e.error.message, e.error.error);
        return throwError(e);
      })
    );
  }
}
