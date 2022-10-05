package org.perficient.registrationsystem.dto.server;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

/**
 * Class ServerResponseDto Created on 5/10/2022
 *
 * @Author Iván Camilo Rincon Saavedra
 */

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServerResponseDto {
    private int httpStatus;
    private String timeStamp = new Date().toString();
    private String message;


    public ServerResponseDto(String message, int httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
