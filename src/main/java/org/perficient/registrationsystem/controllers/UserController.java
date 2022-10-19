package org.perficient.registrationsystem.controllers;

import lombok.extern.slf4j.Slf4j;
import org.perficient.registrationsystem.dto.UserDto;
import org.perficient.registrationsystem.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

/**
 * Class UserController Created on 18/10/2022
 *
 * @Author Iván Camilo Rincon Saavedra
 */
@Slf4j
@RestController
@RequestMapping(path = "/api/v1/users")
public class UserController {

    private final UserService service;


    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Set<UserDto> getAllUsers() throws Exception {
        return service.getAllUsers();
    }

    @GetMapping("/id/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public UserDto getUserById(@PathVariable int id) throws Exception {
        return service.getUserById(id);
    }

    //GET
    @GetMapping("/{email}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public UserDto getUserByEmail(@PathVariable String email) throws Exception {
        return service.getUserByEmail(email);
    }

    //POST
    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto addUser(@Valid @RequestBody UserDto userDto) throws Exception {
        return service.addUser(userDto);
    }

    //UPDATE
    @PutMapping("/{email}")
    @ResponseBody
    @ResponseStatus(HttpStatus.ACCEPTED)
    public UserDto updateUserByEmail(@PathVariable String email, @Valid @RequestBody UserDto userDto) throws Exception {
        return service.updateUserByEmail(email, userDto);
    }

    //DELETE
    @DeleteMapping("/{email}")
    @ResponseBody
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Boolean deleteUserByEmail(@PathVariable String email) throws Exception {
        return service.deleteByEmail(email);
    }
}
