package org.perficient.registrationsystem.controllers;

import org.perficient.registrationsystem.dto.GroupDto;
import org.perficient.registrationsystem.services.GroupService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

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
    public Boolean addGroup(@Valid @RequestBody GroupDto groupDto) throws Exception {
        return groupService.addGroup(groupDto);
    }

    //PUT

    @PutMapping(path = "/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.ACCEPTED)
    public GroupDto updateGroupById(@PathVariable Integer id, @Valid @RequestBody GroupDto groupDto) throws Exception {
        return groupService.updateGroupById(id, groupDto);
    }

    //DELETE
    @DeleteMapping(path = "/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Boolean deleteById(@PathVariable Integer id) throws Exception {
        return groupService.deleteGroupById(id);
    }
}
