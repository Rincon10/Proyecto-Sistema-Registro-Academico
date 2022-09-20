package org.perficient.registrationsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class UserDto Created on 20/09/2022
 *
 * @Author Iván Camilo Rincon Saavedra
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
