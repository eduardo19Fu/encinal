import { Component, Input, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';
import { Router, ActivatedRoute } from '@angular/router';

import { Terrain } from '../../../models/terrain';
import { Seller } from 'src/app/models/seller';
import { Client } from '../../../models/client';
import { Payment } from '../../../models/payment';
import { PaymentAgreement } from '../../../models/payment-agreement';
import { SaleType } from '../../../models/sale-type';
import { Sale } from '../../../models/sale';

import { SellerService } from '../../../services/seller-service/seller.service';
import { TerrainService } from '../../../services/terrain-service/terrain.service';
import { SaleService } from '../../../services/sale-service/sale.service';
import { ClientService } from '../../../services/client-service/client.service';
import { SaleTypeService } from '../../../services/sale-types-service/sale-type.service';
import { PaymentAgreementService } from '../../../services/payment-agreement/payment-agreement.service';
import { ItemService } from '../../../services/items/item.service';

import { JqueryConfigs } from '../../../utils/jquery-utils';
import Swal from 'sweetalert2';
import * as moment from 'moment';

@Component({
  selector: 'app-create-sale',
  templateUrl: './create-sale.component.html',
  styleUrls: ['./create-sale.component.css']
})
export class CreateSaleComponent implements OnInit, OnDestroy {

  public title: string;
  public contado: boolean;
  public cuotas: boolean;

  public terrains: Terrain[];
  public sellers: Seller[];
  public customers: Client[];
  public saleTypes: SaleType[];
  public payments: Payment[] = [];

  public customer: Client;
  public saleType: SaleType;
  public seller: Seller;
  public sale: Sale;
  public terrain: Terrain;
  public paymentAgreement: PaymentAgreement;
  public payment: Payment;

  saleTypeSubscription: Subscription;

  public paymentDate: Date;
  public paymentDateValue: string;
  public years: number;
  public interestRate: number;
  public hitch: number; // Enganche
  public monthlyFee: number;

  public jqueryConfigs: JqueryConfigs = new JqueryConfigs();

  constructor(
    private saleService: SaleService,
    private terrainService: TerrainService,
    private sellerService: SellerService,
    private customerService: ClientService,
    private saleTypeService: SaleTypeService,
    private paymentAgreementService: PaymentAgreementService,
    private itemService: ItemService,
    private router: Router,
    private activatedRoute: ActivatedRoute
  ) {
    this.title = 'Venta';
    this.sale = new Sale();
    this.paymentAgreement = new PaymentAgreement();
    this.payments = [];
    // this.paymentDateValue = new Date().toISOString().slice(0, 10);
    this.paymentDateValue = moment(new Date()).format('yyyy-MM-DD');
  }

  ngOnInit(): void {
    this.loadSaleTypes();
    this.loadTarrains();
    this.loadSellers();
    this.loadInterestRate();
    this.loadTerrain();
  }

  ngOnDestroy(): void {
    // this.saleTypeSubscription.unsubscribe();
  }

  create(): void { }

  loadTerrain(): void {
    this.activatedRoute.params.subscribe(
      params => {
        const id = params.terrainId;

        if (id) {
          this.terrainService.getTerrain(id).subscribe(
            terrain => {
              this.sale.terrain = terrain;
              this.terrain = terrain;
              console.log(this.terrain);
            }
          );
        }
      }
    );
  }

  loadInterestRate(): void {
    this.itemService.getItem(2).subscribe(
      item => {
        this.interestRate = item.itemValue;
      }
    );
  }

  loadSaleTypes(): void {
    this.saleTypeService.getSaleTypes().subscribe(
      saleTypes => this.saleTypes = saleTypes
    );
  }

  loadTarrains(): void {
    this.terrainService.getTerrainsOnSale().subscribe(
      terrains => this.terrains = terrains
    );
  }

  loadSellers(): void {
    this.sellerService.getSellers().subscribe(
      sellers => this.sellers = sellers
    );
  }

  loadCustomers(): void {
    this.customerService.getActiveCustomers().subscribe(
      response => {
        this.customers = response;
        // console.log(this.customers);
      }
    );
  }

  loadCustomer(event): void {
    this.customer = event;
    // console.log(this.customer);
    (document.getElementById('client') as HTMLInputElement).value = this.customer.firstName + ' ' + this.customer.lastName;
  }

  loadSeller(event): void {
    this.seller = event;
    // console.log(this.seller);
    (document.getElementById('seller') as HTMLInputElement).value = this.seller.firstName + ' ' + this.seller.lastName;
  }

  mostrarTotal(event): void {
    this.terrain = event;
  }

  setSaleType(event): void {
    this.saleType = event;
  }

  createSale(): void {
    if (this.terrain) {
      this.sale.client = this.customer;
      this.sale.seller = this.seller;
      this.sale.total = this.terrain.price;

      this.saleService.create(this.sale).subscribe(
        response => {
          this.router.navigate(['/sales/index']);
          Swal.fire(response.message, `La venta del lote no. ${response.sale.terrain.terrainNumber} ha sido completada.`, 'success');
        }
      );
    }
  }

  /******* CUOTAS NIVELADAS ********/

  calculateMonthlyFee(): void {
    if (this.hitch >= 0) {
      this.payments = [];
      let interest: number; // El interés ingresado por el usuario
      let months: number; // La cantidad de meses para los pagos

      interest = (this.interestRate / 12) / 100;
      months = this.years;
      let pv: number = this.terrain.price - this.hitch; // Valor restante despues de haber operado el enganche.

      const pmt: number = pv / ((1 - (Math.pow((1 + interest), (-1 * months)))) / interest); // Calculo de cuota fija.
      this.monthlyFee = pmt;

      // Inicio del ciclo de calculo de cutoas y saldo restante.
      for (let i = 1; i <= months; i++) {
        this.payment = new Payment();

        this.payment.paymentNumber = i;
        this.payment.interestRateGenerated = pv * interest;
        this.payment.principalValue = pmt - this.payment.interestRateGenerated; // AMORTIZACION
        this.payment.paymentTotal = this.monthlyFee;
        this.payment.provisional_payment = 0.0;

        const date = moment((document.getElementById('payment-date') as HTMLInputElement).value).toDate();
        const newDate = moment(date).add(i, 'months').toDate();

        this.payment.expireDate = new Date(newDate);

        pv = pv - this.payment.principalValue; // Saldo restante por cada iteracion.
        if (pv < 0) {
          this.payment.remainingBalance = 0; // El primer saldo negativo, valdrá cero
        } else {
          this.payment.remainingBalance = pv; // Mientras  no se anegativo asignará pv como saldo restante.
        }

        this.payments.push(this.payment);

      }
      
      this.jqueryConfigs.configFeesDataTable('fees');
    } else {
      Swal.fire('Enganche Vacío', 'El valor del enganche no puede ser vacío, por favor ingrese un valor.', 'warning');
    }
  }

  createFeesSale(): void {
    if (this.terrain) {
      this.sale.client = this.customer;
      this.sale.seller = this.seller;
      this.sale.total = this.terrain.price;

      // console.log(this.sale);
      this.saleService.create(this.sale).subscribe(
        response => {
          this.paymentAgreement.hitch = this.hitch;
          this.paymentAgreement.payments = this.payments;
          this.paymentAgreement.totalAgreement = this.calcularTotal(this.paymentAgreement);
          this.paymentAgreement.sale = response.sale;
          this.paymentAgreement.interestRate = this.interestRate;
          this.paymentAgreement.totalPayments = this.paymentAgreement.payments.length;
          // console.log(this.paymentAgreement);

          this.createAgreement(this.paymentAgreement);
        }
      );
    }
  }

  calcularTotal(paymentAgreement: PaymentAgreement): number {
    let sum = 0;
    paymentAgreement.payments.forEach((e, index) => {
      sum = sum + e.paymentTotal;
    });
    return sum;
  }

  createAgreement(agreement: PaymentAgreement): void {
    this.paymentAgreementService.create(this.paymentAgreement).subscribe(
      response => {
        this.router.navigate(['/sales/index']);
        Swal.fire('Venta Realizada con éxito',
          `Se ha creado con éxito el acuerdo de pagos`, 'success');

        // Generate PDF
        this.generateSalePDF(response.paymentAgreement.paymentAgreementId);
      }
    );
  }

  generateSalePDF(agreementId: number): void {
    this.paymentAgreementService.getPaymentAgreementPDF(agreementId).subscribe(
      response => {
        const url = window.URL.createObjectURL(response.data);
        const a = document.createElement('a');
        document.body.appendChild(a);
        a.setAttribute('style', 'display: none');
        a.setAttribute('target', 'blank');
        a.href = url;
        /*
          opcion para pedir descarga de la respuesta obtenida
          a.download = response.filename;
        */
        window.open(a.toString(), '_blank');
        window.URL.revokeObjectURL(url);
        a.remove();
      },
      error => {
        console.log(error);
      }
    );
  }

  compareTerrain(o1: Terrain, o2: Terrain): boolean {
    if (o1 === undefined && o2 === undefined) {
      return true;
    }
    return o1 === null || o2 === null || o1 === undefined || o2 === undefined ? false : o1.terrainId === o2.terrainId;
  }

  reloadPage(): void {
    window.location.reload();
  }
}
