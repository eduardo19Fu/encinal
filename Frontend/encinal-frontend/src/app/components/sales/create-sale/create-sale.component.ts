import { Component, Input, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';
import { Router } from '@angular/router';

import { SellerService } from 'src/app/services/seller-service/seller.service';
import { TerrainService } from 'src/app/services/terrain-service/terrain.service';
import { SaleService } from '../../../services/sale-service/sale.service';
import { Terrain } from '../../../models/terrain';
import { Seller } from 'src/app/models/seller';
import { Client } from '../../../models/client';
import { ClientService } from '../../../services/client-service/client.service';
import { SaleTypeService } from '../../../services/sale-types-service/sale-type.service';
import { SaleType } from '../../../models/sale-type';
import { Sale } from '../../../models/sale';

import Swal from 'sweetalert2';

@Component({
  selector: 'app-create-sale',
  templateUrl: './create-sale.component.html',
  styleUrls: ['./create-sale.component.css']
})
export class CreateSaleComponent implements OnInit, OnDestroy {

  title: string;
  contado: boolean;
  cuotas: boolean;

  terrains: Terrain[];
  sellers: Seller[];
  customers: Client[];
  saleTypes: SaleType[];

  customer: Client;
  saleType: SaleType;
  seller: Seller;
  sale: Sale;
  terrain: Terrain;

  saleTypeSubscription: Subscription;

  constructor(
    private saleService: SaleService,
    private terrainService: TerrainService,
    private sellerService: SellerService,
    private customerService: ClientService,
    private saleTypeService: SaleTypeService,
    private router: Router
  ) {
    this.title = 'Venta';
    this.sale = new Sale();
  }

  ngOnInit(): void {
    this.loadSaleTypes();
    this.loadTarrains();
    this.loadSellers();
  }

  ngOnDestroy(): void {
    // this.saleTypeSubscription.unsubscribe();
  }

  create(): void { }

  loadSaleTypes(): void {
    this.saleTypeService.getSaleTypes().subscribe(
      saleTypes => this.saleTypes = saleTypes
    );
  }

  loadTarrains(): void {
    this.terrainService.getTerrainsOnSale().subscribe(
      terrains => this.terrains = terrains
    );
  }

  loadSellers(): void {
    this.sellerService.getSellers().subscribe(
      sellers => this.sellers = sellers
    );
  }

  loadCustomers(): void {
    this.customerService.getActiveCustomers().subscribe(
      response => {
        this.customers = response;
        // console.log(this.customers);
      }
    );
  }

  loadCustomer(event): void {
    this.customer = event;
    // console.log(this.customer);
    (document.getElementById('client') as HTMLInputElement).value = this.customer.firstName + ' ' + this.customer.lastName;
  }

  loadSeller(event): void {
    this.seller = event;
    // console.log(this.seller);
    (document.getElementById('seller') as HTMLInputElement).value = this.seller.firstName + ' ' + this.seller.lastName;
  }

  mostrarTotal(event): void {
    console.log(event);
    this.terrain = event;
  }

  setSaleType(event): void {
    this.saleType = event;
  }

  createSale(): void {
    if (this.terrain) {
      this.sale.client = this.customer;
      this.sale.seller = this.seller;
      this.sale.total = this.terrain.price;

      this.saleService.create(this.sale).subscribe(
        response => {
          this.router.navigate(['/sales/index']);
          Swal.fire(response.message, `La venta del lote no. ${response.sale.terrain.terrainNumber} ha sido completada.`, 'success');
        }
      );
    }
  }
}
