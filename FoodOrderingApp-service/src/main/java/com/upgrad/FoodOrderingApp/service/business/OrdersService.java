package com.upgrad.FoodOrderingApp.service.business;

import com.upgrad.FoodOrderingApp.service.entity.CouponEntity;
import com.upgrad.FoodOrderingApp.service.entity.OrdersEntity;
import com.upgrad.FoodOrderingApp.service.dao.OrderRepository;
import com.upgrad.FoodOrderingApp.service.exception.CouponNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrdersService {


    @Autowired
    private OrderRepository orderRepository;


    public CouponEntity getCouponByCouponName(String couponName) throws CouponNotFoundException {
        CouponEntity couponEntity=orderRepository.getCouponByCouponName(couponName);
        if(couponEntity==null){

            throw new CouponNotFoundException("CPF-001","No coupon by this name");
        }
        return couponEntity;

    }


    public CouponEntity getCouponByCouponId(String id) throws CouponNotFoundException {
        CouponEntity couponEntity=orderRepository.getCouponByCouponId(id);
        if(couponEntity==null){

            throw new CouponNotFoundException("CPF-002","No coupon by this id");
        }
        return couponEntity;


    }


    public void shouldSaveOrder(OrdersEntity ordersEntity) {
        orderRepository.savePlacedOrder(ordersEntity);

    }

    public List<OrdersEntity> getOrdersByCustomers(String uuid) {

        return orderRepository.getAllPastOrdersOfCustomer(uuid);

    }
}
