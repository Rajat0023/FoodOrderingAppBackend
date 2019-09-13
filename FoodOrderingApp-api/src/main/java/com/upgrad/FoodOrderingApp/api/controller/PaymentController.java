package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.api.model.PaymentListResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/payment")
public class PaymentController {


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PaymentListResponse> getPaymentMethods(){


        return new ResponseEntity<PaymentListResponse>(HttpStatus.OK);
    }
}
