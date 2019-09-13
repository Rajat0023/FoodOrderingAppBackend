package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.api.model.CouponDetailsResponse;
import com.upgrad.FoodOrderingApp.api.model.CustomerOrderResponse;
import com.upgrad.FoodOrderingApp.api.model.SaveOrderResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/order")
public class OrderController {


    @RequestMapping(method = RequestMethod.GET, path = "/coupon/{coupon_name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CouponDetailsResponse> getCouponByCouponName() {


        return new ResponseEntity<CouponDetailsResponse>(HttpStatus.OK);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerOrderResponse> getPastOrders() {


        return new ResponseEntity<CustomerOrderResponse>(HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SaveOrderResponse> savePlacedOrder() {


        return new ResponseEntity<SaveOrderResponse>(HttpStatus.OK);
    }

}
