import { SaleType } from './sale-type';
import { Client } from './client';
import { Seller } from './seller';
import { Terrain } from './terrain';

export class Sale {

    saleId: number;
    saleDate: Date;
    total: number;

    saleType: SaleType;
    client: Client;
    seller: Seller;
    terrain: Terrain;
}
