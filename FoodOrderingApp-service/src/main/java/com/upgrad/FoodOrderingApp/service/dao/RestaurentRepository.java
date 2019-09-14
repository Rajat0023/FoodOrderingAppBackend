package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

public class RestaurentRepository {
    @PersistenceContext
    EntityManager entityManager;


    public RestaurantEntity getRestaurent(String uuid) {
        RestaurantEntity restaurantEntity = null;
        try {

            TypedQuery<RestaurantEntity> query = entityManager.createNamedQuery("query", RestaurantEntity.class);
            query.setParameter("uuid", uuid);
            restaurantEntity = query.getSingleResult();
        } catch (NoResultException e) {


        }
        return restaurantEntity;
    }

}
