package xyz.pangosoft.encinalbackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import xyz.pangosoft.encinalbackend.models.Item;
import xyz.pangosoft.encinalbackend.services.IItemService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"http://localhost:4200", "https://encinal5-808d5.web.app", "https://condadoelencinal.com"})
@RestController
@RequestMapping("/api")
public class ItemApiController {

    @Autowired
    private IItemService itemService;

    @Secured(value = {"ROLE_ADMIN", "ROLE_SUPERADMIN"})
    @GetMapping("/items")
    public List<Item> index(){
        return this.itemService.listAll();
    }

    @Secured(value = {"ROLE_ADMIN", "ROLE_SUPERADMIN"})
    @GetMapping("/items/page/{page}")
    public Page<Item> indexPaginate(@PathVariable("page") Integer page){
        return this.itemService.listAllPage(PageRequest.of(page, 5));
    }

    @Secured(value = {"ROLE_ADMIN", "ROLE_SUPERADMIN"})
    @GetMapping("/items/{id}")
    public ResponseEntity<?> findItem(@PathVariable("id") Integer id){

        Map<String, Object> response = new HashMap<>();
        Item item = null;

        try{
            item = this.itemService.singleItem(id);
        } catch(DataAccessException e){
            response.put("message", "¡Ha ocurrido un error en la Base de Datos!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(item == null){
            response.put("message", "¡El item no se encuentra registrado en la Base de Datos!");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Item>(item, HttpStatus.OK);
    }

    @Secured(value = {"ROLE_SUPERADMIN"})
    @PostMapping("/items")
    public ResponseEntity<?> create(@RequestBody Item item, BindingResult result){

        Map<String, Object> response = new HashMap<>();
        Item newItem = null;

        if(result.hasErrors()){
            List<String> errors = result.getFieldErrors().stream()
                    .map(err -> "El campo '".concat(err.getField().concat("' ").concat(err.getDefaultMessage())))
                    .collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        try{
            newItem = this.itemService.save(item);
        } catch(DataAccessException e){
            response.put("message", "¡Ha ocurrido un error en la Base de Datos!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "¡El Item ha sido registrado con éxito!");
        response.put("item", newItem);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @Secured(value = {"ROLE_ADMIN", "ROLE_SUPERADMIN", "ROLE_SECRETARIO"})
    @PutMapping("/items")
    public ResponseEntity<?> update(@RequestBody Item item, BindingResult result){

        Map<String, Object> response = new HashMap<>();
        Item itemUpdated = null;

        if(result.hasErrors()){
            List<String> errors = result.getFieldErrors().stream()
                    .map(err -> "El campo '".concat(err.getField().concat("' ").concat(err.getDefaultMessage())))
                    .collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        try{
            itemUpdated = this.itemService.save(item);
        } catch(DataAccessException e){
            response.put("message", "¡Ha ocurrido un error en la Base de Datos!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "¡El Item ha sido actualizado con éxito!");
        response.put("item", itemUpdated);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }
}
