import { Component, OnDestroy, OnInit } from '@angular/core';
import { Client } from 'src/app/models/client';
import { ClientService } from '../../services/client-service/client.service';
import { JqueryConfigs } from '../../utils/jquery-utils';

@Component({
  selector: 'app-clients',
  templateUrl: './clients.component.html',
  styles: [
  ]
})

export class ClientsComponent implements OnInit {

  title: string;
  clients: Client[];

  jqueryConfigs: JqueryConfigs;

  constructor(
    private clientService: ClientService
  ) {
    this.title = 'Clientes';
    this.jqueryConfigs = new JqueryConfigs();
  }

  ngOnInit(): void {
    this.getClients();
  }

  getClients(): void {
    this.clientService.getClients().subscribe(
      clients => {
        this.clients = clients;
        this.jqueryConfigs.configDataTable('clients');
      }
    );
  }

}
