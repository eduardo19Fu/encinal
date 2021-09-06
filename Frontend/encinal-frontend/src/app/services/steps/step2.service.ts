import { EventEmitter, Injectable } from '@angular/core';
import { Client } from 'src/app/models/client';
import { SaleType } from 'src/app/models/sale-type';
import { Terrain } from 'src/app/models/terrain';

@Injectable({
  providedIn: 'root'
})
export class Step2Service {

  saleType$ = new EventEmitter<SaleType>();
  customer$ = new EventEmitter<Client>();
  terrain$ = new EventEmitter<Terrain>();

  constructor() { }
}
