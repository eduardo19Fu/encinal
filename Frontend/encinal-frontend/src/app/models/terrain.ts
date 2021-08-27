import { Status } from './status';
import { Block } from './block';

export class Terrain {

    terrainId: number;
    terrainNumber: string;
    price: number;
    height: number;
    weight: number;
    area: number;

    status: Status;
    block: Block;
}
