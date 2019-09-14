package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.api.model.*;
import com.upgrad.FoodOrderingApp.service.business.*;
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
    @Autowired
    private CustomerService customerService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private ItemService itemService;


    @GetMapping(path = "/coupon/{coupon_name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CouponDetailsResponse> getCouponByCouponName(@PathVariable("coupon_name") String couponName,

                                                                       @RequestHeader("authorization") String accessToken) {
        //authorizing the access token in cust_auth,retreive cutomerId and get back cutomer entity(subquery)
        CustomerEntity cutomerEntity = customerService.getCustomer(accessToken);
        //validate if logout>login


        //currentTime>sessionExpiry

//positive case, after all checks
        ordersService.getCouponByCouponName(couponName);


        //entity to dto pending
        return new ResponseEntity<CouponDetailsResponse>(HttpStatus.OK);
    }


    //----------------------------------------------------------------------------------------------------------------------


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerOrderResponse> getPastOrders(@RequestHeader("authorization") String accessToken) {
//authorizing the access token
        CustomerEntity cutomerEntity = customerService.getCustomer(accessToken);

//validate if logout>login


        //currentTime>sessionExpiry
//positive case, after all checks
        ordersService.getOrdersByCustomers(cutomerEntity.getUuid());

        //entity to dto pending
        return new ResponseEntity<CustomerOrderResponse>(HttpStatus.OK);
    }


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SaveOrderResponse> savePlacedOrder(@RequestBody SaveOrderRequest placedOrder,
                                                             @RequestHeader("authorization") String accessToken) {

//authorizing the access token
        CustomerEntity cutomerEntity = customerService.getCustomer(accessToken);

//validate if logout>login

        //currentTime>sessionExpiry

//positive case, after all checks
        OrdersEntity ordersEntity = new OrdersEntity();

        AddressEntity addressEntity = addressService.getAddressEntityFromUuid(placedOrder.getAddressId().toString());

        CouponEntity couponEntity = ordersService.getCouponByCouponId(placedOrder.getCouponId().toString());

        PaymentEntity paymentEntity = paymentService.getPaymentEntityFromUuid(placedOrder.getPaymentId().toString());

        RestaurantEntity restaurant = restaurantService.getRestaurentEntityFromUuid(placedOrder.getRestaurantId().toString());


        List<ItemQuantity> cartItemlist = placedOrder.getItemQuantities();
        List<OrderItemEntity> items = new LinkedList<>();

        for (ItemQuantity currentItem : cartItemlist) {
            ItemEntity item = itemService.getItemForItemId(currentItem.getItemId().toString());
            OrderItemEntity orderItem = new OrderItemEntity();

            orderItem.setItemId(item);
            orderItem.setPrice(currentItem.getPrice());
            orderItem.setQuantity(currentItem.getQuantity());

            items.add(orderItem);
        }

        //populating the required entity fields
        ordersEntity.setUuid(UUID.randomUUID().toString());  //random number
        ordersEntity.setBill(placedOrder.getBill());
        ordersEntity.setDiscount(placedOrder.getDiscount());
        ordersEntity.setDate(LocalDateTime.now());

        ordersEntity.setAddressId(addressEntity);
        ordersEntity.setCouponEntityId(couponEntity);
        ordersEntity.setOrderItems(items);
        ordersEntity.setPaymentEntityId(paymentEntity);
        ordersEntity.setRestaurantId(restaurant);
        ordersEntity.setCustomerId(cutomerEntity);


        ordersService.shouldSaveOrder(ordersEntity);
//entity to dto pending
        return new ResponseEntity<SaveOrderResponse>(HttpStatus.OK);
    }

}
