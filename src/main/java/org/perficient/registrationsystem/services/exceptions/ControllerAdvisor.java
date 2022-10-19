package org.perficient.registrationsystem.services.exceptions;

import org.perficient.registrationsystem.controllers.GroupController;
import org.perficient.registrationsystem.controllers.SubjectController;
import org.perficient.registrationsystem.controllers.UserController;
import org.perficient.registrationsystem.dto.server.ServerResponseDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Class ControllerAdvisor Created on 11/10/2022
 *
 * @Author Iván Camilo Rincon Saavedra
 */
@ControllerAdvice(basePackageClasses = {GroupController.class, SubjectController.class, UserController.class})
public class ControllerAdvisor extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        BindingResult bindingResult = ex.getBindingResult();

        List<String> errors = bindingResult.getFieldErrors()
                .stream()
                .map(f -> "The field " + f.getField() + " " + f.getDefaultMessage())
                .collect(Collectors.toList());
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    //HANDLER EXCEPTION
    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<?> handleAccessDeniedException(
            Exception ex, WebRequest request) {
        return new ResponseEntity<>(
                "Access denied", new HttpHeaders(), HttpStatus.FORBIDDEN);
    }


    @ExceptionHandler(ServerErrorException.class)
    public ResponseEntity<?> serverErrorResponseHandlerException(ServerErrorException e) {
        String message = e.getMessage();
        int httpStatus = e.getHttpStatus();

        Logger.getLogger(ControllerAdvisor.class.getName()).log(Level.SEVERE, null, message);

        return new ResponseEntity<>(new ServerResponseDto(message, httpStatus), HttpStatus.valueOf(httpStatus));
    }
}
