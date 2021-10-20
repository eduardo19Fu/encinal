package xyz.pangosoft.encinalbackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;   

import xyz.pangosoft.encinalbackend.models.Block;
import xyz.pangosoft.encinalbackend.models.Status;
import xyz.pangosoft.encinalbackend.services.IBlockService;
import xyz.pangosoft.encinalbackend.services.IStatusService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"http://localhost:4200", "https://encinal4.web.app"})
@RestController
@RequestMapping(value = "/api")
public class BlockApiController {

    @Autowired
    private IBlockService blockService;

    @Autowired
    private IStatusService statusService;

    @GetMapping("/blocks")
    public List<Block> index(){
        return this.blockService.listAll();
    }

    @Secured(value = {"ROLE_ADMIN"})
    @GetMapping("/blocks/{id}")
    public ResponseEntity<?> blockById(@PathVariable("id") Integer id){

        Block block = null;
        Map<String, Object> response = new HashMap<>();

        try {
            block = blockService.singleBlock(id);
        }catch(DataAccessException e){
            response.put("message", "¡Ha ocurrido un error en la Base de Datos!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(block == null){
            response.put("message", "¡La manzana no se encuentra registrada en la Base de Datos!");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Block>(block, HttpStatus.OK);
    }

    @Secured(value = {"ROLE_ADMIN"})
    @PostMapping("/blocks")
    public ResponseEntity<?> create(@RequestBody Block block, BindingResult result) {

        Block newBlock = null;
        Status status = null;
        Map<String, Object> response = new HashMap<>();

        // ERRORS HANDLER
        if(result.hasErrors()) {
            List<String> errors = result.getFieldErrors().stream()
                    .map(err -> "El campo '".concat(err.getField().concat("' ")).concat(err.getDefaultMessage()))
                    .collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            status = statusService.singleStatus(8);
            block.setStatus(status);
            newBlock = blockService.save(block);
        } catch(DataAccessException e) {
            response.put("message", "¡Ha ocurrido un error en la Base de Datos!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(newBlock == null) {
            response.put("message", "¡No se pudo llevar a cabo el registro deseado!");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "¡Manzana registrada con éxito!");
        response.put("block", newBlock);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @Secured(value = {"ROLE_ADMIN"})
    @PutMapping("/blocks")
    public ResponseEntity<?> update(@RequestBody Block block, BindingResult result){

        Block blockUpdated = null;
        Map<String, Object> response = new HashMap<>();

        // ERRORS HANDLER
        if(result.hasErrors()) {
            List<String> errors = result.getFieldErrors().stream()
                    .map(err -> "El campo '".concat(err.getField().concat("' ")).concat(err.getDefaultMessage()))
                    .collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            blockUpdated = blockService.save(block);
        } catch(DataAccessException e) {
            response.put("message", "¡Ha ocurrido un error en la Base de Datos!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(blockUpdated == null) {
            response.put("message", "¡No se ha podido actualizar el registro deseado!");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        response.put("message", "¡El registro se ha actualizado con éxito!");
        response.put("block", blockUpdated);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @Secured(value = {"ROLE_ADMIN"})
    @DeleteMapping("/blocks/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {

        Map<String, Object> response = new HashMap<>();

        try {
            blockService.delete(id);
        } catch(DataAccessException e) {
            response.put("message", "¡Ha ocurrido un error en la Base de Datos!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "¡Manzana eliminada con éxito!");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }
}
