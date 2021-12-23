import { Component, OnInit } from '@angular/core';

import { CorrelativeService } from '../../services/correlative-service/correlative.service';
import { Correlative } from '../../models/correlative';

import { JqueryConfigs } from '../../utils/jquery-utils';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-correlatives',
  templateUrl: './correlatives.component.html',
  styles: [
  ]
})
export class CorrelativesComponent implements OnInit {

  title: string;
  jqueryConfigs: JqueryConfigs = new JqueryConfigs();

  correlatives: Correlative[];

  swalWithBootstrapButtons = Swal.mixin({
    customClass: {
      confirmButton: 'btn btn-success',
      cancelButton: 'btn btn-danger'
    },
    buttonsStyling: true
  });

  constructor(
    private correlativeService: CorrelativeService
  ) {
    this.title = 'Correlativos';
  }

  ngOnInit(): void {
    this.loadCorrelatives();
  }

  loadCorrelatives(): void{
    this.correlativeService.getCorrelatives().subscribe(
      correlatives => {
        this.correlatives = correlatives;
        this.jqueryConfigs.configDataTable('correlatives');
      }
    );
  }

  cancelCorrelative(correlative: Correlative): void{
    this.swalWithBootstrapButtons.fire({
      title: '¿Está seguro?',
      text: `¿Seguro que desea anular el correlativo con ID: ${correlative.correlativeId}`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: '¡Si, anular!',
      cancelButtonText: '¡No, cancelar!',
      reverseButtons: true
    }).then((result) => {
      if (result.isConfirmed) {

        // aqui va el codigo de confirmación para anular factura
        this.correlativeService.cancel(correlative.correlativeId).subscribe(
          response => {
            correlative.status = response.correlative.status;
            this.swalWithBootstrapButtons.fire(
              `${response.mensaje}`,
              `El correlativo ${correlative.correlativeId} ha sido anulado con éxito`,
              'success'
            );
          }
        );

        // this.sales.map(oldSale => {
        //   if (facturaVieja.idFactura === factura.idFactura) {
        //     facturaVieja.estado = factura.estado;
        //   }
        //   return factura;
        // });

      } else if (
        /* Read more about handling dismissals below */
        result.dismiss === Swal.DismissReason.cancel
      ) {
        this.swalWithBootstrapButtons.fire(
          'Proceso Cancelado',
          `El correlativo ${correlative.correlativeId} no fué anulado.`,
          'error'
        );
      }
    });
  }

}
