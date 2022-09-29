package org.perficient.registrationsystem.dto;

import lombok.Data;
import org.perficient.registrationsystem.model.enums.Department;

import java.util.Set;

/**
 * Class ProfessorDto Created on 20/09/2022
 *
 * @Author Iván Camilo Rincon Saavedra
 */
@Data
public class ProfessorDto extends UserDto {

    private Department department;
    private Set<GroupDto> teachingIn;
}
