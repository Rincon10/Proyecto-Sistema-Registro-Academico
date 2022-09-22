package org.perficient.registrationsystem.controllers;

import org.perficient.registrationsystem.dto.UserDto;
import org.perficient.registrationsystem.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class UserController Created on 22/09/2022
 *
 * @Author Iván Camilo Rincon Saavedra
 */
@Repository
@RequestMapping(path = "/api/v1")
public class UserController {

    private final UserService service;

    public UserController(@Autowired UserService service) {
        this.service = service;
    }

    @RequestMapping(path = "/users")
    public ResponseEntity<?> getAllUsers() {
        try {
            return new ResponseEntity<>(service.getUsers(), HttpStatus.OK);
        } catch (Exception ex) {
            Logger.getLogger(SubjectController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(path = "users")
    public ResponseEntity<?> getAddUser(@RequestBody UserDto userDto) {
        try {
            return new ResponseEntity<>(service.addUser(userDto), HttpStatus.OK);
        } catch (Exception ex) {
            Logger.getLogger(SubjectController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
