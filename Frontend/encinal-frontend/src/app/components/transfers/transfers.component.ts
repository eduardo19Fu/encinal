import { Component, OnInit } from '@angular/core';

import { Transfer } from '../../models/transfer';

import { TransferService } from '../../services/transfers/transfer.service';
import { JqueryConfigs } from '../../utils/jquery-utils';

@Component({
  selector: 'app-transfers',
  templateUrl: './transfers.component.html'
})
export class TransfersComponent implements OnInit {

  title: string;

  transfers: Transfer[];
  jqueryconfigs = new JqueryConfigs();

  constructor(
    private transferService: TransferService
  ) {
    this.title = 'Traspasos de Terrenos';
  }

  ngOnInit(): void {
    this.loadTransfers();
  }

  loadTransfers(): void{
    this.transferService.getTransfers().subscribe(
      transfers => {
        this.transfers = transfers;
      }
    );
  }

}
