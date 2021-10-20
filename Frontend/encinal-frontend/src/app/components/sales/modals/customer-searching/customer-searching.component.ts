import { Component, EventEmitter, OnInit, Output, ViewChild } from '@angular/core';
import { Client } from 'src/app/models/client';
import { ClientService } from '../../../../services/client-service/client.service';
import { JqueryConfigs } from '../../../../utils/jquery-utils';

@Component({
  selector: 'app-customer-searching',
  templateUrl: './customer-searching.component.html',
  styles: [
  ]
})
export class CustomerSearchingComponent implements OnInit {

  @ViewChild('myModalClose') modalClose;
  @Output() customer = new EventEmitter<Client>();

  public title: string;
  jqueryConfigs: JqueryConfigs = new JqueryConfigs();

  public customers: Client[];

  constructor(
    private clientService: ClientService
  ) {
    this.title = 'Búsqueda de Clientes';
  }

  ngOnInit(): void {
    this.loadCustomers();
  }

  loadCustomers(): void {
    this.clientService.getClients().subscribe(
      customers => {
        this.customers = customers;
        this.jqueryConfigs.configModalDataTable('customers');
      }
    );
  }

  chooseCustomer(customer: Client): void{
    this.customer.emit(customer);
    // console.log(customer);
    (document.getElementById('button-x')).click();
  }

}
