package xyz.pangosoft.encinalbackend.tasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import xyz.pangosoft.encinalbackend.models.*;
import xyz.pangosoft.encinalbackend.services.IClientService;
import xyz.pangosoft.encinalbackend.services.IPaymentAgreementService;
import xyz.pangosoft.encinalbackend.services.IStatusService;
import xyz.pangosoft.encinalbackend.services.IUserService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@EnableScheduling
public class FindDefaulter {

    private Date currentDate;

    @Autowired
    private IClientService clientService;

    @Autowired
    private IStatusService statusService;

    @Autowired
    private IPaymentAgreementService paymentAgreementService;

    @Scheduled(cron = "0 0 23 * * ?" /*fixedDelay = TimeConstants.MINUTE*/)
    public void adjustDefaulters(){
        this.currentDate = new Date();

        Status statusAgreement = null;
        Status newStatusAgreement = null;
        Status newStatusClient = null;
        Status newStatusPayment = null;

        Client customer = null;

        Double amount_outstanding = 0.00;

        try{
            statusAgreement = statusService.singleStatusName("Activo", "Payment Agreement");
            newStatusAgreement = statusService.singleStatusName("En Mora", "Payment Agreement");
            newStatusPayment = statusService.singleStatusName("Retrasado", "Payment");
            newStatusClient = statusService.singleStatusName("Moroso", "Client");

            for(PaymentAgreement paymentAgreement : paymentAgreementService.listPaymentAgreements(statusAgreement)){
                for(Payment payment : paymentAgreement.getPayments()){
                    if(payment.getExpireDate().getTime() < this.currentDate.getTime()){
                        paymentAgreement.setStatus(newStatusAgreement);
                        paymentAgreementService.save(paymentAgreement);

                        amount_outstanding = amount_outstanding + payment.getPaymentTotal();
                    }
                }
                customer = new Client();
                customer = paymentAgreement.getSale().getClient();
                customer.setAmountOutstanding(amount_outstanding);
                customer.setStatus(newStatusClient);

                clientService.save(customer);
            }
        } catch(DataAccessException e){
            System.out.println(e.getMessage());
        }

    }

}
