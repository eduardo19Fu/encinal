import { Component, OnInit } from '@angular/core';

import { Transfer } from '../../../models/transfer';
import { Terrain } from '../../../models/terrain';
import { Sale } from '../../../models/sale';
import { Client } from '../../../models/client';

import { TransferService } from '../../../services/transfers/transfer.service';
import { ClientService } from '../../../services/client-service/client.service';
import { TerrainService } from '../../../services/terrain-service/terrain.service';
import { SaleService } from '../../../services/sale-service/sale.service';
import { AuthService } from '../../../services/users/auth.service';
import { UserService } from '../../../services/users/user.service';

import { JqueryConfigs } from '../../../utils/jquery-utils';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-create-transfer',
  templateUrl: './create-transfer.component.html',
  styles: [
  ]
})
export class CreateTransferComponent implements OnInit {

  title: string;

  transfer: Transfer;
  client: Client;

  soldTerrain: Terrain;
  soldTerrains: Terrain[];

  sales: Sale[];

  jqueryConfigs: JqueryConfigs = new JqueryConfigs();

  constructor(
    private transferService: TransferService,
    private clientService: ClientService,
    private terrainService: TerrainService,
    private saleService: SaleService,
    private authService: AuthService,
    private userService: UserService
  ) {
    this.title = 'Realizar Traspaso';
    this.transfer = new Transfer();
    this.soldTerrains = [];
    this.client = new Client();
  }

  ngOnInit(): void {
    this.loadTerrains();
  }

  create(): void{
  }

  loadTerrains(): void {
  }

}
