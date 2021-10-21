package xyz.pangosoft.encinalbackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import xyz.pangosoft.encinalbackend.models.Status;
import xyz.pangosoft.encinalbackend.models.Terrain;
import xyz.pangosoft.encinalbackend.services.IStatusService;
import xyz.pangosoft.encinalbackend.services.ITerrainService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"http://localhost:4200", "https://encinal5.web.app"})
@RestController
@RequestMapping("/api")
public class TerrainApiController {

    @Autowired
    private ITerrainService terrainService;

    @Autowired
    private IStatusService statusService;

    @GetMapping("/terrains")
    public List<Terrain> index(){
        return this.terrainService.listTerrains();
    }

    @Secured(value = {"ROLE_ADMIN"})
    @GetMapping("/terrains/on-sale")
    public ResponseEntity<?> listOnSale(){

        Map<String, Object> response = new HashMap<>();
        Status status = null;

        try{
            status = statusService.singleStatus(10);
        }
        catch(DataAccessException e){
            response.put("message", "¡Ha ocurrido un error en la Base de Datos!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<List<Terrain>>(this.terrainService.listTerrainsOnSale(status), HttpStatus.OK);
    }

    @Secured(value = {"ROLE_ADMIN"})
    @GetMapping("/terrains/{id}")
    public ResponseEntity<?> findTerrain(@PathVariable("id") Integer id){

        Map<String, Object> response = new HashMap<>();
        Terrain terrain = null;

        try{
            terrain = this.terrainService.singleTerrain(id);
        }catch (DataAccessException e){
            response.put("message", "¡Ha ocurrido un error en la Base de Datos!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(terrain == null){
            response.put("message", "¡El Lote con ID: ".concat(id.toString()).concat(", no se encuentra registrado en la Base de Datos!"));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Terrain>(terrain, HttpStatus.OK);
    }

    @Secured(value = {"ROLE_ADMIN"})
    @PostMapping("/terrains")
    public ResponseEntity<?> create(@RequestBody Terrain terrain, BindingResult result){

        Map<String, Object> response = new HashMap<>();
        Terrain newTerrain = null;
        Status status = null;

        if(result.hasErrors()){
            List<String> errors = result.getFieldErrors().stream()
                    .map(err -> "El campo '".concat(err.getField().concat("' ")).concat(err.getDefaultMessage()))
                    .collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        try{
            status = this.statusService.singleStatus(10);
            terrain.setStatus(status);
            newTerrain = this.terrainService.save(terrain);
        } catch(DataAccessException e){
            response.put("message", "¡Ha ocurrido un error en la Base de Datos!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(newTerrain == null){
            response.put("message", "¡El Lote no se pudo guardar en la Base de Datos!");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        response.put("message", "¡El Lote fué guardado con éxito en la Base de Datos!");
        response.put("terrain", newTerrain);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @Secured(value = {"ROLE_ADMIN"})
    @PutMapping("/terrains")
    public ResponseEntity<?> update(@RequestBody Terrain terrain, BindingResult result){

        Map<String, Object> response = new HashMap<>();
        Terrain terrainUpdated = null;

        if(result.hasErrors()){
            List<String> errors = result.getFieldErrors().stream()
                    .map(err -> "El campo '".concat(err.getField().concat("' ")).concat(err.getDefaultMessage()))
                    .collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        try{
            terrainUpdated = this.terrainService.save(terrain);
        } catch(DataAccessException e){
            response.put("message", "¡Ha ocurrido un error en la Base de Datos!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "¡Lote ha sido actualizado con éxito!");
        response.put("terrain", terrainUpdated);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }
}
