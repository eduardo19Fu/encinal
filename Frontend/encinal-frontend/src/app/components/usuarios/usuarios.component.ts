import { Component, OnInit } from '@angular/core';
import { User } from '../../models/user';
import { UserService } from '../../services/users/user.service';

import { JqueryConfigs } from '../../utils/jquery-utils';
import Swal from 'sweetalert2';
import { AuthService } from '../../services/users/auth.service';

@Component({
  selector: 'app-usuarios',
  templateUrl: './usuarios.component.html',
  styleUrls: ['./usuarios.component.css']
})
export class UsuariosComponent implements OnInit {

  public title: string;
  public userSelected: User;
  public users: User[];

  jqueryConfigs: JqueryConfigs;

  swalWithBootstrapButtons = Swal.mixin({
    customClass: {
      confirmButton: 'btn btn-success',
      cancelButton: 'btn btn-danger'
    },
    buttonsStyling: true
  });

  constructor(
    private userService: UserService,
    private authService: AuthService
  ) {
    this.title = 'Usuarios';
    this.jqueryConfigs = new JqueryConfigs();
    this.userSelected = new User();
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

  openDetail(user: User): void{
    this.userSelected = user;
  }

  disable(user: User): void{
    this.swalWithBootstrapButtons.fire({
      title: '¿Está seguro?',
      text: `¿Seguro que desea deshabilitar el usuario ${user.username}?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: '¡Si, eliminar!',
      cancelButtonText: '¡No, cancelar!',
      reverseButtons: true
    }).then((result) => {
      if (result.isConfirmed) {

        user.enabled = false;

        // tslint:disable-next-line: deprecation
        this.userService.delete(user).subscribe(
          response => {
            if (this.authService.user.userId === user.userId) {
              this.authService.logout();
            } else {
              this.swalWithBootstrapButtons.fire(
                '¡Usuario dehsabiliatado con éxito!',
                'El registro ha sido eliminado con éxito!',
                'success'
              );
            }
          }
        );
      } else if (
        /* Read more about handling dismissals below */
        result.dismiss === Swal.DismissReason.cancel
      ) {
        this.swalWithBootstrapButtons.fire(
          'Proceso Cancelado',
          'El registro no fué eliminado de la base de datos.',
          'error'
        );
      }
    });
  }

}
