package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.api.model.*;
import com.upgrad.FoodOrderingApp.service.businness.OrdersService;
import com.upgrad.FoodOrderingApp.service.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrdersService ordersService;


    @GetMapping(path = "/coupon/{coupon_name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CouponDetailsResponse> getCouponByCouponName(@PathVariable("coupon_name") String couponName,
                                                                       @RequestHeader("authorization") String accessToken) {


        return new ResponseEntity<CouponDetailsResponse>(HttpStatus.OK);
    }


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerOrderResponse> getPastOrders(@RequestHeader("authorization") String accessToken) {

//INVOLVES LINKING MULTIPLE TABLE, TO RETREIVE ??
        return new ResponseEntity<CustomerOrderResponse>(HttpStatus.OK);
    }


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SaveOrderResponse> savePlacedOrder(@RequestBody SaveOrderRequest placedOrder,
                                                             @RequestHeader("authorization") String accessToken) {

        OrdersEntity ordersEntity = new OrdersEntity();
        AddressEntity address = new AddressEntity();
        address.setUuid(placedOrder.getAddressId());

        CouponEntity couponEntity = new CouponEntity();
        couponEntity.setUuid(placedOrder.getCouponId().toString());

        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.setUuid(placedOrder.getPaymentId().toString());

        RestaurantEntity restaurant = new RestaurantEntity();
        restaurant.setUuid(placedOrder.getRestaurantId().toString());


        List<ItemQuantity> cartItemlist = placedOrder.getItemQuantities();
        List<OrderItemEntity> items = new LinkedList<>();

        for (ItemQuantity currentItem : cartItemlist) {
            ItemEntity item = new ItemEntity();
            item.setUuid(currentItem.getItemId().toString());
            OrderItemEntity orderItem = new OrderItemEntity();
            orderItem.setItemId(item);
            orderItem.setPrice(currentItem.getPrice());
            orderItem.setQuantity(currentItem.getQuantity());
            items.add(orderItem);
        }

        //populating the entity
        ordersEntity.setUuid(UUID.randomUUID().toString());  //random number
        ordersEntity.setBill(placedOrder.getBill());
        ordersEntity.setDiscount(placedOrder.getDiscount());
        ordersEntity.setDate(LocalDateTime.now());
        ordersEntity.setAddressId(address);
        ordersEntity.setCouponEntityId(couponEntity);
        ordersEntity.setOrderItems(items);
        ordersEntity.setPaymentEntityId(paymentEntity);
        ordersEntity.setRestaurantId(restaurant);
ordersService.shouldSaveOrder(ordersEntity);

        return new ResponseEntity<SaveOrderResponse>(HttpStatus.OK);
    }

}
