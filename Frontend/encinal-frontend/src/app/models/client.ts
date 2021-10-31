import { IdentificationType } from './identification-type';
import { Status } from './status';

export class Client {

    clientId: number;
    firstName: string;
    middleName: string;
    lastName: string;
    nit: string;
    id: string;
    telNumber: string;
    email: string;
    address: string;
    birthDate: Date = new Date();
    createdAt: Date;
    amountOutstanding: number;
    underage: boolean;

    status: Status;
    identificationType: IdentificationType;
}
