import { Component, OnDestroy, OnInit, AfterViewInit } from '@angular/core';
import { Client } from 'src/app/models/client';
import { ClientService } from '../../services/client-service/client.service';
import { JqueryConfigs } from '../../utils/jquery-utils';

@Component({
  selector: 'app-clients',
  templateUrl: './clients.component.html',
  styles: [
  ]
})

export class ClientsComponent implements OnInit, AfterViewInit{

  public title: string;
  public clients: Client[];
  public client: Client;

  jqueryConfigs: JqueryConfigs;

  constructor(
    private clientService: ClientService
  ) {
    this.title = 'Clientes';
    this.client = new Client();
    this.jqueryConfigs = new JqueryConfigs();
  }

  ngOnInit(): void {
    this.getClients();
  }

  ngAfterViewInit(): void{
  }

  getClients(): void {
    this.clientService.getClients().subscribe(
      clients => {
        this.clients = clients;
        this.jqueryConfigs.configDataTable('clients');
      }
    );
  }

  clientDetail(clientLoaded: Client): void{
    this.client = clientLoaded;
  }

}
