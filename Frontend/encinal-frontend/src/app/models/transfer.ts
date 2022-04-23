import { Client } from './client';
import { User } from './user';
import { Terrain } from './terrain';

export class Transfer {
    transferId: number;
    description: string;
    createdAt: Date;

    createdBy: User;
    fromClient: Client;
    toClient: Client;
    terrain: Terrain;
}
