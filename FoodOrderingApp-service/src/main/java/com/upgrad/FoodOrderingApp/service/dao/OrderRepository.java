package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.CouponEntity;
import com.upgrad.FoodOrderingApp.service.entity.OrdersEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;

@Repository
public class OrderRepository {
    @PersistenceContext
    EntityManager entityManager;

    public CouponEntity getCouponByCouponName(String couponName) {
        CouponEntity couponEntity = null;

        try {

            TypedQuery<CouponEntity> query = entityManager.createNamedQuery("findCouponByCouponName", CouponEntity.class);
            query.setParameter("couponName", couponName);
            couponEntity = query.getSingleResult();
        } catch (NoResultException e) {

        }
        return couponEntity;
    }

    public CouponEntity getCouponByCouponId(String uuid) {
        CouponEntity couponEntity = null;

        try {

            TypedQuery<CouponEntity> query = entityManager.createNamedQuery("findCouponByCouponId", CouponEntity.class);
            query.setParameter("uuid", uuid);
            couponEntity = query.getSingleResult();
        } catch (NoResultException e) {

        }
        return couponEntity;
    }


    public void savePlacedOrder(OrdersEntity transaction) {

        entityManager.persist(transaction);
    }


    public List<OrdersEntity> getAllPastOrdersOfCustomer(String customerId) {

        List<OrdersEntity> orders = null;
        try {
            TypedQuery<OrdersEntity> query = entityManager.createNamedQuery("findOrdersByCustomerId", OrdersEntity.class);

            query.setParameter("customerId", customerId);
            orders = query.getResultList();
        } catch (NoResultException e) {


        }
        return orders;
    }

}
