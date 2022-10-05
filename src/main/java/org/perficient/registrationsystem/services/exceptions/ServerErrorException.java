package org.perficient.registrationsystem.services.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class ServerErrorResponseDto Created on 3/10/2022
 *
 * @Author Iván Camilo Rincon Saavedra
 */
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServerErrorException extends Exception {
    public static final String DOESNT_EXITS = "The Group that you are looking for doesn't exists.";
    public static final String ALREADY_EXITS = "The Group that you are trying to add already exists.";

    private int httpStatus;

    public ServerErrorException(String message, int httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
