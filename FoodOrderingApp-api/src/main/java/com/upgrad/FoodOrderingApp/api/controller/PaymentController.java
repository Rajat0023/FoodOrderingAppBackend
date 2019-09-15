package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.api.model.PaymentListResponse;
import com.upgrad.FoodOrderingApp.api.model.PaymentResponse;
import com.upgrad.FoodOrderingApp.service.business.CustomerService;
import com.upgrad.FoodOrderingApp.service.business.PaymentService;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.entity.PaymentEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping("/payment")
public class PaymentController {
    @Autowired
    private PaymentService paymentsService;
    @Autowired
    private CustomerService customerService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PaymentListResponse> getPaymentMethods(@RequestHeader("authorization") String accessToken) {
        CustomerEntity cutomerEntity = customerService.getCustomer(accessToken);  //check authorization to rest uri

        List<PaymentEntity> paymentEntityList = paymentsService.getAllPaymentMethods();
        List<PaymentResponse> paymentResponseList = new LinkedList<>();
        for (PaymentEntity paymentEntity : paymentEntityList) {
            PaymentResponse paymentResponse = new PaymentResponse();
            paymentResponse.setPaymentName(paymentEntity.getPaymentName());
            // paymentResponse.setId();
            paymentResponseList.add(paymentResponse);
        }
        PaymentListResponse paymentListResponse = new PaymentListResponse();

        paymentListResponse.setPaymentMethods(paymentResponseList);
        return new ResponseEntity<PaymentListResponse>( paymentListResponse,HttpStatus.OK);
    }
}