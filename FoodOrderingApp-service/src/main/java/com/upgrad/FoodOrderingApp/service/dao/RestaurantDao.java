package com.upgrad.FoodOrderingApp.service.dao;

//import com.upgrad.FoodOrderingApp.service.entity.Category;
import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;
//import com.upgrad.FoodOrderingApp.service.entity.Item;
//import com.upgrad.FoodOrderingApp.service.entity.Restaurant;
import com.upgrad.FoodOrderingApp.service.entity.ItemEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantCategory;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import com.upgrad.FoodOrderingApp.service.exception.RestaurantNotFoundException;
import org.apache.commons.lang3.reflect.Typed;
import org.springframework.data.annotation.Persistent;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository

public class RestaurantDao {

    @PersistenceContext
    private EntityManager entityManager;

    public List<RestaurantEntity> getAllRestaurants() {
        List<RestaurantEntity> restaurantList = null;

        TypedQuery<RestaurantEntity> query =
                entityManager.createNamedQuery("allRestaurants", RestaurantEntity.class);
        restaurantList = query.getResultList();

        return restaurantList;
    }

    public List<RestaurantEntity> getRestaurantByName(String restaurantName) {
        List<RestaurantEntity> restaurantList = null;
        TypedQuery<RestaurantEntity> query =
                entityManager.createNamedQuery("findRestaurantByName", RestaurantEntity.class);
        query.setParameter("restaurantName", "%" + restaurantName + "%");

        restaurantList = query.getResultList();
        return restaurantList;
    }

    private List<RestaurantEntity> noList = new ArrayList<>();

    public List<RestaurantEntity> getNoList() {
        return noList;
    }

    public List<CategoryEntity> getAllCategories() {
        List<CategoryEntity> categoryList = null;

        TypedQuery<CategoryEntity> query =
                entityManager.createNamedQuery("allCategory", CategoryEntity.class);
        categoryList = query.getResultList();

        return categoryList;
    }

    public List<RestaurantCategory> getCategoryByRestaurant(Integer restaurantId) {

        TypedQuery<RestaurantCategory> query = entityManager.createNamedQuery("getCategoryByRestaurant", RestaurantCategory.class);
        query.setParameter("restaurantId", restaurantId);
        return query.getResultList();
    }

    public List<RestaurantCategory> getRestaurantByCategoryId(String uuid) {
        List<RestaurantCategory> restaurantList = null;
        TypedQuery<RestaurantCategory> query =
                entityManager.createNamedQuery("findRestaurantByCategoryId", RestaurantCategory.class);
        query.setParameter("uuid", uuid);
        restaurantList = query.getResultList();
        return restaurantList;
    }

    public List<RestaurantEntity> restaurantByUUID(String uuid) {
        List<RestaurantEntity> restaurantList = null;
        TypedQuery<RestaurantEntity> query =
                entityManager.createNamedQuery("findRestaurantById", RestaurantEntity.class);
        query.setParameter("uuid", uuid);
        restaurantList = query.getResultList();
        return restaurantList;

    }

    public List<ItemEntity> getItemByRestaurant(UUID uuid) {
        List<ItemEntity> items = null;
        TypedQuery<ItemEntity> query =
                entityManager.createNamedQuery("itemsByRestaurant", ItemEntity.class);
        items = query.getResultList();
        return items;

    }

    public RestaurantEntity updateRestaurantRating(String restaurantId, BigDecimal customerRating) {

        TypedQuery<RestaurantEntity> query =
                entityManager.createNamedQuery("findRestaurantById", RestaurantEntity.class);
        query.setParameter("uuid", restaurantId);

        RestaurantEntity r = query.getSingleResult();
        BigDecimal existingRating = r.getCustomerRating();
        Integer numberOfCustomers = r.getNumberOfCustomersRated();

        BigDecimal total = existingRating.multiply(new BigDecimal(numberOfCustomers));
        BigDecimal a = total.add(customerRating);
        BigDecimal b= new BigDecimal(numberOfCustomers+1);
        BigDecimal newRating = a.divide(b,2, RoundingMode.HALF_UP);
        //BigDecimal newRating = (total.add(customerRating)).divide(new BigDecimal(numberOfCustomers + 1));

        r.setCustomerRating(newRating);
        r.setNumberOfCustomersRated(++numberOfCustomers);

        entityManager.persist(r);
        entityManager.flush();

        return r;

    }

}


