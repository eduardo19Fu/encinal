import { Component, OnInit } from '@angular/core';
import { User } from '../../models/user';
import { UserService } from '../../services/users/user.service';

import { JqueryConfigs } from '../../utils/jquery-utils';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-usuarios',
  templateUrl: './usuarios.component.html',
  styleUrls: ['./usuarios.component.css']
})
export class UsuariosComponent implements OnInit {

  title: string;
  users: User[];

  jqueryConfigs: JqueryConfigs;

  constructor(
    private userService: UserService
  ) {
    this.title = 'Usuarios';
    this.jqueryConfigs = new JqueryConfigs();
  }

  ngOnInit(): void {
    this.getUsers();
  }

  getUsers(): void{
    this.userService.getUsers().subscribe(
      users => {
        this.users = users;
        this.jqueryConfigs.configDataTable('users');
      }
    );
  }

}
