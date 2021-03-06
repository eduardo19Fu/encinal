import { Component, OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';

import { Sale } from 'src/app/models/sale';
import { Block } from '../../models/block';
import { SaleService } from '../../services/sale-service/sale.service';
import { BlockService } from '../../services/block-service/block.service';

import { JqueryConfigs } from '../../utils/jquery-utils';

import * as moment from 'moment';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-sales',
  templateUrl: './sales.component.html',
  styleUrls: ['./sales.component.css']
})
export class SalesComponent implements OnInit {

  public title: string;
  public sales: Sale[];
  public blocks: Block[];

  public iniDate: Date;
  public endDate: Date;

  public jqueryConfigs: JqueryConfigs;

  iniDateValue: string;
  endDateValue: string;

  swalWithBootstrapButtons = Swal.mixin({
    customClass: {
      confirmButton: 'btn btn-success',
      cancelButton: 'btn btn-danger'
    },
    buttonsStyling: true
  });

  constructor(
    private saleService: SaleService,
    private blockService: BlockService
  ) {
    this.title = 'Ventas Realizadas';
    this.jqueryConfigs = new JqueryConfigs();
    // this.iniDateValue = new Date().toISOString().slice(0, 10);
    this.iniDateValue = moment(new Date()).format('yyyy-MM-DD');
    this.endDateValue = moment(new Date()).format('yyyy-MM-DD');
  }

  ngOnInit(): void {
    // this.loadSales();
    this.loadBlocks();
  }

  loadSales(): void{
    this.saleService.getSales().subscribe(
      sales => {
        this.sales = sales;
        this.jqueryConfigs.configDataTable('sales');
      }
    );
  }

  searchSalesDate(): void{
    this.iniDate = moment((document.getElementById('init-date') as HTMLInputElement).value).toDate();
    this.endDate = moment((document.getElementById('end-date') as HTMLInputElement).value).toDate();

    this.saleService.getSalesByDate(this.iniDate, this.endDate).subscribe(
      sales => {
        this.sales = sales;
        this.jqueryConfigs.configDataTable('sales');
      }
    );
  }

  loadBlocks(): void{
    this.blockService.getBlocks().subscribe(
      blocks => {
        this.blocks = blocks;
      }
    );
  }

  searchSalesByBlock(): void{
    const blockId = +(document.getElementById('blocks') as HTMLSelectElement).value;
    this.saleService.getSalesByBlock(blockId).subscribe(
      sales => {
        this.sales = sales;
      }
    );
  }

  searchSalesByBlockAndDate(): void{
    this.iniDate = moment((document.getElementById('init-date') as HTMLInputElement).value).toDate();
    this.endDate = moment((document.getElementById('end-date') as HTMLInputElement).value).toDate();
    const blockId = +(document.getElementById('blocks') as HTMLSelectElement).value;

    this.saleService.getSalesByBlockAndDate(blockId, this.iniDate, this.endDate).subscribe(
      sales => {
        this.sales = sales;
      }
    );
  }

  searchAllSales(): void{}

  cancel(sale: Sale): void{
    this.swalWithBootstrapButtons.fire({
      title: '??Est?? seguro?',
      text: `??Seguro que desea anular la factura No. ${sale.saleId}`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: '??Si, anular!',
      cancelButtonText: '??No, cancelar!',
      reverseButtons: true
    }).then((result) => {
      if (result.isConfirmed) {

        // aqui va el codigo de confirmaci??n para anular factura
        this.saleService.cancel(sale.saleId).subscribe(
          response => {
            sale.status = response.sale.status;
            this.swalWithBootstrapButtons.fire(
              `${response.mensaje}`,
              `La venta No. ${sale.saleId} ha sido anulada con ??xito`,
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
          `La venta No. ${sale.saleId} no fu?? anulada.`,
          'error'
        );
      }
    });
  }
}
