import { Component, OnInit, AfterViewInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { SellerService } from '../../../services/seller-service/seller.service';
import { Seller } from '../../../models/seller';

import Swal from 'sweetalert2';

@Component({
  selector: 'app-create-seller',
  templateUrl: './create-seller.component.html',
  styleUrls: ['./create-seller.component.css']
})
export class CreateSellerComponent implements OnInit {

  title: string;
  seller: Seller;

  constructor(
    private sellerService: SellerService,
    private router: Router,
    private activatedRoute: ActivatedRoute
  ) {
    this.title = 'Registrar Vendedor';
    this.seller = new Seller();
  }

  ngOnInit(): void {
    this.loadSeller();
  }

  loadSeller(): void{
    this.activatedRoute.params.subscribe(params => {
      const id = params.id;
      if (id){
        this.sellerService.getSeller(id).subscribe(
          seller => this.seller = seller
        );
      }
    });
  }

  create(): void{
    this.sellerService.create(this.seller).subscribe(
      response => {
        this.router.navigate(['/admin/sellers/index']);
        Swal.fire(response.message, `El vendedor ${response.seller.firstName + ' ' + response.seller.lastName} fué registrado con éxito!`,
          'success');
      }
    );
  }

  update(): void{
    this.sellerService.update(this.seller).subscribe(
      response => {
        this.router.navigate(['/admin/sellers/index']);
        Swal.fire(response.message, `El vendedor ${response.seller.firstName + ' ' + response.seller.lastName} fué actualizado con éxito!`,
          'success');
      }
    );
  }

}
