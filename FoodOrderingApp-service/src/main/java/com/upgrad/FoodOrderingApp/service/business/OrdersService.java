package com.upgrad.FoodOrderingApp.service.business;

import com.upgrad.FoodOrderingApp.service.entity.CouponEntity;
import com.upgrad.FoodOrderingApp.service.entity.OrdersEntity;
import com.upgrad.FoodOrderingApp.service.dao.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrdersService {


    @Autowired
    private OrderRepository orderRepository;


    public CouponEntity getCouponByCouponName(String couponName) {
        return orderRepository.getCouponByCouponName(couponName);

    }


    public CouponEntity getCouponByCouponId(String id) {
        return orderRepository.getCouponByCouponId(id);

    }


    public void shouldSaveOrder(OrdersEntity ordersEntity) {
        orderRepository.savePlacedOrder(ordersEntity);

    }

    public List<OrdersEntity> getOrdersByCustomers(String uuid) {

        return orderRepository.getAllPastOrdersOfCustomer(uuid);

    }
}
