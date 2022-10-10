package xyz.pangosoft.encinalbackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import xyz.pangosoft.encinalbackend.models.Transfer;
import xyz.pangosoft.encinalbackend.services.IClientTerrainService;
import xyz.pangosoft.encinalbackend.services.IStatusService;
import xyz.pangosoft.encinalbackend.services.ITerrainService;
import xyz.pangosoft.encinalbackend.services.ITransferService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"https://condadoelencinal.com", "http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class TransferApiController {

    @Autowired
    private ITransferService transferService;

    @Autowired
    private IStatusService statusService;

    @Autowired
    private ITerrainService terrainService;

    @Autowired
    private IClientTerrainService clientTerrainService;

    @GetMapping("/transfers")
    public List<Transfer> index(){
        return this.transferService.listAllTransfers();
    }

    @GetMapping("/transfers/{id}")
    public ResponseEntity<?> findSingleTransfer(@PathVariable("id") Integer id){

        Map<String, Object> response = new HashMap<>();
        Transfer transfer = null;

        try{
            transfer = this.transferService.singleTransfer(id);
        } catch(DataAccessException e){
            response.put("message", "¡Ha ocurrido un error en la base de datos!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(transfer == null){
            response.put("message", "¡No existe registro asociado a la búsqueda!");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Transfer>(transfer, HttpStatus.OK);
    }
    @Secured(value = {"ROLE_ADMIN", "ROLE_SECRETARIO"})
    @PostMapping("/transfers")
    public ResponseEntity<?> create(@RequestBody Transfer transfer){

        Map<String, Object> response = new HashMap<>();
        Transfer newTransfer = null;

        try{
            newTransfer = this.transferService.save(transfer);
        } catch(DataAccessException e){
            response.put("message", "¡Ha ocurrido un error en la base de datos!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "¡Traspaso realizado con éxito!");
        response.put("trasnfer", newTransfer);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }
}
