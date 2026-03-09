package org.example.helloapp.controller;

import io.jsonwebtoken.security.SignatureException;
import org.example.helloapp.dto.HttpResponseDto;
import org.example.helloapp.exception.UnauthorisedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    HttpResponseDto error = new HttpResponseDto();

    @ExceptionHandler({ ArithmeticException.class })
    public ResponseEntity<HttpResponseDto> handleGlobalException(ArithmeticException exception) {


        error.setMessage(exception.getMessage());
        error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        error.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.setSuccess(false);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }


    @ExceptionHandler({ UnauthorisedException.class })
    public ResponseEntity<HttpResponseDto> unauthorised(UnauthorisedException exception) {


        error.setMessage(exception.getMessage());
        error.setStatus(HttpStatus.UNAUTHORIZED);
        error.setStatusCode(HttpStatus.UNAUTHORIZED.value());
        error.setSuccess(false);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<HttpResponseDto> handleValidation(MethodArgumentNotValidException exception) {

        HashMap<String, String> errors = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));


        error.setMessage("Validation failed");
        error.setStatus(HttpStatus.BAD_REQUEST);
        error.setStatusCode(HttpStatus.BAD_REQUEST.value());
        error.setErrors(errors);
        error.setSuccess(false);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }



    @ExceptionHandler({ SignatureException.class })
    public ResponseEntity<HttpResponseDto> jwtException(SignatureException exception) {


        error.setMessage(exception.getMessage());
        error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        error.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.setSuccess(false);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }


    @ExceptionHandler({ IOException.class })
    public ResponseEntity<HttpResponseDto> fileUpload(IOException exception) {


        error.setMessage(exception.getMessage());
        error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        error.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.setSuccess(false);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception ex) {

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of(
                        "success", false,
                        "message", ex.getMessage()
                ));
    }

}
