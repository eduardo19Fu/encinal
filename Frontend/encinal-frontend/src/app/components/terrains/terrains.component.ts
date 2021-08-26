import { Component, OnInit } from '@angular/core';
import { Terrain } from 'src/app/models/terrain';
import { TerrainService } from '../../services/terrain-service/terrain.service';
import { JqueryConfigs } from '../../utils/jquery-utils';

@Component({
  selector: 'app-terrains',
  templateUrl: './terrains.component.html',
  styles: [
  ]
})
export class TerrainsComponent implements OnInit {

  title: string;
  terrains: Terrain[];

  jqueryConfigs: JqueryConfigs;

  constructor(
    private terrainService: TerrainService
  ) {
    this.title = 'Lotes Registrados';
    this.jqueryConfigs = new JqueryConfigs();
  }

  ngOnInit(): void {
    this.getTerrains();
  }

  getTerrains(): void{
    this.terrainService.getTerrains().subscribe(
      terrains => {
        this.terrains = terrains;
        this.jqueryConfigs.configDataTable('terrains');
      }
    );
  }

}
