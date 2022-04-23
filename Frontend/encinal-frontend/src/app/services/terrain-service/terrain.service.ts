import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { Terrain } from 'src/app/models/terrain';

import { global } from '../global';
import Swal from 'sweetalert2';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class TerrainService {

  url: string;
  httpHeaders = new HttpHeaders().set('Content-Type', 'application/json');

  constructor(
    private httpClient: HttpClient
  ) {
    this.url = global.url;
  }

  getTerrains(): Observable<Terrain[]> {
    return this.httpClient.get<Terrain[]>(`${this.url}/terrains`);
  }

  getTerrainsOnSale(): Observable<any> {
    return this.httpClient.get<any>(`${this.url}/terrains/on-sale`).pipe(
      catchError(e => {
        Swal.fire(e.error.message, e.error.error, 'error');
        return throwError(e);
      })
    );
  }

  getSoldTerrains(): Observable<any>{
    return this.httpClient.get<any>(`${this.url}/terrains/sold`).pipe(
      catchError(e => {
        Swal.fire(e.error.message, e.error.error, 'error');
        return throwError(e);
      })
    );
  }

  getTerrainsByBlock(id: number): Observable<any>{
    return this.httpClient.get<any>(`${this.url}/terrains/blocks/${id}`).pipe(
      catchError(e => {
        Swal.fire(e.error.message, e.error.error, 'error');
        return throwError(e);
      })
    );
  }

  getTerrain(id: number): Observable<any> {
    return this.httpClient.get<Terrain>(`${this.url}/terrains/${id}`).pipe(
      catchError(e => {
        Swal.fire(e.error.message, e.error.error, 'error');
        return throwError(e);
      })
    );
  }

  create(terrain: Terrain): Observable<any> {
    return this.httpClient.post<any>(`${this.url}/terrains`, terrain).pipe(
      catchError(e => {
        Swal.fire(e.error.message, e.error.error, 'error');
        return throwError(e);
      })
    );
  }

  update(terrain: Terrain): Observable<any> {
    return this.httpClient.put<any>(`${this.url}/terrains`, terrain).pipe(
      catchError(e => {
        Swal.fire(e.error.message, e.error.error, 'error');
        return throwError(e);
      })
    );
  }
}
