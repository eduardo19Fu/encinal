import { Component, OnInit } from '@angular/core';

import { Receipt } from '../../models/receipt';
import { ReceiptService } from '../../services/receipts/receipt.service';
import { JqueryConfigs } from '../../utils/jquery-utils';


@Component({
  selector: 'app-receipts',
  templateUrl: './receipts.component.html',
  styles: [
  ]
})
export class ReceiptsComponent implements OnInit {

  title: string;
  receipts: Receipt[];

  jqueryConfigs: JqueryConfigs;

  constructor(
    private receiptService: ReceiptService
  ) {
    this.title = 'Listado de Pagos';
    this.jqueryConfigs = new JqueryConfigs();
  }

  ngOnInit(): void {
    this.loadReceipts();
  }

  loadReceipts(): void{
    this.receiptService.getReceipts().subscribe(
      receipts => {
        this.receipts = receipts;
        this.jqueryConfigs.configDataTable('receipts');
      }
    );
  }

}
