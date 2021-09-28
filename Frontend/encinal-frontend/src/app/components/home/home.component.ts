import { Component, OnInit } from '@angular/core';

import { SaleService } from '../../services/sale-service/sale.service';
import { TerrainService } from '../../services/terrain-service/terrain.service';
import { BlockService } from '../../services/block-service/block.service';

import { Terrain } from '../../models/terrain';
import { Block } from '../../models/block';
import { Sale } from '../../models/sale';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  terrains: number;
  blocks: number;
  sales: number;

  constructor(
    private saleService: SaleService,
    private terrainService: TerrainService,
    private blockService: BlockService
  ) { }

  ngOnInit(): void {
    this.getCountSales();
    this.getCountTerrains();
    this.getCountBlocks();
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

}
