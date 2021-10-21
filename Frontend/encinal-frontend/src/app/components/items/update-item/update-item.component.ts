import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

import { Item } from '../../../models/item';
import { ItemService } from '../../../services/items/item.service';

import Swal from 'sweetalert2';

@Component({
  selector: 'app-update-item',
  templateUrl: './update-item.component.html',
  styles: [
  ]
})
export class UpdateItemComponent implements OnInit {

  public title: string;
  public item: Item;

  constructor(
    private itemService: ItemService,
    private router: Router,
    private activatedRout: ActivatedRoute
  ) {
    this.title = 'Configurar Valor';
    this.item = new Item();
  }

  ngOnInit(): void {
    this.loadItem();
  }

  loadItem(): void{
    this.activatedRout.params.subscribe(
      params => {
        const id = params.id;

        if (id){
          this.itemService.getItem(id).subscribe(
            item => this.item = item
          );
        }
      }
    );
  }

  update(): void{
    this.itemService.update(this.item).subscribe(
      response => {
        this.router.navigate(['/admin/items/index']);
        Swal.fire(response.message, 'El valor del item ha sido configurado con éxito', 'success');
      }
    );
  }

}
