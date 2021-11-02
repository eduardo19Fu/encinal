package xyz.pangosoft.encinalbackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.pangosoft.encinalbackend.models.Role;
import xyz.pangosoft.encinalbackend.services.IRoleService;
import xyz.pangosoft.encinalbackend.services.IStatusService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"http://localhost:4200", "https://encinal5.web.app"})
@RestController
@RequestMapping("/api")
public class RoleApiController {

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IStatusService statusService;

    @GetMapping("/roles")
    public List<Role> index(){

        List<Role> roles = new ArrayList<>();

        for(Role role : this.roleService.listAll()){
            if(role.getRoleId() != 2){
                roles.add(role);
            }
        }
        return roles;
    }

    @GetMapping("/roles/{id}")
    public ResponseEntity<?> findRole(@PathVariable("id") Integer id){

        Map<String, Object> response = new HashMap<>();
        Role role = null;

        try{
            role = roleService.singleRole(id);
        } catch(DataAccessException e){
            response.put("message", "¡Ha ocurrido un error en la Base de Datos!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(role == null){
            response.put("message", "El role solicitado no existe en la Base de Datos");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Role>(role, HttpStatus.OK);
    }
}
