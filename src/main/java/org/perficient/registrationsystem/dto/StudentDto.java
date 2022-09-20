package org.perficient.registrationsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.perficient.registrationsystem.model.Group;

import java.util.Set;

/**
 * Class StudentDto Created on 20/09/2022
 *
 * @Author Iván Camilo Rincon Saavedra
 */

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StudentDto {
    private Integer semester;
    private Set<Group> currentGroups;
}
