import { Component, OnInit } from '@angular/core';
import { Step1Service } from '../../../../../services/steps/step1.service';
import { SaleType } from '../../../../../models/sale-type';
import { Client } from '../../../../../models/client';
import { Terrain } from '../../../../../models/terrain';
import { TerrainService } from '../../../../../services/terrain-service/terrain.service';
import { JqueryConfigs } from '../../../../../utils/jquery-utils';
import { Step2Service } from '../../../../../services/steps/step2.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-step2',
  templateUrl: './step2.component.html',
  styleUrls: ['./step2.component.css']
})
export class Step2Component implements OnInit {

  title: string;
  saleType: SaleType;
  customer: Client;
  terrains: Terrain[];

  jqueryConfigs: JqueryConfigs = new JqueryConfigs();

  subscriptionCustomer: Subscription;
  subscriptionSaleType: Subscription;

  constructor(
    private terrainService: TerrainService,
    private step1Service: Step1Service,
    private step2Service: Step2Service
  ) {
    this.title = 'Paso 2';
  }

  ngOnInit(): void {
    this.loadTarrainsOnSale();

    this.subscriptionCustomer = this.step1Service.customer$.subscribe(
      response => {
        this.customer = response;
        console.log(this.customer);
      }
    );

    this.subscriptionSaleType = this.step1Service.saleType$.subscribe(
      response => {
        this.saleType = response;
        console.log(this.saleType);
      }
    );
  }

  loadTarrainsOnSale(): void{
    this.terrainService.getTerrainOnSale().subscribe(
      response => {
        this.terrains = response;
        this.jqueryConfigs.configDataTable('terrains');
      }
    );
  }

  emitToStep3(terrain: Terrain): void{
    this.subscriptionCustomer.unsubscribe();
    this.subscriptionSaleType.unsubscribe();
    this.step2Service.terrain$.emit(terrain);
    this.step2Service.customer$.emit(this.customer);
    this.step2Service.saleType$.emit(this.saleType);
  }

  emitToStep1(): void{}

}
