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

  public title: string;

  public sellers: Seller[];
  public seller: Seller;

  jqueryConfigs: JqueryConfigs;

  constructor(
    private sellerService: SellerService
  ) {
    this.title = 'Vendedores';
    this.jqueryConfigs = new JqueryConfigs();
    this.seller = new Seller();
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

  sellerDetail(seller: Seller): void{
    this.seller = seller;
    console.log(this.seller);
  }

}
