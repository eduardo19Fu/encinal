import { Component, OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';

import { Sale } from 'src/app/models/sale';
import { SaleService } from '../../services/sale-service/sale.service';

import { JqueryConfigs } from '../../utils/jquery-utils';
import { BlockService } from '../../services/block-service/block.service';
import { Block } from '../../models/block';

@Component({
  selector: 'app-sales',
  templateUrl: './sales.component.html',
  styleUrls: ['./sales.component.css']
})
export class SalesComponent implements OnInit {

  public title: string;
  public sales: Sale[];
  public blocks: Block[];

  public iniDate: Date;
  public endDate: Date;

  public jqueryConfigs: JqueryConfigs;

  iniDateValue: string;
  endDateValue: string;

  constructor(
    private saleService: SaleService,
    private blockService: BlockService
  ) {
    this.title = 'Ventas Realizadas';
    this.jqueryConfigs = new JqueryConfigs();
    this.iniDateValue = new Date().toISOString().slice(0, 10);
    this.endDateValue = new Date().toISOString().slice(0, 10);
  }

  ngOnInit(): void {
    // this.loadSales();
    this.loadBlocks();
  }

  loadSales(): void{
    this.saleService.getSales().subscribe(
      sales => {
        this.sales = sales;
        this.jqueryConfigs.configDataTable('sales');
      }
    );
  }

  searchSalesDate(): void{
    this.iniDate = (document.getElementById('init-date') as HTMLInputElement).valueAsDate;
    this.endDate = (document.getElementById('end-date') as HTMLInputElement).valueAsDate;
    console.log(this.endDate);
    this.saleService.getSalesByDate(this.iniDate, this.endDate).subscribe(
      sales => {
        this.sales = sales;
        this.jqueryConfigs.configDataTable('sales');
      }
    );
  }

  loadBlocks(): void{
    this.blockService.getBlocks().subscribe(
      blocks => {
        this.blocks = blocks;
      }
    );
  }

  searchSalesByBlock(): void{
    const blockId = +(document.getElementById('blocks') as HTMLSelectElement).value;
    this.saleService.getSalesByBlock(blockId).subscribe(
      sales => {
        this.sales = sales;
      }
    );
  }

  searchSalesByBlockAndDate(): void{
    this.iniDate = (document.getElementById('init-date') as HTMLInputElement).valueAsDate;
    this.endDate = (document.getElementById('end-date') as HTMLInputElement).valueAsDate;
    const blockId = +(document.getElementById('blocks') as HTMLSelectElement).value;

    this.saleService.getSalesByBlockAndDate(blockId, this.iniDate, this.endDate).subscribe(
      sales => {
        this.sales = sales;
      }
    );
  }

  searchAllSales(): void{}
}
