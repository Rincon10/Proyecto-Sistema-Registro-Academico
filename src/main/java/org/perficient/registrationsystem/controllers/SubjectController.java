package org.perficient.registrationsystem.controllers;

import lombok.extern.slf4j.Slf4j;
import org.perficient.registrationsystem.dto.SubjectDto;
import org.perficient.registrationsystem.services.SubjectService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
        return service.getAllSubjects();
    }

    @GetMapping(path = "/{acronym}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public SubjectDto getSubjectByAcronym(@PathVariable String acronym) throws Exception {
        return service.getSubjectByAcronym(acronym.toUpperCase());
    }

    //POST
    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public SubjectDto addSubject(@Valid @RequestBody SubjectDto subjectDto) throws Exception {
        return service.addSubject(subjectDto);
    }

    //PUT
    @PutMapping(path = "/{acronym}")
    @ResponseBody
    @ResponseStatus(HttpStatus.ACCEPTED)
    public SubjectDto updateSubjectByAcronym(@PathVariable String acronym, @Valid @RequestBody SubjectDto subjectDto) throws Exception {
        return service.updateSubjectByAcronym(acronym.toUpperCase(), subjectDto);
    }

    //DELETE

    @DeleteMapping(path = "/{acronym}")
    @ResponseBody
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Boolean deleteByAcronym(@PathVariable String acronym) throws Exception {
        return service.deleteSubjectByAcronym(acronym);
    }
}
