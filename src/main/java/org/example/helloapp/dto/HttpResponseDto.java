package org.example.helloapp.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;


import java.util.Date;
import java.util.HashMap;

@Data
public class HttpResponseDto {
    private String message;
    private HttpStatus status;
    private int statusCode;
    private boolean success;
    private Object data;
    private Date timestamp = new Date();
    private HashMap<String,String> errors;
}
