import { Component, Input, OnInit } from '@angular/core';

import { Block } from 'src/app/models/block';
import { BlockService } from '../../../services/block-service/block.service';

import Swal from 'sweetalert2';
import { Router } from '@angular/router';

@Component({
  selector: 'app-create-block',
  templateUrl: './create-block.component.html',
  styleUrls: ['./create-block.component.css']
})
export class CreateBlockComponent implements OnInit {

  title: string;

  @Input() public block: Block;

  constructor(
    private blockService: BlockService,
    private router: Router
  ) {
    this.title = 'Registrar Manzana';
    this.block = new Block();
  }

  ngOnInit(): void {
  }

  create(): void{
    this.blockService.create(this.block).subscribe(
      response => {
        // this.router.navigate(['/admin/blocks/index']);
        Swal.fire('Manzana registrada', `${response.message}: ${response.block.blockNumber}`, 'success');
      }
    );
  }

  update(): void{
    this.blockService.update(this.block).subscribe(
      response => {
        this.router.navigate(['/admin/blocks/index']);
        Swal.fire('Manzana Actualizada', `${response.message}`, 'success');
      }
    );
  }

  setRemaining(event): void{
    this.block.remaining = event;
  }

}
