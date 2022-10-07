package org.perficient.registrationsystem.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.crypto.bcrypt.BCrypt;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

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
    @NotNull
    private String email;

    @NotNull
    @Length(min = 6, max = 100)
    private String password;

}
