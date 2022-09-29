package org.perficient.registrationsystem.dto;

import lombok.*;
import org.hibernate.validator.constraints.Range;
import org.perficient.registrationsystem.model.Group;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Set;

/**
 * Class StudentDto Created on 20/09/2022
 *
 * @Author Iván Camilo Rincon Saavedra
 */

@Data
public class StudentDto {
    @Range(min = 0, max = 10)
    private Integer semester;
    private Set<Group> currentGroups;
}
