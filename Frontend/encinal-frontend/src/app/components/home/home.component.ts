import { Component, OnInit } from '@angular/core';

import { SaleService } from '../../services/sale-service/sale.service';
import { TerrainService } from '../../services/terrain-service/terrain.service';
import { BlockService } from '../../services/block-service/block.service';
import { UserService } from '../../services/users/user.service';
import { AuthService } from '../../services/users/auth.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html'
})
export class HomeComponent implements OnInit {

  public terrains: number;
  public blocks: number;
  public sales: number;
  public users: number;
  public totalSales: number;

  constructor(
    private saleService: SaleService,
    private terrainService: TerrainService,
    private blockService: BlockService,
    private userService: UserService,
    public authService: AuthService
  ) { }

  ngOnInit(): void {
    this.getCountSales();
    this.getCountTerrains();
    this.getCountBlocks();
    this.getCountUsers();
    this.getTotalSales();
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

  getTotalSales(): void{
    this.saleService.reportDailySales().subscribe(
      response => {
        this.totalSales = response;
      }
    );
  }

}
