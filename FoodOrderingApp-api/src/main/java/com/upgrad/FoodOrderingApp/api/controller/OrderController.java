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
        CouponEntity couponEntity = ordersService.getCouponByCouponName(couponName);

        CouponDetailsResponse couponDetailsResponse = new CouponDetailsResponse();
//entity to dto pending
        couponDetailsResponse.setCouponName(couponEntity.getCouponName());
        UUID uuid = UUID.fromString(couponEntity.getUuid());
        couponDetailsResponse.setId(uuid);
        couponDetailsResponse.setPercent(couponEntity.getPercent());

        return new ResponseEntity<>(couponDetailsResponse, HttpStatus.OK);
    }


    //----------------------------------------------------------------------------------------------------------------------


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerOrderResponse> getPastOrders(@RequestHeader("authorization") String accessToken) {
//authorizing the access token
        CustomerEntity cutomerEntity = customerService.getCustomer(accessToken);

//validate if logout>login


        //currentTime>sessionExpiry
//positive case, after all checks
        List<OrdersEntity> list = ordersService.getOrdersByCustomers(cutomerEntity.getUuid());
        CustomerOrderResponse customerOrderResponse = new CustomerOrderResponse();
        List<OrderList> orderList = new LinkedList<OrderList>();
        List<ItemQuantityResponse> itemListPerOrder = new LinkedList<ItemQuantityResponse>();
        //entity to dto pending
        for (OrdersEntity entity : list) {
            OrderList orderModel = new OrderList();


            for (OrderItemEntity item : entity.getOrderItems()) {
                ItemQuantityResponseItem itemQuantityResponseItem = new ItemQuantityResponseItem();
                ItemQuantityResponse itemQuantityResponse = new ItemQuantityResponse();
                UUID uuid = UUID.fromString(item.getItemId().getUuid());
                itemQuantityResponseItem.setId(uuid); //how to convert integer to UUID
                itemQuantityResponseItem.setItemName(item.getItemId().getItemName());
                itemQuantityResponseItem.setItemPrice(item.getItemId().getPrice());
                if (item.getItemId().getType().equals("0")) {
                    //veg
                    itemQuantityResponseItem.setType(ItemQuantityResponseItem.TypeEnum.VEG);
                } else {
                    //non veg
                    itemQuantityResponseItem.setType(ItemQuantityResponseItem.TypeEnum.NON_VEG);
                }


                itemQuantityResponse.setItem(itemQuantityResponseItem);
                itemQuantityResponse.setPrice(item.getPrice());
                itemQuantityResponse.setQuantity(item.getQuantity());

                itemListPerOrder.add(itemQuantityResponse);
            }
            UUID uuid = UUID.fromString(entity.getUuid());
            orderModel.setId(uuid);
            //   orderModel.setAddress();
            orderModel.setBill(entity.getBill());
            // orderModel.setCoupon();
            //orderModel.setCustomer();
            //orderModel.setPayment();

            orderModel.setDate(entity.getDate().toString());
            orderModel.setDiscount(entity.getDiscount());
            orderModel.setItemQuantities(itemListPerOrder); //list of orders

            orderList.add(orderModel);


        }

        customerOrderResponse.setOrders(orderList);
        return new ResponseEntity<>(customerOrderResponse, HttpStatus.OK);
    }


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SaveOrderResponse> savePlacedOrder(@RequestBody SaveOrderRequest placedOrder,
                                                             @RequestHeader("authorization") String accessToken) {

//authorizing the access token
        CustomerEntity customerEntity = customerService.getCustomer(accessToken);

//validate if logout>login

        //currentTime>sessionExpiry

//positive case, after all checks
        OrdersEntity ordersEntity = new OrdersEntity();

        AddressEntity addressEntity = addressService.getAddressEntityFromUuid(placedOrder.getAddressId());

        CouponEntity couponEntity = ordersService.getCouponByCouponId(placedOrder.getCouponId().toString());

        PaymentEntity paymentEntity = paymentService.getPaymentEntityFromUuid(placedOrder.getPaymentId().toString());

        RestaurantEntity restaurant = restaurantService.getRestaurentEntityFromUuid(placedOrder.getRestaurantId().toString());


        List<ItemQuantity> cartItemlist = placedOrder.getItemQuantities();
        List<OrderItemEntity> items = new LinkedList<>();

        for (ItemQuantity currentItem : cartItemlist) {
            ItemEntity item = itemService.getItemForItemId(currentItem.getItemId().toString());
            OrderItemEntity orderItem = new OrderItemEntity();

            orderItem.setItemId(item);
            orderItem.setOrderId(ordersEntity);
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
        ordersEntity.setCustomerId(customerEntity);

        ordersService.shouldSaveOrder(ordersEntity);
//entity to dto pending
        return new ResponseEntity<SaveOrderResponse>(HttpStatus.OK);
    }

}
