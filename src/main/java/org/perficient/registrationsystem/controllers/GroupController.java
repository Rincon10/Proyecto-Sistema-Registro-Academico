package org.perficient.registrationsystem.controllers;

import org.perficient.registrationsystem.dto.GroupDto;
import org.perficient.registrationsystem.dto.server.ServerResponseDto;
import org.perficient.registrationsystem.services.GroupService;
import org.perficient.registrationsystem.services.exceptions.ServerErrorException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    private void throwExceptionForInvalidData(BindingResult result) throws ServerErrorException {
        List<String> errors = new ArrayList<>();

        result.getFieldErrors()
                .stream()
                .map(f -> "The field " + f.getField() + " " + f.getDefaultMessage())
                .forEach(errors::add);

        throw new ServerErrorException(errors.toString(), HttpStatus.BAD_REQUEST.value());

    }

    //GET

    @GetMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Set<GroupDto> getAllGroups() throws Exception {
        return groupService.getAllGroups();
    }

    @GetMapping(path = "/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public GroupDto getGroupById(@PathVariable Integer id) throws Exception {
        return groupService.findGroupById(id);
    }

    //POST

    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public Boolean addGroup(@Valid @RequestBody GroupDto groupDto, BindingResult result) throws Exception {
        if (result.hasErrors()) throwExceptionForInvalidData(result);

        return groupService.addGroup(groupDto);
    }

    //PUT

    @PutMapping(path = "/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.ACCEPTED)
    public GroupDto updateGroupById(@PathVariable Integer id,
                                    @Valid @RequestBody GroupDto groupDto, BindingResult result) throws Exception {
        if (result.hasErrors()) throwExceptionForInvalidData(result);

        return groupService.updateGroupById(id, groupDto);

    }

    //DELETE
    @DeleteMapping(path = "/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Boolean deleteById(@PathVariable Integer id) throws Exception {
        return groupService.deleteGroupById(id);
    }

    //HANDLER EXCEPTION

    @ExceptionHandler(ServerErrorException.class)
    public ResponseEntity<?> serverErrorResponseHandlerException(ServerErrorException e) {
        String message = e.getMessage();
        int httpStatus = e.getHttpStatus();

        Logger.getLogger(SubjectController.class.getName()).log(Level.SEVERE, null, message);

        return new ResponseEntity<>(new ServerResponseDto(message, httpStatus), HttpStatus.valueOf(httpStatus));
    }
}
