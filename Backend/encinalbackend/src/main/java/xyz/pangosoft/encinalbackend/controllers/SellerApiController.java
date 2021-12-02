package xyz.pangosoft.encinalbackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import xyz.pangosoft.encinalbackend.models.Seller;
import xyz.pangosoft.encinalbackend.models.Status;
import xyz.pangosoft.encinalbackend.services.ISellerService;
import xyz.pangosoft.encinalbackend.services.IStatusService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"http://localhost:4200", "https://encinal5-808d5.web.app"})
@RestController
@RequestMapping("/api")
public class SellerApiController {

    @Autowired
    private ISellerService sellerService;

    @Autowired
    private IStatusService statusService;

    @GetMapping("/sellers")
    public List<Seller> index() {
        return sellerService.listSellers();
    }

    @Secured(value = {"ROLE_ADMIN"})
    @GetMapping("/sellers/{id}")
    public ResponseEntity<?> findSeller(@PathVariable("id") Integer id) {

        Map<String, Object> response = new HashMap<>();
        Seller seller = null;

        try {
            seller = sellerService.singleSeller(id);
        } catch(DataAccessException e) {
            response.put("message", "¡Ha ocurrido un error en la Base de Datos!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(seller == null) {
            response.put("message", "¡Vendedor con ID: ".concat(id.toString()).concat(", no se encuentra registrado en la Base de Datos!"));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Seller>(seller, HttpStatus.OK);
    }

    @Secured(value = {"ROLE_ADMIN"})
    @PostMapping("/sellers")
    public ResponseEntity<?> create(@RequestBody Seller seller, BindingResult result) {

        Map<String, Object> response = new HashMap<>();
        Seller newSeller = null;
        Status status = null;

        if(result.hasErrors()) {
            List<String> errors = result.getFieldErrors().stream()
                    .map(err -> "El campo '".concat(err.getField().concat("' ")).concat(err.getDefaultMessage()))
                    .collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            status = this.statusService.singleStatus(1);
            seller.setStatus(status);
            seller.setSalesQuantity(0);
            newSeller = this.sellerService.save(seller);
        } catch(DataAccessException e) {
            response.put("message", "¡Ha ocurrido un error en la Base de Datos!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(newSeller == null) {
            response.put("messgae", "¡No se pudo crear el vendedor!");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        response.put("message", "¡Vendedor registrado con éxito!");
        response.put("seller", newSeller);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @Secured(value = {"ROLE_ADMIN"})
    @PutMapping("/sellers")
    public ResponseEntity<?> update(@RequestBody Seller seller, BindingResult result) {

        Map<String, Object> response = new HashMap<>();
        Seller sellerUpdated = null;

        if(result.hasErrors()) {
            List<String> errors = result.getFieldErrors().stream()
                    .map(err -> "El campo '".concat(err.getField().concat("' ")).concat(err.getDefaultMessage()))
                    .collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            sellerUpdated = sellerService.save(seller);
        } catch(DataAccessException e) {
            response.put("message", "¡Ha ocurrido un error en la Base de Datos!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(sellerUpdated == null) {
            response.put("messgae", "¡No se pudo actualizar el vendedor!");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        response.put("message", "¡Vendedor actualizado con éxito!");
        response.put("seller", sellerUpdated);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @Secured(value = {"ROLE_ADMIN"})
    @DeleteMapping("/sellers/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {

        Map<String, Object> response = new HashMap<>();

        try {
            sellerService.delete(id);
        } catch(DataAccessException e) {
            response.put("message", "¡Ha ocurrido un error en la Base de Datos!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "¡Vendedor eliminado con éxito!");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }
    
}
