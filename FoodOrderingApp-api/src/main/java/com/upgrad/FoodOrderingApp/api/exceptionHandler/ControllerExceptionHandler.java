
package com.upgrad.FoodOrderingApp.api.exceptionHandler;


import com.upgrad.FoodOrderingApp.api.model.ErrorResponse;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {

    private ErrorResponse errorResponse = new ErrorResponse();

    @ExceptionHandler(AuthorizationFailedException.class)
    public ResponseEntity<ErrorResponse> handleAuthorizationFailedException(AuthorizationFailedException e) {

        errorResponse.setCode(e.getCode());
        errorResponse.setMessage(e.getErrorMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }


}



