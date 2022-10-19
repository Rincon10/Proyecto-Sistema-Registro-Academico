package org.perficient.registrationsystem.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
@Builder
@AllArgsConstructor
@NoArgsConstructor

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubjectDto implements Serializable {

    @Length(min = 3, max = 6)
    @NotNull
    private String acronym;

    @NotNull
    private String name;
    private Set<SubjectDto> prerequisites = new HashSet<>();
}
