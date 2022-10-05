package org.perficient.registrationsystem.dto.server;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Class ServerErrorResponseDto Created on 3/10/2022
 *
 * @Author Iván Camilo Rincon Saavedra
 */
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServerErrorResponseDtoException extends Exception {
    public static final String DOESNT_EXITS = "The Group that you are looking for doesn't exists.";
    public static final String ALREADY_EXITS = "The Group that you are looking for doesn't exists.";


    private String message;
    private String timeStamp = new Date().toString();
    private int httpStatus;

    public ServerErrorResponseDtoException(String message, int httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
