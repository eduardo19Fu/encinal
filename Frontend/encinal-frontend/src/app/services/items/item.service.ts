import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { map, catchError } from 'rxjs/operators';

import { Item } from '../../models/item';

import { global } from '../global';
import Swal from 'sweetalert2';

@Injectable({
  providedIn: 'root'
})
export class ItemService {

  url: string;

  constructor(
    private httpClient: HttpClient
  ) {
    this.url = global.url;
  }

  getItems(): Observable<Item[]>{
    return this.httpClient.get<Item[]>(`${this.url}/items`);
  }

  getItemsPaginate(page: number): Observable<any>{
    return this.httpClient.get<any>(`${this.url}/items/${page}`).pipe(
      map((res: any) => {
        (res.content as Item[]).map(item => {
          return item;
        });
        return res;
      })
    );
  }

  getItem(id: number): Observable<any>{
    return this.httpClient.get<any>(`${this.url}/items/${id}`).pipe(
      catchError(e => {
        Swal.fire(e.error.error, e.error.message, 'error');
        return throwError(e);
      })
    );
  }

  create(item: Item): Observable<any>{
    return this.httpClient.post<any>(`${this.url}/items`, item).pipe(
      catchError(e => {
        Swal.fire(e.error.message, e.error.error, 'error');
        return throwError(e);
      })
    );
  }

  update(item: Item): Observable<any>{
    return this.httpClient.put<any>(`${this.url}/items`, item).pipe(
      catchError(e => {
        Swal.fire(e.error.message, e.error.error, 'error');
        return throwError(e);
      })
    );
  }
}
