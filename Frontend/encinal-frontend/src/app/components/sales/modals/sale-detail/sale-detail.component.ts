import { Component, Input, OnInit } from '@angular/core';

import { SaleService } from '../../../../services/sale-service/sale.service';
import { Sale } from '../../../../models/sale';

@Component({
  selector: 'app-sale-detail',
  templateUrl: './sale-detail.component.html',
  styles: [
  ]
})
export class SaleDetailComponent implements OnInit {

  @Input() sale: Sale;

  title: string;

  constructor(
    private saleService: SaleService
  ) {
    this.title = 'Detalle de Venta';
  }

  ngOnInit(): void {
  }

}
