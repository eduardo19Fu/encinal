import { Component, OnInit } from '@angular/core';
import { User } from '../../models/user';
import { UserService } from '../../services/users/user.service';

import Swal from 'sweetalert2';

@Component({
  selector: 'app-usuarios',
  templateUrl: './usuarios.component.html',
  styleUrls: ['./usuarios.component.css']
})
export class UsuariosComponent implements OnInit {

  title: string;
  users: User[];

  constructor(
    private userService: UserService
  ) {
    this.title = 'Usuarios';
  }

  ngOnInit(): void {
  }

}
