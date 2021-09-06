import { Component, OnInit } from '@angular/core';
import { SaleType } from '../../../../../models/sale-type';
import { Client } from '../../../../../models/client';
import { Terrain } from '../../../../../models/terrain';
import { Step1Service } from '../../../../../services/steps/step1.service';
import { Step2Service } from '../../../../../services/steps/step2.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-step3',
  templateUrl: './step3.component.html',
  styleUrls: ['./step3.component.css']
})
export class Step3Component implements OnInit {

  title: string;

  saleType: SaleType;
  customer: Client;
  terrain: Terrain;

  subscriptionCustomer: Subscription;
  subscriptionSaleType: Subscription;
  subscriptionTerrain: Subscription;

  constructor(
    private step1Service: Step1Service,
    private step2Service: Step2Service
  ) {}

  ngOnInit(): void {
    this.subscriptionCustomer = this.step2Service.customer$.subscribe(
      customer => {
        this.customer = customer;
        console.log(this.customer);
      }
    );
  }

}
