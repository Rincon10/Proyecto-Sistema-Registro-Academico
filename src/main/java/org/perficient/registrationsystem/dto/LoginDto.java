package org.perficient.registrationsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Class LoginDto Created on 20/10/2022
 *
 * @Author Iván Camilo Rincon Saavedra
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto implements Serializable {
    private String email;
    private String password;
}
