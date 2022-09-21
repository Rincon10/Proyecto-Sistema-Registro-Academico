package org.perficient.registrationsystem.controllers;

import lombok.extern.slf4j.Slf4j;
import org.perficient.registrationsystem.services.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class SubjectController Created on 20/09/2022
 *
 * @Author Iván Camilo Rincon Saavedra
 */
@Slf4j
@Controller
@RequestMapping("/api/v1")
public class SubjectController {

    private final SubjectService service;

    public SubjectController(@Autowired SubjectService service) {
        this.service = service;
    }

    @RequestMapping(path = "/subjects")
    public ResponseEntity<?> getAllSubjects() {
        try {
            return new ResponseEntity<>(service.getSubjects(), HttpStatus.OK);
        } catch (Exception ex) {
            Logger.getLogger(SubjectController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
