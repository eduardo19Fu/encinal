import { Component, OnDestroy, OnInit, AfterViewInit } from '@angular/core';

import { Client } from 'src/app/models/client';
import { ClientService } from '../../services/client-service/client.service';

import { JqueryConfigs } from '../../utils/jquery-utils';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-clients',
  templateUrl: './clients.component.html',
  styles: [
  ]
})

export class ClientsComponent implements OnInit, AfterViewInit{

  public title: string;
  public clients: Client[];
  public client: Client;

  jqueryConfigs: JqueryConfigs;

  swalWithBootstrapButtons = Swal.mixin({
    customClass: {
      confirmButton: 'btn btn-success',
      cancelButton: 'btn btn-danger'
    },
    buttonsStyling: true
  });

  constructor(
    private clientService: ClientService
  ) {
    this.title = 'Clientes';
    this.client = new Client();
    this.jqueryConfigs = new JqueryConfigs();
  }

  ngOnInit(): void {
    this.getClients();
  }

  ngAfterViewInit(): void{
  }

  getClients(): void {
    this.clientService.getClients().subscribe(
      clients => {
        this.clients = clients;
        this.jqueryConfigs.configDataTable('clients');
      }
    );
  }

  clientDetail(clientLoaded: Client): void{
    this.client = clientLoaded;
  }

  disable(client: Client): void{
    this.swalWithBootstrapButtons.fire({
      title: '¿Está seguro?',
      text: `¿Seguro que desea deshabilitar el cliente ${client.firstName + ' ' + client.lastName}?`,
      icon: 'question',
      showCancelButton: true,
      confirmButtonText: '¡Si, deshabilitar!',
      cancelButtonText: '¡No, cancelar!',
      reverseButtons: true
    }).then((result) => {
      if (result.isConfirmed) {

        // tslint:disable-next-line: deprecation
        this.clientService.disable(client).subscribe(
          response => {
            client.status = response.client.status;
            this.swalWithBootstrapButtons.fire(
              '¡Cliente dehsabiliatado con éxito!',
              'El registro ha sido deshabilitado con éxito!',
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
          'El registro no fué deshabilitado.',
          'error'
        );
      }
    });
  }

  delete(): void{}

}
