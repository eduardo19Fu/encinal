import { Component, OnInit } from '@angular/core';

import { Receipt } from '../../models/receipt';
import { ReceiptService } from '../../services/receipts/receipt.service';

import { JqueryConfigs } from '../../utils/jquery-utils';
import Swal from 'sweetalert2';


@Component({
  selector: 'app-receipts',
  templateUrl: './receipts.component.html',
  styles: [
  ]
})
export class ReceiptsComponent implements OnInit {

  title: string;
  receipts: Receipt[];

  jqueryConfigs: JqueryConfigs;

  swalWithBootstrapButtons = Swal.mixin({
    customClass: {
      confirmButton: 'btn btn-success',
      cancelButton: 'btn btn-danger'
    },
    buttonsStyling: true
  });

  constructor(
    private receiptService: ReceiptService
  ) {
    this.title = 'Listado de Pagos';
    this.jqueryConfigs = new JqueryConfigs();
  }

  ngOnInit(): void {
    this.loadReceipts();
  }

  loadReceipts(): void{
    this.receiptService.getReceipts().subscribe(
      receipts => {
        this.receipts = receipts;
        this.jqueryConfigs.configDataTable('receipts');
      }
    );
  }

  cancelReceipt(receipt: Receipt): void{
    this.swalWithBootstrapButtons.fire({
      title: '¿Está seguro?',
      text: `¿Seguro que desea anular el pago. ${receipt.receiptId}`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: '¡Si, anular!',
      cancelButtonText: '¡No, cancelar!',
      reverseButtons: true
    }).then((result) => {
      if (result.isConfirmed) {

        // aqui va el codigo de confirmación para anular factura
        this.receiptService.cancel(receipt).subscribe(
          response => {
            receipt.status = response.receipt.status;
            this.swalWithBootstrapButtons.fire(
              `${response.message}`,
              `El pago ${receipt.receiptId} ha sido anulado con éxito`,
              'success'
            );
          }
        );

      } else if (
        /* Read more about handling dismissals below */
        result.dismiss === Swal.DismissReason.cancel
      ) {
        this.swalWithBootstrapButtons.fire(
          'Proceso Cancelado',
          `El pago ${receipt.receiptId} no fué anulado.`,
          'error'
        );
      }
    });
  }

}
