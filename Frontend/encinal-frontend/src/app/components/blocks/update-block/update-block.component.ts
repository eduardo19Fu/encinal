import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { BlockService } from '../../../services/block-service/block.service';
import { Block } from '../../../models/block';

import Swal from 'sweetalert2';

@Component({
  selector: 'app-update-block',
  templateUrl: './update-block.component.html',
  styles: [
  ]
})
export class UpdateBlockComponent implements OnInit {

  title: string;
  block: Block;

  constructor(
    private blockService: BlockService,
    private router: Router,
    private activatedRoute: ActivatedRoute
  ) {
    this.title = 'Actualizar Manzana';
    this.block = new Block();
  }

  ngOnInit(): void {
    this.loadBlock();
  }

  loadBlock(): void{
    this.activatedRoute.params.subscribe(
      params => {
        const id = params.id;
        if (id) {
          this.blockService.getBlock(id).subscribe(
            block => this.block = block
          );
        }
      }
    );
  }

  update(): void{
    this.blockService.update(this.block).subscribe(
      response => {
        this.router.navigate(['/admin/blocks/index']);
        Swal.fire(response.message, `La manzana no. ${response.block.blockNumber} ha sido actualizada!`, 'success');
      }
    );
  }

}
