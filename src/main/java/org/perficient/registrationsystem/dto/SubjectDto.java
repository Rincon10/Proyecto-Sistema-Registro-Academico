package org.perficient.registrationsystem.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.perficient.registrationsystem.model.Subject;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

/**
 * Class SubjectDto Created on 20/09/2022
 *
 * @Author Iván Camilo Rincon Saavedra
 */

@Data
public class SubjectDto {

    @Length(max = 6)
    private String acronym;
    @NotNull
    private String name;
    private Set<SubjectDto> prerequisites = new HashSet<>();
}
