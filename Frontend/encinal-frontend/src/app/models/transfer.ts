import { Client } from './client';
import { User } from './user';
import { Terrain } from './terrain';
import { Sale } from './sale';

export class Transfer {
    transferId: number;
    description: string;
    createdAt: Date;

    createdBy: User;
    fromClient: Client;
    toClient: Client;
    sale: Sale;
}
