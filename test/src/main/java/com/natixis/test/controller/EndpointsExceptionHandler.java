package com.natixis.test.controller;

import com.natixis.test.exception.InvalidTransferException;
import com.natixis.test.exception.TransferNotFoundException;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class EndpointsExceptionHandler {

    @ExceptionHandler( InvalidTransferException.class )
    public ResponseEntity<String> handleBadRequest(BadRequestException ex ) {
        return new ResponseEntity<>( ex.getMessage(), HttpStatus.BAD_REQUEST );
    }

    @ExceptionHandler( TransferNotFoundException.class )
    public ResponseEntity<String> handleNotFound( TransferNotFoundException ex ) {
        return new ResponseEntity<>( ex.getMessage(), HttpStatus.NOT_FOUND );
    }

}
