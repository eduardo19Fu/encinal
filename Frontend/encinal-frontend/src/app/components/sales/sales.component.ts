import { Component, OnInit } from '@angular/core';
import { Sale } from 'src/app/models/sale';
import { SaleService } from '../../services/sale-service/sale.service';
import { JqueryConfigs } from '../../utils/jquery-utils';

@Component({
  selector: 'app-sales',
  templateUrl: './sales.component.html',
  styleUrls: ['./sales.component.css']
})
export class SalesComponent implements OnInit {

  title: string;
  sales: Sale[];

  iniDate: Date;
  endDate: Date;

  jqueryConfigs: JqueryConfigs;

  constructor(
    private saleService: SaleService
  ) {
    this.title = 'Ventas Realizadas';
    this.jqueryConfigs = new JqueryConfigs();
  }

  ngOnInit(): void {
    // this.loadSales();
  }

  loadSales(): void{
    this.saleService.getSales().subscribe(
      sales => {
        this.sales = sales;
        this.jqueryConfigs.configDataTable('sales');
      }
    );
  }

  loadSalesDate(): void{
    this.saleService.getSalesByDate(this.iniDate, this.endDate).subscribe(
      sales => {
        this.sales = sales;
        this.jqueryConfigs.configDataTable('sales');
      }
    );
  }
}
