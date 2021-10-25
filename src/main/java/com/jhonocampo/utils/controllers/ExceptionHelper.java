package com.jhonocampo.utils.controllers;

import com.jhonocampo.utils.logs.LogUtil;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;


@RestControllerAdvice
public class ExceptionHelper {
    private LogUtil logUtil = new LogUtil(ExceptionHelper.class);

    @ExceptionHandler(value = { Unauthorized.class })

    public ResponseEntity<Object> handleUnauthorizedException(Unauthorized ex) {

        logUtil.error("Unauthorized Exception: " + ex.getMessage());

        return new ResponseEntity<Object>(ex.getMessage(), HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(value = { Exception.class })

    public ResponseEntity<Object> handleException(Exception ex) {

        logUtil.error("Exception: " + ex.getMessage());

        return new ResponseEntity<Object>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

    }

}
