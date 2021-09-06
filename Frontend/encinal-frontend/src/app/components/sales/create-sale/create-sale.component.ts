import { Component, Input, OnInit } from '@angular/core';
import { SellerService } from 'src/app/services/seller-service/seller.service';
import { TerrainService } from 'src/app/services/terrain-service/terrain.service';
import { SaleService } from '../../../services/sale-service/sale.service';
import { Terrain } from '../../../models/terrain';
import { Seller } from 'src/app/models/seller';
import { Client } from '../../../models/client';
import { ClientService } from '../../../services/client-service/client.service';
import { Step1Service } from '../../../services/steps/step1.service';
import { SaleTypeService } from '../../../services/sale-types-service/sale-type.service';
import { SaleType } from '../../../models/sale-type';

@Component({
  selector: 'app-create-sale',
  templateUrl: './create-sale.component.html',
  styleUrls: ['./create-sale.component.css']
})
export class CreateSaleComponent implements OnInit {

  title: string;
  contado: boolean;
  cuotas: boolean;

  step1: boolean;

  terrains: Terrain[];
  sellers: Seller[];
  customers: Client[];
  customer: Client;
  saleType: SaleType;

  @Input() terrain: Terrain;

  constructor(
    private saleService: SaleService,
    private terrainService: TerrainService,
    private sellerService: SellerService,
    private customerService: ClientService,
    private saleTypeService: SaleTypeService,

    private step1Service: Step1Service
  ) {
    this.title = 'Venta';
    this.contado = false;
    this.cuotas = false;
    this.step1 = false;
  }

  ngOnInit(): void {
    this.loadTarrains();
    this.loadSellers();
  }

  create(): void{}

  loadTarrains(): void{
    this.terrainService.getTerrains().subscribe(
      terrains => this.terrains = terrains
    );
  }

  loadSellers(): void{
    this.sellerService.getSellers().subscribe(
      sellers => this.sellers = sellers
    );
  }

  loadCustomers(): void{
    this.customerService.getActiveCustomers().subscribe(
      response => {
        this.customers = response;
        console.log(this.customers);
      }
    );
  }

  chooseCustomer(customerSelected: Client): void{
    this.customer = customerSelected;
    this.step1 = false;
    console.log(this.customer);
  }

  changeToContado(): void{
    this.contado = true;
    this.cuotas = false;
    this.step1 = true;
    this.loadCustomers();
  }

  changeToCuotas(): void{
    this.contado = false;
    this.cuotas = true;
    console.log('Contado: ' + this.contado);
    console.log('Cuotas: ' + this.cuotas);
  }

  emitSaleType(type: number): void{
    this.saleTypeService.getSaleType(type).subscribe(
      response => {
        this.saleType = response;
        this.step1Service.saleType$.emit(this.saleType);
      }
    );
  }

}
