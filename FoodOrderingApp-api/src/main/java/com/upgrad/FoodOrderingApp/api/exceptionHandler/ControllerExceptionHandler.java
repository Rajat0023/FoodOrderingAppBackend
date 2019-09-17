
package com.upgrad.FoodOrderingApp.api.exceptionHandler;


import com.upgrad.FoodOrderingApp.api.model.ErrorResponse;
import com.upgrad.FoodOrderingApp.service.exception.*;
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

    @ExceptionHandler(CouponNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCouponNotFoundException(CouponNotFoundException e) {

        errorResponse.setCode(e.getCode());
        errorResponse.setMessage(e.getErrorMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(AddressNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCouponNotFoundException(AddressNotFoundException e) {

        errorResponse.setCode(e.getCode());
        errorResponse.setMessage(e.getErrorMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(PaymentMethodNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCouponNotFoundException(PaymentMethodNotFoundException e) {

        errorResponse.setCode(e.getCode());
        errorResponse.setMessage(e.getErrorMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RestaurantNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCouponNotFoundException(RestaurantNotFoundException e) {

        errorResponse.setCode(e.getCode());
        errorResponse.setMessage(e.getErrorMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCouponNotFoundException(ItemNotFoundException e) {

        errorResponse.setCode(e.getCode());
        errorResponse.setMessage(e.getErrorMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}



