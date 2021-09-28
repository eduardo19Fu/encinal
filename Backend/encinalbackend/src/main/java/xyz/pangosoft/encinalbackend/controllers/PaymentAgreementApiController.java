package xyz.pangosoft.encinalbackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import xyz.pangosoft.encinalbackend.models.PaymentAgreement;
import xyz.pangosoft.encinalbackend.models.Status;
import xyz.pangosoft.encinalbackend.models.Terrain;
import xyz.pangosoft.encinalbackend.services.IPaymentAgreementService;
import xyz.pangosoft.encinalbackend.services.IStatusService;
import xyz.pangosoft.encinalbackend.services.ITerrainService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"http://localhost:4200", "*"})
@RestController
@RequestMapping("/api")
public class PaymentAgreementApiController {

    @Autowired
    private IPaymentAgreementService paymentAgreementService;

    @Autowired
    private IStatusService statusService;

    @Autowired
    private ITerrainService terrainService;

    @GetMapping("/payment-agreements")
    public List<PaymentAgreement> index(){
        return this.paymentAgreementService.listPaymentAgreements();
    }

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
}
