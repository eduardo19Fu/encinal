import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { Client } from '../../../models/client';
import { ClientService } from '../../../services/client-service/client.service';
import { JqueryConfigs } from '../../../utils/jquery-utils';

@Component({
  selector: 'app-search-client-modal',
  templateUrl: './search-client-modal.component.html',
  styleUrls: ['./search-client-modal.component.css']
})
export class SearchClientModalComponent implements OnInit {

  @Output() customer = new EventEmitter<Client>();

  title: string;

  clients: Client[];
  jqueryConfigs: JqueryConfigs = new JqueryConfigs();

  constructor(
    private clientService: ClientService
  ) {
    this.title = 'Búsqueda de Clientes';
  }

  ngOnInit(): void {
  }

  loadCustomers(): void {
    this.clientService.getClients().subscribe(
      customers => {
        this.clients = customers;
        this.jqueryConfigs.configModalDataTable('customers');
      }
    );
  }

  chooseCustomer(customer: Client): void{
    this.customer.emit(customer);
    // console.log(customer);
    // (document.getElementById('button-x')).click();
  }

}
