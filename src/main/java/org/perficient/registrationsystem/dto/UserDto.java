package org.perficient.registrationsystem.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;

/**
 * Class UserDto Created on 20/09/2022
 *
 * @Author Iván Camilo Rincon Saavedra
 */
@Data
public class UserDto {

    private String firstName;
    private String lastName;

    @Email
    private String email;
    @Length(min = 6)
    private String password;
}
