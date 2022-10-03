package org.perficient.registrationsystem.dto.server;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Class ServerErrorResponseDto Created on 3/10/2022
 *
 * @Author Iván Camilo Rincon Saavedra
 */
@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServerErrorResponseDto {
    private String message;
    private Throwable Cause;
    private int httpStatus;

    private String timeStamp = new Date().toString();

    public ServerErrorResponseDto(String message, Throwable cause, int httpStatus) {
        this.message = message;
        Cause = cause;
        this.httpStatus = httpStatus;
    }
}
