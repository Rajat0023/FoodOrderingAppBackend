package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.entity.OrdersEntity;
import com.upgrad.FoodOrderingApp.service.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrdersService {


    @Autowired
    private OrderRepository orderRepository;


    public void getCouponByCouponName(String couponName) {


    }


    public void shouldSaveOrder(OrdersEntity ordersEntity) {
        orderRepository.savePlacedOrder(ordersEntity);


    }

    public void shouldGetPlacedOrderDetails() {

    }
}
