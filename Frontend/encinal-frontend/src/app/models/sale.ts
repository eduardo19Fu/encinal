import { SaleType } from './sale-type';
import { Client } from './client';
import { Seller } from './seller';
import { Terrain } from './terrain';
import { Status } from './status';
import { PaymentAgreement } from './payment-agreement';

export class Sale {

    saleId: number;
    saleDate: Date;
    total: number;

    saleType: SaleType;
    client: Client;
    seller: Seller;
    terrain: Terrain;
    status: Status;
    paymentAgreement: PaymentAgreement;
}
