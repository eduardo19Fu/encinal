package xyz.pangosoft.encinalbackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import xyz.pangosoft.encinalbackend.models.Client;
import xyz.pangosoft.encinalbackend.models.Status;
import xyz.pangosoft.encinalbackend.services.IClientService;
import xyz.pangosoft.encinalbackend.services.IStatusService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"http://localhost:4200", "https://encinal5-808d5.web.app"})
@RestController
@RequestMapping("/api")
public class ClientApiController {

    @Autowired
    private IClientService clientService;

    @Autowired
    private IStatusService statusService;

    @GetMapping("/clients")
    public List<Client> index() {
        return clientService.listClients();
    }

    @GetMapping("/clients/active")
    public ResponseEntity<?> activeClients(){

        Status status = null;
        Map<String, Object> response = new HashMap<>();

        try{
            status = statusService.singleStatus(6);
        }catch(DataAccessException e){
            response.put("message", "¡Ha ocurddio un error en la Base de Datos!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<List<Client>>(clientService.listActiveClients(status), HttpStatus.OK);
    }

    @Secured(value = {"ROLE_ADMIN"})
    @GetMapping("/clients/{id}")
    public ResponseEntity<?> findClient(@PathVariable("id") Integer id) {

        Map<String, Object> response = new HashMap<>();
        Client client = null;

        try {
            client = clientService.singleClient(id);
        } catch(DataAccessException e) {
            response.put("message", "¡Ha ocurrido un error en la Base de Datos!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(client == null) {
            response.put("message", "¡El cliente con ID: ".concat(id.toString()).concat(", no se encuentra registrado en la Base de Datos!"));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Client>(client, HttpStatus.OK);
    }

    @Secured(value = {"ROLE_ADMIN", "ROLE_SECRETARIO"})
    @GetMapping("/cleintes/receipt/{identification}")
    public ResponseEntity<?> findClientByIdentification(@PathVariable("identification") String id){
        Map<String, Object> response = new HashMap<>();
        Client client = null;

        try {
            // Get the client that find by its identification number
            client = clientService.singleClient(id);
        } catch(DataAccessException e) {
            response.put("message", "¡Ha ocurrido un error en la Base de Datos!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(client == null) {
            response.put("message", "¡El cliente con ID: ".concat(id.toString()).concat(", no se encuentra registrado en la Base de Datos!"));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Client>(client, HttpStatus.OK);
    }

    @Secured(value = {"ROLE_ADMIN"})
    @PostMapping("/clients")
    public ResponseEntity<?> create(@RequestBody Client client, BindingResult result) {

        Map<String, Object> response = new HashMap<>();
        Client newClient = null;
        Status status = null;

        // ERRORS HANDLER
        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors().stream()
                    .map(err -> "El campo '".concat(err.getField().concat("' ")).concat(err.getDefaultMessage()))
                    .collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            status = statusService.singleStatus(5);
            client.setStatus(status);
            newClient = clientService.save(client);
        } catch(DataAccessException e) {
            response.put("message", "¡Ha ocurrido un error en la Base de Datos!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(newClient == null) {
            response.put("message", "¡No se ha podido llevar a cabo el registro del cliente!");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        response.put("message", "¡El cliente ha sido registrado con éxito!");
        response.put("client", newClient);  
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @Secured(value = {"ROLE_ADMIN"})
    @PutMapping("/clients")
    public ResponseEntity<?> update(@RequestBody Client client, BindingResult result) {

        Map<String, Object> response = new HashMap<>();
        Client clientUpdated = null;

        // ERRORS HANDLER
        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors().stream()
                    .map(err -> "El campo '".concat(err.getField().concat("' ")).concat(err.getDefaultMessage()))
                    .collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            clientUpdated = clientService.save(client);
        } catch(DataAccessException e) {
            response.put("message", "¡Ha ocurrido un error en la Base de Datos!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "¡Cliente actualizado con éxito!");
        response.put("client", clientUpdated);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @Secured(value = {"ROLE_ADMIN", "ROLE_SECRETARIO"})
    @PutMapping("/clients/disable/{id}")
    public ResponseEntity<?> disable(@PathVariable("id") Integer id, @RequestBody Client client){

        Client customerToDisable = null;
        Status status = null;
        Map<String, Object> response = new HashMap<>();

        try{
            status = statusService.singleStatusName("Inactivo", "Client");
            client.setStatus(status);
            customerToDisable = clientService.save(client);
        } catch(DataAccessException e){
            response.put("message", "¡Ha ocurrido un error en la Base de Datos!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "¡Cliente desactivado con éxito!");
        response.put("client", customerToDisable);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @Secured(value = {"ROLE_ADMIN"})
    @DeleteMapping("/clients/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {

        Map<String, Object> response = new HashMap<>();

        try {
            clientService.delete(id);
        } catch(DataAccessException e) {
            response.put("message", "¡Ha ocurrido un error en la Base de Datos!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "¡Cliente eliminado con éxito!");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

}
