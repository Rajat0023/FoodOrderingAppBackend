package com.upgrad.FoodOrderingApp.service.repository;

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

            TypedQuery<CouponEntity> query = entityManager.createNamedQuery("query", CouponEntity.class);
            query.setParameter("couponName", couponName);
            couponEntity = query.getSingleResult();
        } catch (NoResultException e) {

        }
        return couponEntity;
    }






    public void savePlacedOrder(OrdersEntity transaction) {
        entityManager.persist(transaction);
    }





    public List<OrdersEntity> getAllPastOrdersOfUser(String accessToken) {

        List<OrdersEntity> orders = null;
        try {
            TypedQuery<OrdersEntity> query = entityManager.createNamedQuery("select o from OrdersEntity where o.customerId.salt =:accessToken", OrdersEntity.class);

            query.setParameter("accessToken", accessToken);
            orders = query.getResultList();
        } catch (NoResultException e) {


        }
        return orders;
    }

}
