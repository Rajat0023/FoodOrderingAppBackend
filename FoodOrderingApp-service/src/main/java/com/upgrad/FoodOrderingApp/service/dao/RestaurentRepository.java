package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 */
@Repository
public class RestaurentRepository {
    @PersistenceContext
    EntityManager entityManager;

    /**
     *
     * @param uuid
     * @return
     */
    public RestaurantEntity getRestaurent(String uuid) {
        RestaurantEntity restaurantEntity = null;
        try {

            TypedQuery<RestaurantEntity> query = entityManager.createNamedQuery("getRestaurentById", RestaurantEntity.class);
            query.setParameter("uuid", uuid);
            restaurantEntity = query.getSingleResult();
        } catch (NoResultException e) {


        }
        return restaurantEntity;
    }

}
