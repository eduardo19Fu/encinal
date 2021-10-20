package xyz.pangosoft.encinalbackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import xyz.pangosoft.encinalbackend.models.SaleType;
import xyz.pangosoft.encinalbackend.services.ISaleTypeService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"http://localhost:4200", "https://encinal4.web.app"})
@RestController
@RequestMapping("/api")
public class SaleTypeApiController {

    @Autowired
    private ISaleTypeService saleTypeService;

    @GetMapping("/sale-types")
    public List<SaleType> index(){
        return this.saleTypeService.listSaleTypes();
    }

    @Secured(value = {"ROLE_ADMIN"})
    @GetMapping("/sale-types/{id}")
    public ResponseEntity<?> findSaleType(@PathVariable("id") Integer id){

        Map<String, Object> response = new HashMap<>();
        SaleType saleType = null;

        try{
            saleType = saleTypeService.singleSaleType(id);
        } catch(DataAccessException e){
            response.put("message", "¡Ha ocurrido un error en la Base de Datos!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(saleType == null){
            response.put("message", "El registro no se encuentra guardado en la Base de Datos.");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<SaleType>(saleType, HttpStatus.OK);
    }
}
