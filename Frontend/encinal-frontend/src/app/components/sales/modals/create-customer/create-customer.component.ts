import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { Client } from '../../../../models/client';
import { ClientService } from '../../../../services/client-service/client.service';
import { IdentificationTypeService } from '../../../../services/identification-type-service/identification-type.service';
import { IdentificationType } from '../../../../models/identification-type';

import Swal from 'sweetalert2';

@Component({
  selector: 'app-create-customer',
  templateUrl: './create-customer.component.html',
  styleUrls: ['./create-customer.component.css']
})
export class CreateCustomerComponent implements OnInit {

  title: string;
  client: Client;
  identifications: IdentificationType[];

  @Output() customer = new EventEmitter<any>();

  constructor(
    private clientService: ClientService,
    private identificationTypeService: IdentificationTypeService
  ) {
    this.client = new Client();
  }

  ngOnInit(): void {
    this.loadIdentificationTypes();
  }

  create(): void{
    this.clientService.create(this.client).subscribe(
      response => {
        this.customer.emit(response.client);
        Swal.fire(response.message, `Cliente ${response.client.clientId} ha sido registrado con éxito`, 'success');
      }
    );
  }

  loadIdentificationTypes(): void{
    this.identificationTypeService.getIdentificationsTypes().subscribe(
      identificationTypes => this.identifications = identificationTypes
    );
  }

}
