import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Sale } from 'src/app/models/sale';

import { global } from '../global';
import Swal from 'sweetalert2';
import { SaleType } from 'src/app/models/sale-type';

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

  getSales(): Observable<Sale[]>{
    return this.httpClient.get<Sale[]>(`${this.url}/sales`, {headers: this.httpHeaders});
  }

  getSaleTypes(): Observable<SaleType[]>{
    return this.httpClient.get<SaleType[]>(`${this.url}/sales/sales-types`, {headers: this.httpHeaders});
  }
}
