import { AfterViewInit, Component, OnInit } from '@angular/core';
import { Client } from '../../../models/client';
import { ClientService } from '../../../services/client-service/client.service';
import { IdentificationTypeService } from '../../../services/identification-type-service/identification-type.service';
import { IdentificationType } from '../../../models/identification-type';
import { JqueryConfigs } from '../../../utils/jquery-utils';

import Swal from 'sweetalert2';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-create-client',
  templateUrl: './create-client.component.html',
  styleUrls: ['./create-client.component.css']
})
export class CreateClientComponent implements OnInit, AfterViewInit {

  title: string;
  client: Client;

  identificationTypes: IdentificationType[];

  jqueryConfigs: JqueryConfigs;

  constructor(
    private clientService: ClientService,
    private identificationTypeService: IdentificationTypeService,
    private router: Router,
    private activatedRoute: ActivatedRoute
  ) {
    this.title = 'Registrar Nuevo Cliente';
    this.client = new Client();
    this.jqueryConfigs = new JqueryConfigs();
  }

  ngOnInit(): void {
    this.loadIdentificationTypes();
    this.loadClient();
  }

  ngAfterViewInit(): void {
    // this.jqueryConfigs.configValidation();
  }

  loadClient(): void{
    this.activatedRoute.params.subscribe(params => {
      const id = params.id;
      if (id){
        this.clientService.getClient(id).subscribe(
          client => this.client = client
        );
      }
    });
  }

  create(): void {
    this.clientService.create(this.client).subscribe(
      response => {
        this.router.navigate(['/admin/clients/index']);
        console.log(response);
        Swal.fire(response.message, `El cliente ${response.client.firstName + ' ' + response.client.lastName} fué registrado con éxito.`, 'success');
      }
    );
  }

  loadIdentificationTypes(): void {
    this.identificationTypeService.getIdentificationsTypes().subscribe(
      identificationTypes => this.identificationTypes = identificationTypes
    );
  }

  compareIdentification(o1: IdentificationType, o2: IdentificationType): boolean{
    if (o1 === undefined && o2 === undefined) {
      return true;
    }
    return o1 === null || o2 === null || o1 === undefined || o2 === undefined ? false : o1.identificationTypeId === o2.identificationTypeId;
  }

}
