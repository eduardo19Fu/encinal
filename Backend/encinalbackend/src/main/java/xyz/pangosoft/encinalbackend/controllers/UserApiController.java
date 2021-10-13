package xyz.pangosoft.encinalbackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.pangosoft.encinalbackend.models.Status;
import xyz.pangosoft.encinalbackend.models.User;
import xyz.pangosoft.encinalbackend.services.IUserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"http://localhost:4200", "*"})
@RestController
@RequestMapping("/api")
public class UserApiController {

    @Autowired
    private IUserService userService;

    @GetMapping("/users")
    public List<User> index(){
        return this.userService.findAll();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> findUser(@PathVariable("id") Integer id){

        Map<String, Object> response = new HashMap<>();
        User user = null;

        try{
            user = this.userService.singleUser(id);
        } catch(DataAccessException e){
            response.put("message", "¡Ha ocurrido un error en la Base de Datos!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(user == null){
            response.put("message", "¡El usuario no se encuentra registrado en la Base de Datos!");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @PostMapping("/users")
    public ResponseEntity<?> create(@RequestBody User user){

        Map<String, Object> response = new HashMap<>();
        User newUser = null;

        try{
            newUser = this.userService.create(user);
        } catch(DataAccessException e){
            response.put("message", "¡Ha ocurrido un error en la Base de Datos!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "¡El usuario ha sido creado con éxito!");
        response.put("user", newUser);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @PutMapping("/users")
    public ResponseEntity<?> update(@RequestBody User user){

        Map<String, Object> response = new HashMap<>();
        User userUpdated = null;

        try{
            userUpdated = this.userService.create(user);
        } catch(DataAccessException e){
            response.put("message", "¡Ha ocurrido un error en la Base de Datos!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(userUpdated == null){
            response.put("message", "¡El usuario no pudo ser actualizado!");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_GATEWAY);
        }

        response.put("message", "¡El usuario ha sido actualizado con éxito!");
        response.put("user", userUpdated);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }
}
