import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { SellerService } from '../../../../services/seller-service/seller.service';
import { Seller } from '../../../../models/seller';
import { JqueryConfigs } from '../../../../utils/jquery-utils';

@Component({
  selector: 'app-seller-searching',
  templateUrl: './seller-searching.component.html',
  styles: [
  ]
})
export class SellerSearchingComponent implements OnInit {

  @Output() seller = new EventEmitter<Seller>();

  title: string;
  jqueryConfigs: JqueryConfigs = new JqueryConfigs();

  sellers: Seller[];

  constructor(
    private sellerService: SellerService
  ) {
    this.title = 'Búsqueda de vendedor';
  }

  ngOnInit(): void {
    this.loadSellers();
  }

  loadSellers(): void{
    this.sellerService.getSellers().subscribe(
      sellers => {
        this.sellers = sellers;
        this.jqueryConfigs.configModalDataTable('sellers');
      }
    );
  }

  chooseSeller(seller: Seller): void{
    this.seller.emit(seller);
    (document.getElementById('button-y')).click();
  }

}
