package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.CouponEntity;
import com.upgrad.FoodOrderingApp.service.entity.OrderEntity;
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


    public OrderEntity savePlacedOrder(OrderEntity transaction) {

        entityManager.persist(transaction);
        return transaction;
    }


    public List<OrderEntity> getAllPastOrdersOfCustomer(String customerId) {

        List<OrderEntity> orders = null;
        try {
            TypedQuery<OrderEntity> query = entityManager.createNamedQuery("findOrdersByCustomerId", OrderEntity.class);

            query.setParameter("customerId", customerId);
            orders = query.getResultList();
        } catch (NoResultException e) {


        }
        return orders;
    }

}
