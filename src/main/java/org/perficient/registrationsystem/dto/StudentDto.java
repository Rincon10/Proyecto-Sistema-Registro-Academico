package org.perficient.registrationsystem.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Range;

import java.util.Set;

/**
 * Class StudentDto Created on 20/09/2022
 *
 * @Author Iván Camilo Rincon Saavedra
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder

@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentDto extends UserDto {
    @Range(min = 0, max = 10)
    private Integer semester;
    private Set<GroupDto> currentGroups;
}
