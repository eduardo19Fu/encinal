import { Component, OnInit } from '@angular/core';
import { Terrain } from 'src/app/models/terrain';
import { TerrainService } from '../../services/terrain-service/terrain.service';

import { JqueryConfigs } from '../../utils/jquery-utils';
import { BlockService } from '../../services/block-service/block.service';
import { Block } from '../../models/block';

@Component({
  selector: 'app-terrains',
  templateUrl: './terrains.component.html',
  styles: [
  ]
})
export class TerrainsComponent implements OnInit {

  public title: string;

  public terrains: Terrain[];
  public blocks: Block[];

  jqueryConfigs: JqueryConfigs;

  constructor(
    private terrainService: TerrainService,
    private blockService: BlockService
  ) {
    this.title = 'Lotes Registrados';
    this.jqueryConfigs = new JqueryConfigs();
  }

  ngOnInit(): void {
    this.getTerrains();
    this.loadBlocks();
  }

  getTerrains(): void {
    this.terrainService.getTerrains().subscribe(
      terrains => {
        this.terrains = terrains;
        this.jqueryConfigs.configDataTable('terrains');
      }
    );
  }

  loadBlocks(): void {
    this.blockService.getBlocks().subscribe(
      blocks => {
        this.blocks = blocks;
      }
    );
  }

  loadTerrainsByBlock(event): void {
    if (event !== 'null') {
      this.terrainService.getTerrainsByBlock(+event).subscribe(
        response => {
          this.terrains = response;
        }
      );
    }
  }

}
