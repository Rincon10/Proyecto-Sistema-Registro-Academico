package org.perficient.registrationsystem.controllers;

import lombok.extern.slf4j.Slf4j;
import org.perficient.registrationsystem.dto.SubjectDto;
import org.perficient.registrationsystem.services.SubjectService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * Class SubjectController Created on 20/09/2022
 *
 * @Author Iván Camilo Rincon Saavedra
 */
@Slf4j
@RestController
@RequestMapping(path = "/api/v1/subjects")
public class SubjectController {

    private final SubjectService service;

    public SubjectController(SubjectService service) {
        this.service = service;
    }

    //GET
    @GetMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Set<SubjectDto> getAllSubjects() throws Exception {
        return service.getSubjects();
    }
}
