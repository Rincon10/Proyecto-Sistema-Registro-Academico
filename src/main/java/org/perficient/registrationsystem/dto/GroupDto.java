package org.perficient.registrationsystem.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.sql.Time;
import java.util.HashSet;
import java.util.Set;

/**
 * Class GroupDto Created on 20/09/2022
 *
 * @Author Iván Camilo Rincon Saavedra
 */

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GroupDto implements Serializable {
    private Integer id;

    @Positive
    @NotNull
    private Integer number;

    @NotNull
    private ProfessorDto professor;

    @NotNull
    private SubjectDto subject;
    private Set<StudentDto> students = new HashSet<>();

    @NotNull
    private Time startTime;
    
    @NotNull
    private Time endTime;
}
