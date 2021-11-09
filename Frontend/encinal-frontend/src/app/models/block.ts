import { Status } from './status';

export class Block {

    blockId: number;
    blockNumber: string;
    capacity: number;
    remaining: number;
    createdAt: Date;

    status: Status;
}
