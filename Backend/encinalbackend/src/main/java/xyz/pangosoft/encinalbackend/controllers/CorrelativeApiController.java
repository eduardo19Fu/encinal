package xyz.pangosoft.encinalbackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import xyz.pangosoft.encinalbackend.models.Correlative;
import xyz.pangosoft.encinalbackend.models.Status;
import xyz.pangosoft.encinalbackend.services.ICorrelativeService;
import xyz.pangosoft.encinalbackend.services.IStatusService;
import xyz.pangosoft.encinalbackend.services.IUserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"http://localhost:4200", "https://condadoelencinal.com"})
@RestController
@RequestMapping("/api")
public class CorrelativeApiController {

    @Autowired
    private ICorrelativeService correlativeService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IStatusService statusService;

    @GetMapping("/correlatives")
    public List<Correlative> index(){
        return this.correlativeService.listAll();
    }

    @GetMapping("/correlatives/{id}")
    public ResponseEntity<?> findCorrelative(@PathVariable("id") Integer id){

        Correlative correlative = null;
        Map<String, Object> response = new HashMap<>();

        try{
            correlative = this.correlativeService.singleCorrelative(id);
        } catch (DataAccessException e){
            response.put("message", "¡Ha ocurrido un error en la Base de Datos!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(correlative == null){
            response.put("message", "El correlativo buscado no existe en la Base de Datos");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Correlative>(correlative, HttpStatus.OK);
    }

    @Secured(value = {"ROLE_ADMIN", "ROLE_SECRETARIO"})
    @PostMapping("/correlatives")
    public ResponseEntity<?> create(@RequestBody Correlative correlative){

        Correlative newCorrelative = null;
        Status correlativeStatus = null;
        Map<String, Object> response = new HashMap<>();

        try{
            correlativeStatus = this.statusService.singleStatusName("Activo", "Correlative");
            correlative.setStatus(correlativeStatus);
            newCorrelative = this.correlativeService.save(correlative);
        } catch(DataAccessException e){
            response.put("message", "¡Ha ocurrido un error en la Base de Datos!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(newCorrelative == null){
            response.put("message", "¡No se ha podido llevar a cabo el registro del correlativo deseado!");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        response.put("message", "¡Correlativo registrado con éxito!");
        response.put("correlative", newCorrelative);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    public ResponseEntity<?> cancel(){
        return null;
    }
}
