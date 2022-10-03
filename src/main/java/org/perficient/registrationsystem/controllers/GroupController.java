package org.perficient.registrationsystem.controllers;

import org.perficient.registrationsystem.dto.GroupDto;
import org.perficient.registrationsystem.dto.server.ServerErrorResponseDto;
import org.perficient.registrationsystem.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class GroupController Created on 29/09/2022
 *
 * @Author Iván Camilo Rincon Saavedra
 */
@RestController
@RequestMapping(path = "/api/v1/groups")
public class GroupController {
    private final GroupService groupService;

    public GroupController(@Autowired GroupService groupService) {
        this.groupService = groupService;
    }

    //GET

    @GetMapping
    @ResponseBody
    public Set<GroupDto> getAllGroups() throws SQLException {
        return groupService.getAllGroups();
    }

    @GetMapping(path = "/{id}")
    @ResponseBody
    public GroupDto getGroupById(@PathVariable Integer id) throws SQLException {
        return groupService.findGroupById(id);
    }

    //POST

    @PostMapping
    @ResponseBody
    public ResponseEntity<?> addGroup(@Valid @RequestBody GroupDto groupDto, BindingResult result) throws SQLException {
        if (result.hasErrors()) {
            List<String> errors = new ArrayList<>();

            result.getFieldErrors()
                    .stream()
                    .map(f -> "The field " + f.getField() + " " + f.getDefaultMessage())
                    .forEach(errors::add);
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(groupService.addGroup(groupDto), HttpStatus.OK);
    }

    //PUT

    @PutMapping(path = "/{id}")
    public ResponseEntity<?> updateGroupById(@PathVariable Integer id,
                                             @Valid @RequestBody GroupDto groupDto, BindingResult result) throws Exception {
        return new ResponseEntity<>(groupService.updateGroupById(id, groupDto), HttpStatus.OK);

    }



    //HANDLER EXCEPTION
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(SQLException.class)
    public ServerErrorResponseDto defaultHandlerException(Exception e) {

        ServerErrorResponseDto response = new ServerErrorResponseDto(e.getMessage(), e.getCause(), HttpStatus.BAD_REQUEST.value());
        Logger.getLogger(SubjectController.class.getName()).log(Level.SEVERE, null, e.getMessage());
        return response;
    }
}
