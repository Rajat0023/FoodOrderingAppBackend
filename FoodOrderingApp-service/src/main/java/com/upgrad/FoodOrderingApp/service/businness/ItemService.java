package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.common.GenericExceptionCode;
import com.upgrad.FoodOrderingApp.service.dao.CategoryDao;
import com.upgrad.FoodOrderingApp.service.dao.RestaurantDao;
import com.upgrad.FoodOrderingApp.service.entity.CategoryItem;
import com.upgrad.FoodOrderingApp.service.entity.ItemEntity;
import com.upgrad.FoodOrderingApp.service.exception.RestaurantNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/** This service class manages all functionalities and business rules of item management */

@Service

public class ItemService {

    @Autowired
    RestaurantDao restaurantDao;

    @Autowired
    CategoryDao categoryDao;

    /**
     * This method manages business rules to get items by category and restaurant
     *
     */

    public List<ItemEntity> getItemsByCategoryAndRestaurant(UUID restaurantId) throws RestaurantNotFoundException {

        if(restaurantId.toString().isEmpty()){
            throw new RestaurantNotFoundException(
                    GenericExceptionCode.RNF_001.getCode(),
                    GenericExceptionCode.RNF_001.getDescription());
        }
        return restaurantDao.getItemByRestaurant(restaurantId);
    }

    /**
     * This method manages business rules to get item by category
     *
     */

    public List<CategoryItem>getItemByCategory(String categoryId){
        return categoryDao.getItemByCategoryId((categoryId));
    }

    /**
     * This method manages business rules to get items by popularity
     *
     */

    public List<ItemEntity> getItemsByPopularity(UUID uuid) throws RestaurantNotFoundException {

        if (uuid.toString().isEmpty()) {
            throw new RestaurantNotFoundException(
                    GenericExceptionCode.RNF_001.getCode(),
                    GenericExceptionCode.RNF_001.getDescription());
        }
        return restaurantDao.getItemByPopular(uuid);
    }
}
