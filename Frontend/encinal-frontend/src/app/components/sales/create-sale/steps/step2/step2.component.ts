import { Component, OnInit, OnDestroy } from '@angular/core';
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
export class Step2Component implements OnInit, OnDestroy {

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

    this.subscriptionCustomer = this.step2Service.customer$.subscribe(
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

  ngOnDestroy(): void {
    this.subscriptionCustomer.unsubscribe();
    this.subscriptionSaleType.unsubscribe();
  }

  loadTarrainsOnSale(): void{
    this.terrainService.getTerrainsOnSale().subscribe(
      response => {
        this.terrains = response;
        this.jqueryConfigs.configDataTable('terrains');
      }
    );
  }

  emitToStep3(terrain: Terrain): void{
    // this.step2Service.terrain$.emit(terrain);
    // console.log(this.customer);
    // console.log(this.saleType);
    // this.step2Service.customer$.emit(this.customer);
    // this.step2Service.saleType$.emit(this.saleType);
  }

  emitToStep1(): void{}

}
