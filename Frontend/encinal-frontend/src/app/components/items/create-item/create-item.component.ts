import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { Item } from '../../../models/item';
import { AuthService } from '../../../services/users/auth.service';
import { ItemService } from '../../../services/items/item.service';

import Swal from 'sweetalert2';

@Component({
  selector: 'app-create-item',
  templateUrl: './create-item.component.html',
  styles: [
  ]
})
export class CreateItemComponent implements OnInit {

  @Input() item: Item;
  title: string;

  constructor(
    private itemService: ItemService,
    private authService: AuthService,
    private router: Router
  ) {
    this.title = 'Crear Nuevo Valor';
    this.item = new Item();
  }

  ngOnInit(): void {
  }

  create(): void{
    this.itemService.create(this.item).subscribe(
      response => {
        this.router.navigate(['/admin/item/index']);
        Swal.fire(response.message, `¡El item ${response.item.itemName} con valor ${response.item.itemValue} ha sido creado con éxito!`, 'success');
      }
    );
  }

}
