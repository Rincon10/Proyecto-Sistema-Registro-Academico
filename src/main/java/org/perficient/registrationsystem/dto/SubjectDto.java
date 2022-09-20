package org.perficient.registrationsystem.dto;

import org.perficient.registrationsystem.model.Subject;

import java.util.HashSet;
import java.util.Set;

/**
 * Class SubjectDto Created on 20/09/2022
 *
 * @Author Iván Camilo Rincon Saavedra
 */
public class SubjectDto {

    private String acronym;
    private String name;
    private Set<Subject> prerequisites = new HashSet<>();
}
