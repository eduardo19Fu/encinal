package xyz.pangosoft.encinalbackend.controllers;

import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import xyz.pangosoft.encinalbackend.models.*;
import xyz.pangosoft.encinalbackend.services.*;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.*;

@CrossOrigin(origins = {"http://localhost:4200", "https://condadoelencinal.com"})
@RestController
@RequestMapping("/api")
public class ReceiptApiController {

    @Autowired
    private IReceiptService receiptService;

    @Autowired
    private IReceiptTypeService receiptTypeService;

    @Autowired
    private IStatusService statusService;

    @Autowired
    private IPaymentService paymentService;

    @Autowired
    private IPaymentAgreementService paymentAgreementService;

    @GetMapping("/receipts")
    public List<Receipt> index(){
        return this.receiptService.listAll();
    }

    @GetMapping("/receipts/{id}")
    public ResponseEntity<?> findReceipt(@PathVariable("id") Integer id){

        Map<String, Object> response = new HashMap<>();
        Receipt receipt = null;

        try{
            receipt = receiptService.singleReceipt(id);
        } catch(DataAccessException e){
            response.put("message", "¡Ha ocurrido un error en la Base de Datos!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(receipt == null){
            response.put("message", "El recibo de pago no se encuentra registrado en la Base de Datos!");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Receipt>(receipt, HttpStatus.OK);
    }

    @Secured(value = {"ROLE_ADMIN", "ROLE_SECRETARIO"})
    @PostMapping("/receipts")
    public ResponseEntity<?> create(@RequestBody Receipt receipt, BindingResult result){

        Map<String, Object> response = new HashMap<>();
        PaymentAgreement paymentAgreementUpdated = null;
        Receipt newReceipt = null;
        Status receiptStatus = null;
        Status paymentStatus = null;
        Status oldPaymentStatus = null;
        Status newPaymentStatus = null;

        int cuotas = 0;
        Double remainingPrincipal = 0.00;
        Double pmt = 0.00;
        Double pv = 0.00;
        Double fee = 0.00;


        try{
            receiptStatus = statusService.singleStatusName("Pagado", "Receipt");
            receipt.setStatus(receiptStatus);
            newReceipt = this.receiptService.create(receipt);

            if(receipt.getReceiptType().getReceiptTypeId() == 1){
                paymentStatus = statusService.singleStatusName("Pagado", "Payment");

                // Validate that the new Receipt was successfully saved
                if(newReceipt != null){
                    for(ReceiptDetail item : newReceipt.getItems()){
                        Payment payment = item.getPayment();
                        payment.setStatus(paymentStatus);
                        this.paymentService.save(payment);
                    }
                }
            } else if(receipt.getReceiptType().getReceiptTypeId() == 2){
                oldPaymentStatus = this.statusService.singleStatusName("Abonado", "Payment");

                List<Payment> oldPayments = receipt.getPaymentAgreement().getPayments();
                List<Payment> newPayments = new ArrayList<>();

                remainingPrincipal = this.paymentAgreementService.getPrincipalTotal(receipt.getPaymentAgreement().getPaymentAgreementId());

                pv = remainingPrincipal - receipt.getTotal();
                pmt = this.paymentAgreementService.getFee(receipt.getPaymentAgreement().getPaymentAgreementId());

                cuotas = pv.intValue() / pmt.intValue();

                for(Payment payment : oldPayments){
                    if(!payment.getStatus().getStatus().equals("Pagado") || !payment.getStatus().getStatus().equals("Anulado")){
                        payment.setStatus(oldPaymentStatus);
                        this.paymentService.save(payment);
                    }
                }

                for(int i = 1; i <= cuotas; i++){
                    Payment payment = new Payment();

                    newPaymentStatus = this.statusService.singleStatusName("Activo", "Payment");
                    payment.setPaymentNumber(i);
                    payment.setInterestRateGenerated(pv * ((receipt.getPaymentAgreement().getInterestRate() / 12) / 100));
                    payment.setPrincipalValue(pmt - payment.getInterestRateGenerated());
                    payment.setPaymentTotal(pmt);
                    payment.setStatus(newPaymentStatus);

                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(new Date());
                    calendar.add(Calendar.MONTH, i);
                    Date paymentDate = calendar.getTime();

                    payment.setExpireDate(paymentDate);
                    pv = pv - payment.getPrincipalValue();
                    if(pv < 0){
                        payment.setRemainingBalance(0.00);
                    } else{
                        payment.setRemainingBalance(pv);
                    }

                    newPayments.add(payment);
                }

                paymentAgreementUpdated = receipt.getPaymentAgreement();
                paymentAgreementUpdated.setPayments(newPayments);

                this.paymentAgreementService.save(paymentAgreementUpdated);
            }

        } catch(DataAccessException e){
            response.put("message", "¡Error en la Base de Datos!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "¡El pago se ha realizado con éxito!");
        response.put("receipt", newReceipt);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @Secured(value = {"ROLE_ADMIN", "ROLE_SECRETARIO"})
    @DeleteMapping("/receipts/cancel/{id}")
    public ResponseEntity<?> cancelReceipt(@PathVariable("id") Integer idreceipt){

        Map<String, Object> response = new HashMap<>();

        Receipt receiptToCancel = null;
        PaymentAgreement paymentAgreement = null;
        Status cancelStatus = null;
        Status paymentStatus = null;

        try{
            cancelStatus = statusService.singleStatusName("Anulado", "Receipt");
            paymentStatus = statusService.singleStatusName("Activo", "Payment");

            receiptToCancel = this.receiptService.singleReceipt(idreceipt);

            receiptToCancel.setStatus(cancelStatus);

            for(ReceiptDetail rdetail : receiptToCancel.getItems()){
                System.out.println(rdetail);
                Payment payment = new Payment();
                payment = rdetail.getPayment();

                payment.setStatus(paymentStatus);

                this.paymentService.save(payment);
            }

            this.receiptService.create(receiptToCancel);

        } catch(DataAccessException e) {
            response.put("message", "¡Error en la Base de Datos!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "Anulación llevada a cabo con éxito");
        response.put("receipt", receiptToCancel);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    /*************** PDF REPORTS CONTROLLERS ********************/

    @GetMapping("/receipts/generate/{id}")
    public void generateReceipt(@PathVariable("id") Integer idreceipt, HttpServletResponse httpServletResponse)
            throws JRException, SQLException, FileNotFoundException {
        try{
            byte[] bytesReceipt = receiptService.rptPrintReceipt(idreceipt);
            ByteArrayOutputStream out = new ByteArrayOutputStream(bytesReceipt.length);
            out.write(bytesReceipt, 0, bytesReceipt.length);

            httpServletResponse.setContentType("application/pdf");
            httpServletResponse.addHeader("Content-Disposition", "inline; filename=receipt-"+idreceipt+".pdf");

            OutputStream os;

            os = httpServletResponse.getOutputStream();
            out.writeTo(os);
            os.flush();
            os.close();
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    @Secured(value = {"ROLE_ADMIN", "ROLE_SECRETARIO"})
    @GetMapping("/receipts/receipt-types")
    public List<ReceiptType> findTypes(){
        return this.receiptTypeService.listAll();
    }
}
