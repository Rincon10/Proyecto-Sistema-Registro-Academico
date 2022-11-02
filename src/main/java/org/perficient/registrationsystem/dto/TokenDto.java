package org.perficient.registrationsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * Class TokenDto Created on 20/10/2022
 *
 * @Author Iván Camilo Rincon Saavedra
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenDto implements Serializable {
    private String token;

    private Date expirationDate;
}
