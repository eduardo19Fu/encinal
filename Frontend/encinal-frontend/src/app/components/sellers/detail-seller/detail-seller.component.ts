import { Component, Input, OnInit } from '@angular/core';
import { Seller } from 'src/app/models/seller';

@Component({
  selector: 'app-detail-seller',
  templateUrl: './detail-seller.component.html',
  styleUrls: ['./detail-seller.component.css']
})
export class DetailSellerComponent implements OnInit {

  title: string;
  @Input() seller: Seller;

  constructor() {
    this.title = 'Detalle del Vendedor';
  }

  ngOnInit(): void {
  }

}
