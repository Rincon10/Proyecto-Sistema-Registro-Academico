package org.perficient.registrationsystem.dto;

import org.perficient.registrationsystem.model.Professor;
import org.perficient.registrationsystem.model.Student;
import org.perficient.registrationsystem.model.Subject;

import java.util.HashSet;
import java.util.Set;

/**
 * Class GroupDto Created on 20/09/2022
 *
 * @Author Iván Camilo Rincon Saavedra
 */
public class GroupDto {
    private Professor professor;
    private Subject subject;
    private Set<Student> students = new HashSet<>();
}
