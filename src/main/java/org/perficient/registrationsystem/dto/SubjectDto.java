package org.perficient.registrationsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.perficient.registrationsystem.model.Subject;

import java.util.HashSet;
import java.util.Set;

/**
 * Class SubjectDto Created on 20/09/2022
 *
 * @Author Iván Camilo Rincon Saavedra
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SubjectDto {

    private String acronym;
    private String name;
    private Set<Subject> prerequisites = new HashSet<>();
}
