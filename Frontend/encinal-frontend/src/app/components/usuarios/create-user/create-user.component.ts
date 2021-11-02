import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

import { UserService } from '../../../services/users/user.service';
import { User } from '../../../models/user';

import Swal from 'sweetalert2';
import { Role } from '../../../models/role';
import { UserAuxiliar } from '../../../models/user-auxiliar';

@Component({
  selector: 'app-create-user',
  templateUrl: './create-user.component.html',
  styleUrls: ['./create-user.component.css']
})
export class CreateUserComponent implements OnInit {

  public title: string;

  public user: User;
  public userAuxiliar: UserAuxiliar;
  public roles: Role[];
  filas: Role[] = [];

  constructor(
    private userService: UserService,
    private router: Router,
    private activatedRoute: ActivatedRoute
  ) {
    this.title = 'Registrar Usuario';
    this.user = new User();
    this.userAuxiliar = new UserAuxiliar();
  }

  ngOnInit(): void {
    this.loadUser();
    this.getRoles();
  }

  loadUser(): void{
    this.activatedRoute.params.subscribe(params => {
        const id = params.id;

        if (id){
          this.userService.getUser(id).subscribe(
            user => {
              this.userAuxiliar = user;
              this.filas = this.userAuxiliar.roles;
            }
          );
        }
      }
    );
  }

  create(): void{
    this.userAuxiliar.roles = this.filas;

    this.userService.create(this.userAuxiliar).subscribe(
      response => {
        this.router.navigate(['/admin/users/index']);
        Swal.fire('Usuario Registrado', `El usuario ${response.message} fué creado con éxito`, 'success');
      }
    );
  }

  update(): void{
    this.userService.update(this.userAuxiliar).subscribe(
      response => {
        this.router.navigate(['/admin/users/index']);
        Swal.fire('Usuario Actualizado', `${response.message}`, 'success');
      }
    );
  }

  getRoles(): void{
    this.userService.getRoles().subscribe(
      roles => this.roles = roles
    );
  }

  loadRole(event): void {
    this.roles.forEach(role => {
      // tslint:disable-next-line: triple-equals
      if (role.roleId == event){
        this.filas.push(role);
      }
    });
  }

  deleteFila(index: number): void{
    this.filas.splice(index, 1);
  }

}
