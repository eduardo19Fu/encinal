import { Component, OnInit } from '@angular/core';

import { SaleService } from '../../services/sale-service/sale.service';
import { TerrainService } from '../../services/terrain-service/terrain.service';
import { BlockService } from '../../services/block-service/block.service';
import { UserService } from '../../services/users/user.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  terrains: number;
  blocks: number;
  sales: number;
  users: number;

  constructor(
    private saleService: SaleService,
    private terrainService: TerrainService,
    private blockService: BlockService,
    private userService: UserService
  ) { }

  ngOnInit(): void {
    this.getCountSales();
    this.getCountTerrains();
    this.getCountBlocks();
    this.getCountUsers();
  }

  getCountTerrains(): void{
    this.terrainService.getTerrains().subscribe(
      terrains => {
        this.terrains = terrains.length;
      }
    );
  }

  getCountSales(): void{
    this.saleService.getSales().subscribe(
      sales => {
        this.sales = sales.length;
      }
    );
  }

  getCountBlocks(): void{
    this.blockService.getBlocks().subscribe(
      blocks => this.blocks = blocks.length
    );
  }

  getCountUsers(): void{
    this.userService.getUsers().subscribe(
      users => this.users = users.length
    );
  }

}
