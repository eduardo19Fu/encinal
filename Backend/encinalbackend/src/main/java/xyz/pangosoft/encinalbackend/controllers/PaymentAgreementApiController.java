package xyz.pangosoft.encinalbackend.controllers;

import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import xyz.pangosoft.encinalbackend.models.Client;
import xyz.pangosoft.encinalbackend.models.PaymentAgreement;
import xyz.pangosoft.encinalbackend.models.Status;
import xyz.pangosoft.encinalbackend.models.Terrain;
import xyz.pangosoft.encinalbackend.services.IClientService;
import xyz.pangosoft.encinalbackend.services.IPaymentAgreementService;
import xyz.pangosoft.encinalbackend.services.IStatusService;
import xyz.pangosoft.encinalbackend.services.ITerrainService;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"http://localhost:4200", "https://encinal5-808d5.web.app", "https://condadoelencinal.com"})
@RestController
@RequestMapping("/api")
public class PaymentAgreementApiController {

    @Autowired
    private IPaymentAgreementService paymentAgreementService;

    @Autowired
    private IStatusService statusService;

    @Autowired
    private ITerrainService terrainService;

    @Autowired
    private IClientService clientService;

    @GetMapping("/payment-agreements")
    public List<PaymentAgreement> index(){
        return this.paymentAgreementService.listPaymentAgreements();
    }

    @Secured(value = {"ROLE_ADMIN", "ROLE_SECRETARIO"})
    @GetMapping("/payment-agreements/client/{id}")
    public List<PaymentAgreement> findByClient(@PathVariable("id") Integer id){
        return this.paymentAgreementService.listPaymentAgreementsByClient(id);
    }

    @Secured(value = {"ROLE_ADMIN", "ROLE_SECRETARIO"})
    @GetMapping("/payment-agreements/client-two/{id}")
    public ResponseEntity<?> findByClient2(@PathVariable("id") Integer id){

        Client client = null;
        Status status = null;
        Map<String, Object> response = new HashMap<>();
        List<PaymentAgreement> paymentAgreements = new ArrayList<>();

        try{
            client = this.clientService.singleClient(id);
            status = this.statusService.singleStatusName("Activo", "Payment Agreement");
            paymentAgreements = this.paymentAgreementService.listPaymentAgreementsByClient(client, status);
        } catch(DataAccessException e){
            response.put("message", "¡Ha ocurrido un error en la Base de Datos!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(paymentAgreements.size() <= 0){
            response.put("message","¡No existen acuerdos de pago relacionados al cliente elegido!");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<List<PaymentAgreement>>(paymentAgreements, HttpStatus.OK);
    }

    @Secured(value = {"ROLE_ADMIN", "ROLE_SECRETARIO"})
    @GetMapping("/payment-agreement/{id}")
    public ResponseEntity<?> findPaymentAgreement(@PathVariable("id") Integer id){

        Map<String, Object> response = new HashMap<>();
        PaymentAgreement paymentAgreement = null;

        try{
            paymentAgreement = paymentAgreementService.singlePaymentAgreement(id);
        } catch(DataAccessException e){
            response.put("message", "¡Ha ocurrido un error en la Base de Datos!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(paymentAgreement == null){
            response.put("message", "¡El acuerdo de pagos no se encuentra registrado en la Base de Datos!");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<PaymentAgreement>(paymentAgreement, HttpStatus.OK);
    }

    @Secured(value = {"ROLE_ADMIN", "ROLE_SECRETARIO"})
    @PostMapping("/payment-agreements")
    public ResponseEntity<?> create(@RequestBody PaymentAgreement paymentAgreement, BindingResult result){

        Map<String, Object> response = new HashMap<>();
        PaymentAgreement newPaymentAgreement = null;
        Status statusAgreement = null;
        Status statusTerrain = null;
        Status statusPayment = null;
        Terrain terrain = null;

        try{
            statusAgreement = statusService.singleStatus(18);
            statusTerrain = statusService.singleStatus(12);
            statusPayment = statusService.singleStatus(22);

            terrain = paymentAgreement.getSale().getTerrain();
            terrain.setStatus(statusTerrain);
            terrainService.save(terrain);

            for(int i = 0; i < paymentAgreement.getPayments().size(); i++){
                paymentAgreement.getPayments().get(i).setStatus(statusPayment);
            }

            paymentAgreement.setStatus(statusAgreement);
            newPaymentAgreement = paymentAgreementService.save(paymentAgreement);
        } catch(DataAccessException e){
            response.put("message", "¡Ha ocurrido un error en la Base de Datos!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "¡El acuerdo de pagos ha sido emitido con éxito!");
        response.put("paymentAgreement", newPaymentAgreement);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    /*************** PDF REPORTS CONTROLLERS ********************/

    @GetMapping("/payment-agreements/generate/{id}")
    public void generatePaymentAgreement(@PathVariable("id") Integer idagreement, HttpServletResponse httpServletResponse)
            throws JRException, SQLException, FileNotFoundException {

        try{
            byte[] bytesPaymentAgreement = paymentAgreementService.rptPaymentAgreement(idagreement);
            ByteArrayOutputStream out = new ByteArrayOutputStream(bytesPaymentAgreement.length);
            out.write(bytesPaymentAgreement, 0, bytesPaymentAgreement.length);

            httpServletResponse.setContentType("application/pdf");
            httpServletResponse.addHeader("Content-Disposition", "inline; filename=payment-agreement-"+idagreement+".pdf");

            OutputStream os;

            os = httpServletResponse.getOutputStream();
            out.writeTo(os);
            os.flush();
            os.close();
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
