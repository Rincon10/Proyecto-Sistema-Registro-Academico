package org.perficient.registrationsystem.dto;

import lombok.*;
import org.perficient.registrationsystem.model.Group;
import org.perficient.registrationsystem.model.enums.Department;

import java.util.Set;

/**
 * Class ProfessorDto Created on 20/09/2022
 *
 * @Author Iván Camilo Rincon Saavedra
 */
@Data
public class ProfessorDto {

    private Department department;
    private Set<Group> teachingIn;
}
