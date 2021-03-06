import { AfterViewInit, Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

import { Client } from '../../../models/client';
import { IdentificationType } from '../../../models/identification-type';

import { ClientService } from '../../../services/client-service/client.service';
import { IdentificationTypeService } from '../../../services/identification-type-service/identification-type.service';

import { JqueryConfigs } from '../../../utils/jquery-utils';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-create-client',
  templateUrl: './create-client.component.html',
  styleUrls: ['./create-client.component.css']
})
export class CreateClientComponent implements OnInit, AfterViewInit {

  public title: string;
  public client: Client;

  public identificationTypes: IdentificationType[];

  public jqueryConfigs: JqueryConfigs;

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
    this.loadCurrentDate();
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
        Swal.fire(response.message, `El cliente ${response.client.firstName + ' ' + response.client.lastName} fué registrado con éxito.`, 'success');
      }
    );
  }

  update(): void{
    this.clientService.update(this.client).subscribe(
      response => {
        this.router.navigate(['/admin/clients/index']);
        Swal.fire(response.message, `El cliente ${response.client.firstName + ' ' + response.client.lastName} fué actualizado con éxito.`, 'success');
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

  loadCurrentDate(): void{
    (document.getElementById('birth-date') as HTMLInputElement).value = new Date().toISOString();
    (document.getElementById('first-name') as HTMLInputElement).value = 'Ramiro';
    (document.getElementById('email') as HTMLInputElement).value = 'ramieduar@gmail.com';
  }

}
