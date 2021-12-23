import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

import { Correlative } from '../../../models/correlative';
import { UserAuxiliar } from '../../../models/user-auxiliar';
import { User } from '../../../models/user';

import { UserService } from '../../../services/users/user.service';
import { CorrelativeService } from '../../../services/correlative-service/correlative.service';

import Swal from 'sweetalert2';
import { AuthService } from '../../../services/users/auth.service';

@Component({
  selector: 'app-create-correlative',
  templateUrl: './create-correlative.component.html',
  styles: [
  ]
})
export class CreateCorrelativeComponent implements OnInit {

  title: string;

  correlative: Correlative;
  users: User[];

  constructor(
    private correlativeService: CorrelativeService,
    private userService: UserService,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private authService: AuthService
  ) {
    this.title = 'Registrar Correlativo';
    this.correlative = new Correlative();
  }

  ngOnInit(): void {
    this.loadUsers();
  }

  loadUsers(): void{
    this.userService.getUsers().subscribe(
      users => {
        this.users = users;
      }
    );
  }

  loadUser(): void{
    this.userService.getUser(+this.authService.user.userId).subscribe(
      response => {
        this.correlative.createdBy = response;
      }
    );
  }

  create(): void{
    this.loadUser();
    if (this.correlative.createdBy){
      this.correlativeService.create(this.correlative).subscribe(
        response => {
          this.router.navigate(['/correlatives/index']);
          // tslint:disable-next-line: max-line-length
          Swal.fire(response.message, `Correlativo ${response.correlative.correlativeId} ha sido registrado y asignado con éxito.`, 'success');
        }
      );
    } else{
      console.log('No esta pasando nada');
    }
  }

  update(): void{}

  changeCurrentField(event): void{
    this.correlative.currentCorrelative = event;
  }

}
