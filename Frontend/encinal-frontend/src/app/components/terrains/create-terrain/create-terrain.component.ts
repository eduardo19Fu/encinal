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

  public title: string;

  public terrain: Terrain;
  public blocks: Block[];

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

  create(): void{
    this.terrainService.create(this.terrain).subscribe(
      response => {
        this.router.navigate(['/terrains/index']);
        Swal.fire(response.message, `El lote número ${response.terrain.terrainNumber} ha sido registrado con éxito!`, 'success');
      }
    );
  }

  update(): void{
    this.terrainService.update(this.terrain).subscribe(
      response => {
        this.router.navigate(['/terrains/index']);
        Swal.fire(response.message, `${response.message}`, 'success');
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

  compareBlock(o1: Block, o2: Block): boolean{
    if (o1 === undefined && o2 === undefined) {
      return true;
    }
    return o1 === null || o2 === null || o1 === undefined || o2 === undefined ? false : o1.blockId === o2.blockId;
  }

  showArea(event): void{
    this.terrain.area = this.terrain.getArea();
  }

}
