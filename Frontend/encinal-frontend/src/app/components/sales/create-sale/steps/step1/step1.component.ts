import { Component, Input, OnInit } from '@angular/core';
import { Client } from 'src/app/models/client';
import { ClientService } from '../../../../../services/client-service/client.service';
import { SaleType } from '../../../../../models/sale-type';
import { Step1Service } from '../../../../../services/steps/step1.service';
import { JqueryConfigs } from '../../../../../utils/jquery-utils';
import { Subscription } from 'rxjs';
import { Step2Service } from '../../../../../services/steps/step2.service';

@Component({
  selector: 'app-step1',
  templateUrl: './step1.component.html',
  styleUrls: ['./step1.component.css']
})
export class Step1Component implements OnInit {

  title: string;
  customers: Client[];
  customer: Client;
  saleType: SaleType;

  jqueryConfigs: JqueryConfigs = new JqueryConfigs();

  subscriptionCustomer: Subscription;
  subscriptionSaleType: Subscription;

  constructor(
    private customerService: ClientService,
    private step1Service: Step1Service,
    private step2Service: Step2Service
  ) {
    this.title = 'Paso 1';
  }

  ngOnInit(): void {
    this.loadCustomers();

    this.subscriptionSaleType = this.step1Service.saleType$.subscribe(
      saleType => {
        this.saleType = saleType;
        localStorage.setItem('sale-type', JSON.stringify(this.saleType));
      }
    );
  }

  loadCustomers(): void{
    this.customerService.getActiveCustomers().subscribe(
      customers => {
        this.customers = customers;
        this.jqueryConfigs.configDataTable('customers');
      }
    );
  }

  chooseCustomer(customerSelected: Client): void{
    this.subscriptionSaleType.unsubscribe();
    this.step2Service.customer$.emit(customerSelected);
    this.step2Service.saleType$.emit(this.saleType);
    // console.log(customerSelected);
    // console.log(this.saleType);
  }

}
