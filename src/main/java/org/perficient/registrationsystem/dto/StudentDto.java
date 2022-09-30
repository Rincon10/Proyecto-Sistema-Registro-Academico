package org.perficient.registrationsystem.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import java.util.Set;

/**
 * Class StudentDto Created on 20/09/2022
 *
 * @Author Iván Camilo Rincon Saavedra
 */

@Data
public class StudentDto extends UserDto {
    @Range(min = 0, max = 10)
    private Integer semester;
    private Set<GroupDto> currentGroups;
}
