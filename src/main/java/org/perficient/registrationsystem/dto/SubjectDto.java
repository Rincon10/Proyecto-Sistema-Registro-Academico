package org.perficient.registrationsystem.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Class SubjectDto Created on 20/09/2022
 *
 * @Author Iván Camilo Rincon Saavedra
 */

@Data
public class SubjectDto implements Serializable {

    @Length(max = 6)
    private String acronym;
    @NotNull
    private String name;
    private Set<SubjectDto> prerequisites = new HashSet<>();
}
