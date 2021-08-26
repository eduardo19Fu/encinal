import { Component, Input, OnInit } from '@angular/core';
import { Client } from '../../../models/client';

@Component({
  selector: 'app-detail-client',
  templateUrl: './detail-client.component.html',
  styleUrls: ['./detail-client.component.css']
})
export class DetailClientComponent implements OnInit {

  title: string;
  @Input() client: Client;

  constructor() {
    this.title = 'Detalle de Cliente';
  }

  ngOnInit(): void {
  }

}
