import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

import { UserService } from '../../../services/users/user.service';
import { User } from '../../../models/user';

import Swal from 'sweetalert2';

@Component({
  selector: 'app-create-user',
  templateUrl: './create-user.component.html',
  styleUrls: ['./create-user.component.css']
})
export class CreateUserComponent implements OnInit {

  public title: string;

  public user: User;

  constructor(
    private userService: UserService,
    private router: Router,
    private activatedRoute: ActivatedRoute
  ) {
    this.title = 'Registrar Usuario';
    this.user = new User();
  }

  ngOnInit(): void {
    this.loadUser();
  }

  loadUser(): void{
    this.activatedRoute.params.subscribe(params => {
        const id = params.id;

        if (id){
          this.userService.getUser(id).subscribe(
            user => {
              this.user = user;
            }
          );
        }
      }
    );
  }

  create(): void{
    this.userService.create(this.user).subscribe(
      response => {
        this.router.navigate(['/admin/users/index']);
        Swal.fire('Usuario Registrado', `El usuario ${response.message} fué creado con éxito`, 'success');
      }
    );
  }

  update(): void{
    this.userService.update(this.user).subscribe(
      response => {
        this.router.navigate(['/admin/users/index']);
        Swal.fire('Usuario Actualizado', `${response.message}`, 'success');
      }
    );
  }

}
