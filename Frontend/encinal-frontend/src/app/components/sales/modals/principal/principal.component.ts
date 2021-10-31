import { Component, OnInit, Output, EventEmitter, Input, OnDestroy } from '@angular/core';
import { Client } from 'src/app/models/client';

@Component({
  selector: 'app-principal',
  templateUrl: './principal.component.html',
  styleUrls: ['./principal.component.css']
})
export class PrincipalComponent implements OnInit, OnDestroy {

  flag = 1;
  @Output() customer = new EventEmitter<any>();

  customerSent: Client;

  title: string;

  constructor(
  ) {
    this.title = 'Búsqueda de Cliente';
  }

  ngOnInit(): void {
  }

  ngOnDestroy(): void {
    this.flag = 1;
  }

  catchCustomer(event): void{
    // console.log(event);
    this.customer.emit(event);
    document.getElementById('button-x').click();
    this.flag = 1;
  }

  decideFlag(item: number): void{
    this.flag = item;
  }

}
