import { Payment } from './payment';
import { Sale } from './sale';
import { Status } from './status';

export class PaymentAgreement {

    paymentAgreementId: number;
    interestRate: number;
    hitch: number;
    totalAgreement: number;
    totalPayments: number;

    sale: Sale;
    status: Status;
    payments: Payment[] = [];
}
