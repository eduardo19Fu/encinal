import { Component, OnInit } from '@angular/core';
import { Item } from '../../models/item';
import { ItemService } from '../../services/items/item.service';
import { AuthService } from '../../services/users/auth.service';

@Component({
  selector: 'app-items',
  templateUrl: './items.component.html',
  styles: [
  ]
})
export class ItemsComponent implements OnInit {

  public title: string;
  public items: Item[];

  public item: Item;

  constructor(
    private itemService: ItemService,
    public authService: AuthService
  ) {
    this.title = 'Valores Configurables';
    this.item = new Item();
  }

  ngOnInit(): void {
    this.loadItems();
  }

  loadItems(): void{
    this.itemService.getItems().subscribe(
      items => this.items = items
    );
  }

}
