package org.perficient.registrationsystem.controllers;

import org.perficient.registrationsystem.dto.UserDto;
import org.perficient.registrationsystem.services.UserService;
import org.perficient.registrationsystem.services.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.persistence.PersistenceException;
import javax.validation.Valid;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class UserController Created on 22/09/2022
 *
 * @Author Iván Camilo Rincon Saavedra
 */
@RestController
@RequestMapping(path = "/api/v1/users")
public class UserController {

    private final UserService service;

    public UserController(@Autowired UserService service) {
        this.service = service;
    }

    @GetMapping
    @ResponseBody
    public Set<UserDto> getAllUsers() {
        return service.getUsers();
    }

    @PostMapping
    public ResponseEntity<?> addUser(@Valid @RequestBody UserDto userDto, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = new ArrayList<>();

            result.getFieldErrors()
                    .stream()
                    .map(f->"The field " + f.getField() + f.getDefaultMessage())
                    .forEach(errors::add);
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(service.addUser(userDto), HttpStatus.OK);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleNotFound() {
        Logger.getLogger(SubjectController.class.getName()).log(Level.SEVERE, null, NotFoundException.MESSAGE);
        return new ResponseEntity<>(NotFoundException.MESSAGE, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PersistenceException.class)
    public ResponseEntity<?> handlePersistenceException() {
        Logger.getLogger(SubjectController.class.getName()).log(Level.SEVERE, null, "Something happens while saving the object");
        return new ResponseEntity<>("Something happens while saving the object", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
