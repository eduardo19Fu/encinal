import { Seller } from './seller';
import { Status } from './status';
import { ReceiptDetail } from './receipt-detail';
import { PaymentAgreement } from './payment-agreement';
import { ReceiptType } from './receipt-type';
import { Payment } from './payment';
import { User } from './user';
import { UserAuxiliar } from './user-auxiliar';

export class Receipt {

    receiptId: number;
    receiptNumber: string;
    createdAt: Date;
    total: number;

    seller: Seller;
    status: Status;
    receiptType: ReceiptType;
    paymentAgreement: PaymentAgreement;
    registerBy: UserAuxiliar;
    items: ReceiptDetail[] = [];

    calcularTotal(): number{
        this.total = 0;
        this.items.forEach((item: ReceiptDetail) => {
            this.total += item.subtotal;
        });
        return this.total;
    }
}
