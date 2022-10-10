import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { global } from '../global';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RangeService {

  url: string;

  constructor(
    private httpClient: HttpClient
  ) {
    this.url = global.url;
  }

  getRanges(): Observable<Range[]>{
    return this.httpClient.get<Range[]>('');
  }
}
