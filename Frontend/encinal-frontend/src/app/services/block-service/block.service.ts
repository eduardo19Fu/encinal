import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Block } from 'src/app/models/block';

import { global } from '../global';
import Swal from 'sweetalert2';

@Injectable({
  providedIn: 'root'
})
export class BlockService {

  url: string;
  httpHeaders = new HttpHeaders().set('Content-Type', 'application/json');

  constructor(
    private httpClient: HttpClient
  ) {
    this.url = global.url;
  }

  getBlocks(): Observable<Block[]> {
    return this.httpClient.get<Block[]>(`${this.url}/blocks`);
  }

  getBlock(id: number): Observable<any> {
    return this.httpClient.get<any>(`${this.url}/blocks/${id}`).pipe(
      catchError(e => {
        Swal.fire('Error al buscar la Manzana', e.error.message, 'error');
        return throwError(e);
      })
    );
  }

  create(block: Block): Observable<any> {
    return this.httpClient.post<any>(`${this.url}/blocks`, block).pipe(
      catchError(e => {
        Swal.fire(e.error.message, e.error.error, 'error');
        return throwError(e);
      })
    );
  }

  update(block: Block): Observable<any> {
    return this.httpClient.put<any>(`${this.url}/blocks`, block).pipe(
      catchError(e => {
        Swal.fire(e.error.message, e.error.error, 'error');
        return throwError(e);
      })
    );
  }
}
