package xyz.pangosoft.encinalbackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import xyz.pangosoft.encinalbackend.models.Role;
import xyz.pangosoft.encinalbackend.models.Status;
import xyz.pangosoft.encinalbackend.models.User;
import xyz.pangosoft.encinalbackend.services.IRoleService;
import xyz.pangosoft.encinalbackend.services.IUserService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"http://localhost:4200", "https://encinal5-808d5.web.app", "https://condadoelencinal.com"})
@RestController
@RequestMapping("/api")
public class UserApiController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/users")
    public List<User> index(){
        return this.userService.findAll();
    }

    @Secured(value = {"ROLE_ADMIN"})
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

    @Secured(value = {"ROLE_ADMIN"})
    @PostMapping("/users")
    public ResponseEntity<?> create(@RequestBody User user){

        Map<String, Object> response = new HashMap<>();

        User newUser = null;
        List<Role> roles = new ArrayList<>();

        try{
            user.setPassword(passwordEncoder.encode(user.getPassword()));
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

    @Secured(value = {"ROLE_ADMIN"})
    @PutMapping("/users/{id}")
    public ResponseEntity<?> update(@RequestBody User user, @PathVariable("id") Integer id){

        Map<String, Object> response = new HashMap<>();
        User currentUser = null;
        User userUpdated = null;

        try{
            currentUser = this.userService.singleUser(id);

            if(currentUser != null){
                currentUser.setUsername(user.getUsername());
                currentUser.setFirstName(user.getFirstName());
                currentUser.setMiddleName(user.getMiddleName());
                currentUser.setLastName(user.getLastName());
                currentUser.setEnabled(user.getEnabled());
                currentUser.setEmail(user.getEmail());
                currentUser.setRoles(user.getRoles());

                if(!currentUser.getPassword().equals(user.getPassword())){
                    currentUser.setPassword(passwordEncoder.encode(user.getPassword()));
                } else{
                    currentUser.setPassword(user.getPassword());
                }

                userUpdated = this.userService.create(currentUser);
            } else{
                response.put("message", "Usuario no se encuentra registrado en la Base de Datos");
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
            }
        } catch(DataAccessException e){
            response.put("message", "¡Ha ocurrido un error en la Base de Datos!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(userUpdated == null){
            response.put("message", "¡El usuario no pudo ser actualizado!");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_GATEWAY);
        }

        response.put("message", "¡El usuario" + userUpdated.getUsername() +" ha sido actualizado con éxito!");
        response.put("user", userUpdated);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }
}
