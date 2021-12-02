import { Component, Input, OnInit } from '@angular/core';
import { User } from 'src/app/models/user';

@Component({
  selector: 'app-detail-user',
  templateUrl: './detail-user.component.html',
  styleUrls: ['./detail-user.component.css']
})
export class DetailUserComponent implements OnInit {

  @Input() user: User;

  title: string;

  constructor() {
    this.title = 'Detalle de Usuario';
  }

  ngOnInit(): void {
  }

}
