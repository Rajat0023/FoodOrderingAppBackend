package com.upgrad.FoodOrderingApp.service.business;

import com.upgrad.FoodOrderingApp.service.dao.OrderItemRepository;
import com.upgrad.FoodOrderingApp.service.entity.CouponEntity;
import com.upgrad.FoodOrderingApp.service.entity.OrderEntity;
import com.upgrad.FoodOrderingApp.service.dao.OrderRepository;
import com.upgrad.FoodOrderingApp.service.entity.OrderItemEntity;
import com.upgrad.FoodOrderingApp.service.exception.CouponNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrderService {


    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;


    public CouponEntity getCouponByCouponName(String couponName) throws CouponNotFoundException {
        CouponEntity couponEntity = orderRepository.getCouponByCouponName(couponName);
        if (couponEntity == null) {

            throw new CouponNotFoundException("CPF-001", "No coupon by this name");
        }
        return couponEntity;

    }


    public CouponEntity getCouponByCouponId(String id) throws CouponNotFoundException {
        CouponEntity couponEntity = orderRepository.getCouponByCouponId(id);
        if (couponEntity == null) {

            throw new CouponNotFoundException("CPF-002", "No coupon by this id");
        }
        return couponEntity;


    }


    public OrderEntity saveOrder(OrderEntity ordersEntity) {
        return orderRepository.savePlacedOrder(ordersEntity);

    }

    public OrderItemEntity saveOrderItem(OrderItemEntity o) {

        return orderItemRepository.saveOrderItem(o);
    }

    public List<OrderEntity> getOrdersByCustomers(String uuid) {

        return orderRepository.getAllPastOrdersOfCustomer(uuid);

    }
}
