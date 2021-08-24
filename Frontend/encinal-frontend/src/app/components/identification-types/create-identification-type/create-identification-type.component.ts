import { Component, Input, OnInit } from '@angular/core';
import { IdentificationType } from '../../../models/identification-type';
import { IdentificationTypeService } from '../../../services/identification-type-service/identification-type.service';

@Component({
  selector: 'app-create-identification-type',
  templateUrl: './create-identification-type.component.html',
  styleUrls: ['./create-identification-type.component.css']
})
export class CreateIdentificationTypeComponent implements OnInit {

  title: string;
  @Input() identificationType: IdentificationType;

  constructor(
    private identificationTypeService: IdentificationTypeService
  ) {
    this.title = 'Registrar Tipo de Identificación';
  }

  ngOnInit(): void {
  }

  create(): void{}

}
