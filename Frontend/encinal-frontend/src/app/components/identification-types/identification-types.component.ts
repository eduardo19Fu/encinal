import { Component, OnInit } from '@angular/core';
import { IdentificationType } from 'src/app/models/identification-type';
import { IdentificationTypeService } from '../../services/identification-type-service/identification-type.service';
import { JqueryConfigs } from '../../utils/jquery-utils';

@Component({
  selector: 'app-identification-types',
  templateUrl: './identification-types.component.html',
  styles: [
  ]
})
export class IdentificationTypesComponent implements OnInit {

  title: string;
  identificationTypes: IdentificationType[];

  jqueryConfigs: JqueryConfigs;

  identificationType: IdentificationType;

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

  getIdentificationTypes(): void{
    this.identificationTypeService.getIdentificationsTypes().subscribe(
      identificationTypes => {
        this.identificationTypes = identificationTypes;
        this.jqueryConfigs.configDataTable('identification-types');
      }
    );
  }

}
