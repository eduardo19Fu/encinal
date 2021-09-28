import { Status } from './status';

export class Payment {

    paymentId: number;
    paymentNumber: number;
    principalValue: number;
    interestRateGenerated: number;
    expireDate: Date;
    remainingBalance: number;
    paymentTotal: number;

    status: Status;
}
