package org.perficient.registrationsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.perficient.registrationsystem.model.Group;
import org.perficient.registrationsystem.model.enums.Department;

import java.util.Set;

/**
 * Class ProfessorDto Created on 20/09/2022
 *
 * @Author Iván Camilo Rincon Saavedra
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProfessorDto {

    private Department department;
    private Set<Group> teachingIn;
}
