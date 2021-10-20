package xyz.pangosoft.encinalbackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import xyz.pangosoft.encinalbackend.models.Receipt;
import xyz.pangosoft.encinalbackend.services.IReceiptService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"http://localhost:4200", "https://encinal4.web.app"})
@RestController
@RequestMapping("/api")
public class ReceiptApiController {

    @Autowired
    private IReceiptService receiptService;

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

    @PostMapping("/receipts")
    public ResponseEntity<?> create(@RequestBody Receipt receipt, BindingResult result){

        Map<String, Object> response = new HashMap<>();
        Receipt newReceipt = null;

        try{
            newReceipt = this.receiptService.create(receipt);
        } catch(DataAccessException e){
            response.put("message", "¡Error en la Base de Datos!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "¡El pago se ha realizado con éxito!");
        response.put("receipt", newReceipt);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }
}
