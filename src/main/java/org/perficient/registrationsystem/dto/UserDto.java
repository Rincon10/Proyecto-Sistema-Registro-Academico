package org.perficient.registrationsystem.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Class UserDto Created on 20/09/2022
 *
 * @Author Iván Camilo Rincon Saavedra
 */
@Data
@AllArgsConstructor
@NoArgsConstructor

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto implements Serializable {

    private String firstName;
    private String lastName;

    @Email
    @NotNull
    private String email;

    @NotNull
    @Length(min = 6, max = 100)
    private String password;

}
