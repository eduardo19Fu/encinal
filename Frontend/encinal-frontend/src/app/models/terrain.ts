import { Status } from './status';
import { Block } from './block';

export class Terrain {

    terrainId: number;
    terrainNumber: string;
    price: number;
    length: number;
    width: number;
    area: number;

    status: Status;
    block: Block;
}
