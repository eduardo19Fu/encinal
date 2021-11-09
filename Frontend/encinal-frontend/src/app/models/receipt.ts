import { Seller } from './seller';
import { Status } from './status';
import { ReceiptDetail } from './receipt-detail';
import { PaymentAgreement } from './payment-agreement';

export class Receipt {

    receiptId: number;
    receiptNumber: string;
    createdAt: Date;

    seller: Seller;
    status: Status;
    paymentAgreement: PaymentAgreement;
}
