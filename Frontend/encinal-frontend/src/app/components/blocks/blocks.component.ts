import { Component, OnInit } from '@angular/core';

import { BlockService } from '../../services/block-service/block.service';
import { Block } from '../../models/block';

import { JqueryConfigs } from 'src/app/utils/jquery-utils';
import Swal from 'sweetalert2';

const swalWithBootstrapButtons = Swal.mixin({
  customClass: {
    confirmButton: 'btn btn-success',
    cancelButton: 'btn btn-danger'
  },
  buttonsStyling: true
});

@Component({
  selector: 'app-blocks',
  templateUrl: './blocks.component.html',
  styles: [
  ]
})
export class BlocksComponent implements OnInit {

  public title: string;
  public blocks: Block[];
  public block: Block;

  jqueryConfigs: JqueryConfigs;

  constructor(
    private blockService: BlockService
  ) {
    this.title = 'Manzanas';
    this.jqueryConfigs = new JqueryConfigs();
    this.block = new Block();
  }

  ngOnInit(): void {
    this.getBlocks();
  }

  getBlocks(): void{
    this.blockService.getBlocks().subscribe(blocks => {
      this.blocks = blocks;
      this.jqueryConfigs.configDataTable('blocks');
      this.jqueryConfigs.configToolTip();
    });
  }

  delete(block: Block): void{
    swalWithBootstrapButtons.fire({
      title: '¿Está Seguro?',
      text: 'No será capaz de recuperar el registro una vez eliminado (es posible que el registro no pueda ser eliminado si este está siendo' +
        'referenciado en otro lugar).',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Si, borrar!',
      cancelButtonText: 'No, cancelar!',
      reverseButtons: true
    }).then((result) => {
      if (result.isConfirmed) {
        this.blockService.delete(block.blockId).subscribe(
          response => {
            swalWithBootstrapButtons.fire(
              'Eliminado!',
              'El registro fué eliminado con éxito',
              'success'
            );
          }
        );
      } else if (
        /* Read more about handling dismissals below */
        result.dismiss === Swal.DismissReason.cancel
      ) {
        swalWithBootstrapButtons.fire(
          'Cancelado',
          'El registro no fué eliminado de la Base de Datos',
          'error'
        );
      }
    });
  }

}
