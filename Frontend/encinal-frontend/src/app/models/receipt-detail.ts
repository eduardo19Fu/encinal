import { Payment } from './payment';

export class ReceiptDetail {

    receiptDetailId: number;
    subtotal: number;

    payment: Payment;

    public calcularSubTotal(): number{
        return this.payment.paymentTotal + this.payment.arrears;
    }
}
