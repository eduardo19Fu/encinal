import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { Client } from 'src/app/models/client';
import { Receipt } from '../../../models/receipt';
import { ReceiptType } from '../../../models/receipt-type';
import { ReceiptDetail } from '../../../models/receipt-detail';
import { PaymentAgreement } from 'src/app/models/payment-agreement';
import { User } from 'src/app/models/user';
import { Payment } from 'src/app/models/payment';

import { ClientService } from '../../../services/client-service/client.service';
import { PaymentAgreementService } from '../../../services/payment-agreement/payment-agreement.service';
import { ReceiptService } from '../../../services/receipts/receipt.service';
import { AuthService } from '../../../services/users/auth.service';
import { ItemService } from '../../../services/items/item.service';
import { UserService } from '../../../services/users/user.service';

import Swal from 'sweetalert2';

@Component({
  selector: 'app-create-receipt',
  templateUrl: './create-receipt.component.html',
  styles: [
  ]
})
export class CreateReceiptComponent implements OnInit {

  title: string;
  valorMora: number;
  totalIngresado: number;

  user: User;
  client: Client;
  paymentAgreement: PaymentAgreement;
  receipt: Receipt;
  receiptType: ReceiptType;

  receiptTypes: ReceiptType[];
  paymentAgreements: PaymentAgreement[];
  payments: Payment[];
  paymentsChecked: Payment[] = [];

  constructor(
    private clientService: ClientService,
    private paymentAgreementService: PaymentAgreementService,
    private receiptService: ReceiptService,
    private itemService: ItemService,
    private authService: AuthService,
    private userService: UserService,
    private router: Router
  ) {
    this.title = 'Crear Pago';
    this.user = new User();
    this.receipt = new Receipt();
    this.receiptType = new ReceiptType();
    this.paymentAgreement = new PaymentAgreement();
  }

  ngOnInit(): void {
    this.user = this.authService.user;
    this.userService.getUser(this.user.userId).subscribe(
      response => {
        this.receipt.registerBy = response;
      }
    );
    // this.loadActivePaymentAgreements();
    this.loadReceiptTypes();
    this.loadMora();
  }

  loadMora(): void {
    this.itemService.getItem(1).subscribe(
      response => {
        this.valorMora = response.itemValue / 100;
        console.log(this.valorMora);
      }
    );
  }

  loadClient(event): void {
    this.client = event;
    this.loadActivePaymentAgreements();
  }

  loadActivePaymentAgreements(): void {
    this.paymentAgreementService.getActivePaymentAgreements(this.client).subscribe(
      response => {
        this.paymentAgreements = response;
      }
    );
  }

  loadReceiptTypes(): void {
    this.receiptService.getTypes().subscribe(
      types => {
        this.receiptTypes = types;
      }
    );
  }

  loadPayments(event): void {
    this.paymentAgreementService.getPaymentAgreement(event.target.value).subscribe(
      paymentAgreement => {
        this.paymentAgreement = paymentAgreement;
        this.payments = this.paymentAgreement.payments;
      }
    );
  }

  setReceiptType(event): void {
    this.receiptType = event;
  }

  /** */

  paymentOnCheck(payment: Payment, event: any): void {
    if (this.receipt.items.length <= 0) {
      if (event.target.checked) {

        const item = new ReceiptDetail();
        const moraIngresada = (+(document.getElementById('arrears-value') as HTMLInputElement).value) / 100;

        item.payment = payment;
        item.subtotal = item.calcularSubTotal();

        this.receipt.items.push(item);
        this.receipt.total = this.receipt.calcularTotal();

      } else {
        this.receipt.items.forEach((item: ReceiptDetail, i: number) => {
          if (item.payment.paymentId === payment.paymentId) {
            this.receipt.items.splice(i, 1);
          }
        });
      }
    } else {
      Swal.fire('¡Acción no Permitida!', 'No se puede cobrar más de una cuota en un mismo recibo.', 'warning');
    }
  }

  searchClient(): void {
    const id = (document.getElementById('buscar') as HTMLInputElement).value;

    this.clientService.getClientByIdentification(id).subscribe(
      response => {
        this.client = response;
      }
    );
  }

  createPayment(): void {
    this.receipt.receiptType = this.receiptType;
    this.receipt.paymentAgreement = this.paymentAgreement;
    let restante = 0;
    // this.receipt.arrearsValue = (+(document.getElementById('arrears-value') as HTMLInputElement).value) / 100;
    // this.receipt.arrearsValue = 0;

    if (this.receipt.receiptType.receiptTypeId === 1) {

      if (this.totalIngresado || this.totalIngresado > 0) {

        if (this.receipt.items.length > 0) {

          if (this.receipt.arrearsValue > 0) {
            this.receipt.total = (this.receipt.calcularTotal() * this.receipt.arrearsValue) + this.receipt.calcularTotal();
            console.log(this.receipt.total);

            // calculates the remainder between the amount paid in and the total of the payments
            restante = this.totalIngresado - this.receipt.total;
            console.log(this.totalIngresado);

            if (restante === 0) {
              this.receiptService.create(this.receipt).subscribe(
                response => {
                  this.router.navigate(['/receipts/index']);
                  Swal.fire('¡Pago Éxitoso!', 'El pago se ha realizado con éxito.', 'success');
                }
              );
            } else if (restante < 0) {
              Swal.fire('Advertencia', 'Valor ingresado no puede ser menor que el total a pagar por las cuotas.', 'warning');
            } else {
              this.receiptService.getType(2).subscribe();
            }

          } else {
            this.receipt.total = this.receipt.calcularTotal();

            this.receiptService.create(this.receipt).subscribe(
              response => {
                this.router.navigate(['/receipts/index']);
                Swal.fire('¡Pago Éxitoso!', 'El pago se ha realizado con éxito.', 'success');
              }
            );
          }

        } else {
          Swal.fire('Advertencia', 'No se ha marcado ninguna cuota para realizar el pago.', 'warning');
        }

      } else {
        Swal.fire('Advertencia', 'Monto Ingresado debe ser mayor a 0.', 'warning');
      }

    } else if (this.receipt.receiptType.receiptTypeId === 2) {
      this.receipt.total = +(document.getElementById('abono') as HTMLInputElement).value;

      // if (this.receipt.total >= 5000) {
      this.receiptService.create(this.receipt).subscribe(
        response => {
          this.router.navigate(['/receipts/index']);
          Swal.fire('¡Pago Éxitoso!', 'El pago se ha realizado con éxito.', 'success');
        }
      );
      // } else {
      //   Swal.fire('Monto Inválido', 'No se pueden efectuar abonos a capital menores a Q. 5,000.00', 'warning');
      // }
    }
  }

  cambioMora(event: any): number {
    this.receipt.arrearsValue = (+(event) / 100);
    // console.log(this.receipt.arrearsValue);
    if (this.receipt.arrearsValue > 0 && this.receipt.total > 0) {
      this.receipt.total = (this.receipt.calcularTotal() * this.receipt.arrearsValue) + this.receipt.calcularTotal();

      // this.receiptService.create(this.receipt).subscribe(
      //   response => {
      //     this.router.navigate(['/receipts/index']);
      //     Swal.fire('¡Pago Éxitoso!', 'El pago se ha realizado con éxito.', 'success');
      //   }
      // );
    } else {
    }
    return this.receipt.arrearsValue;
  }

}
