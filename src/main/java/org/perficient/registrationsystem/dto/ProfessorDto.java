package org.perficient.registrationsystem.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.perficient.registrationsystem.model.enums.Department;

import java.util.Set;

/**
 * Class ProfessorDto Created on 20/09/2022
 *
 * @Author Iván Camilo Rincon Saavedra
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfessorDto extends UserDto {

    private Department department;
    private Set<GroupDto> teachingIn;
}
