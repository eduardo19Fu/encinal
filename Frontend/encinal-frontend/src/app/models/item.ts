import { Range } from './range';

export class Item {

    itemId: number;
    itemName: string;
    itemValue: number;
    createdAt: Date;
    createBy: string;

    range: Range;
}
