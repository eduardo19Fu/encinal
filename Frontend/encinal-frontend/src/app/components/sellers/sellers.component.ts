import { Component, OnInit, AfterViewInit } from '@angular/core';
import { SellerService } from '../../services/seller-service/seller.service';
import { Seller } from '../../models/seller';
import { JqueryConfigs } from '../../utils/jquery-utils';

@Component({
  selector: 'app-sellers',
  templateUrl: './sellers.component.html',
  styles: [
  ]
})
export class SellersComponent implements OnInit, AfterViewInit {

  title: string;

  sellers: Seller[];
  jqueryConfigs: JqueryConfigs;

  constructor(
    private sellerService: SellerService
  ) {
    this.title = 'Vendedores';
    this.jqueryConfigs = new JqueryConfigs();
  }

  ngOnInit(): void {
    this.getSellers();
  }

  ngAfterViewInit(): void {
  }

  getSellers(): void {
    this.sellerService.getSellers().subscribe(
      sellers => {
        this.sellers = sellers;
        this.jqueryConfigs.configDataTable('sellers');
        this.jqueryConfigs.configToolTip();
      }
    );
  }

}