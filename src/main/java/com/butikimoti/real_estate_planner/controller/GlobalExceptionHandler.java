package com.butikimoti.real_estate_planner.controller;

import com.butikimoti.real_estate_planner.service.util.exceptions.ResourceNotFoundException;
import com.butikimoti.real_estate_planner.service.util.exceptions.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UnauthorizedException.class)
    public String unauthorized(UnauthorizedException ex) {
       return "error-unauthorized";
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public String resourceNotFound(ResourceNotFoundException ex) {
        return "error-not-found";
    }
}
