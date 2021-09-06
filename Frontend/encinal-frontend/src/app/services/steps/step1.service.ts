import { Injectable, EventEmitter } from '@angular/core';
import { SaleType } from '../../models/sale-type';
import { Client } from '../../models/client';

@Injectable({
  providedIn: 'root'
})
export class Step1Service {

  saleType$ = new EventEmitter<SaleType>();
  customer$ = new EventEmitter<Client>();

  constructor() { }
}
