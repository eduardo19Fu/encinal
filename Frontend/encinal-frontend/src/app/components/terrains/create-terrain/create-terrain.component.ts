import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { TerrainService } from '../../../services/terrain-service/terrain.service';
import { BlockService } from '../../../services/block-service/block.service';
import { Block } from '../../../models/block';
import { Terrain } from '../../../models/terrain';

import Swal from 'sweetalert2';

@Component({
  selector: 'app-create-terrain',
  templateUrl: './create-terrain.component.html',
  styles: [
  ]
})
export class CreateTerrainComponent implements OnInit {

  title: string;

  terrain: Terrain;
  blocks: Block[];

  constructor(
    private terrainService: TerrainService,
    private blockService: BlockService,
    private router: Router,
    private activatedRoute: ActivatedRoute
  ) {
    this.title = 'Registrar Lote';
    this.terrain = new Terrain();
  }

  ngOnInit(): void {
    this.getBlocks();
    this.loadTerrain();
  }

  create(): void{
    this.terrainService.create(this.terrain).subscribe(
      response => {
        this.router.navigate(['/terrains/index']);
        Swal.fire(response.message, `El lote número ${response.terrain.terrainNumber} ha sido registrado con éxito!`, 'success');
      }
    );
  }

  loadTerrain(): void{
    this.activatedRoute.params.subscribe(
      params => {
        const id = params.id;

        if (id){
          this.terrainService.getTerrain(id).subscribe(
            terrain => this.terrain = terrain
          );
        }
      }
    );
  }

  getBlocks(): void{
    this.blockService.getBlocks().subscribe(
      blocks => {
        this.blocks = blocks;
      }
    );
  }

}
