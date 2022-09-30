package org.perficient.registrationsystem.dto;

import lombok.Data;
import org.perficient.registrationsystem.model.Professor;
import org.perficient.registrationsystem.model.Subject;

import javax.validation.constraints.Positive;
import java.util.HashSet;
import java.util.Set;

/**
 * Class GroupDto Created on 20/09/2022
 *
 * @Author Iván Camilo Rincon Saavedra
 */

@Data
public class GroupDto {
    @Positive
    private Integer number;

    private ProfessorDto professor;
    private SubjectDto subject;
    private Set<StudentDto> students = new HashSet<>();
}
