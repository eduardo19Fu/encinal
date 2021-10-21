import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { IdentificationType } from '../../../models/identification-type';
import { IdentificationTypeService } from '../../../services/identification-type-service/identification-type.service';

import Swal from 'sweetalert2';

@Component({
  selector: 'app-create-identification-type',
  templateUrl: './create-identification-type.component.html',
  styleUrls: ['./create-identification-type.component.css']
})
export class CreateIdentificationTypeComponent implements OnInit {

  public title: string;
  @Input() public identificationType: IdentificationType;

  constructor(
    private identificationTypeService: IdentificationTypeService,
    private router: Router
  ) {
    this.title = 'Registrar Tipo de Identificación';
  }

  ngOnInit(): void {
  }

  create(): void{
    this.identificationTypeService.create(this.identificationType).subscribe(
      response => {
        this.router.navigate(['/admin/identification-types/index']);
        Swal.fire(response.message, `¡El tipo de documento ${response.identificationType.name} ha sido registrado con éixto!`, 'success');
      }
    );
  }

}
