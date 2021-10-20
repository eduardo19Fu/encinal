import { Component, OnInit } from '@angular/core';
import { IdentificationType } from 'src/app/models/identification-type';
import { IdentificationTypeService } from '../../services/identification-type-service/identification-type.service';
import { JqueryConfigs } from '../../utils/jquery-utils';

import Swal from 'sweetalert2';

const swalWithBootstrapButtons = Swal.mixin({
  customClass: {
    confirmButton: 'btn btn-success',
    cancelButton: 'btn btn-danger'
  },
  buttonsStyling: true
});

@Component({
  selector: 'app-identification-types',
  templateUrl: './identification-types.component.html',
  styles: [
  ]
})
export class IdentificationTypesComponent implements OnInit {

  public title: string;
  public identificationTypes: IdentificationType[];

  public jqueryConfigs: JqueryConfigs;

  public identificationType: IdentificationType;

  constructor(
    private identificationTypeService: IdentificationTypeService
  ) {
    this.title = 'Tipos de Identificación Disponibles';
    this.jqueryConfigs = new JqueryConfigs();
    this.identificationType = new IdentificationType();
  }

  ngOnInit(): void {
    this.getIdentificationTypes();
  }

  getIdentificationTypes(): void {
    this.identificationTypeService.getIdentificationsTypes().subscribe(
      identificationTypes => {
        this.identificationTypes = identificationTypes;
        this.jqueryConfigs.configDataTable('identification-types');
      }
    );
  }

  delete(identificationType: IdentificationType): void {
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
        this.identificationTypeService.delete(identificationType.identificationTypeId).subscribe(
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
