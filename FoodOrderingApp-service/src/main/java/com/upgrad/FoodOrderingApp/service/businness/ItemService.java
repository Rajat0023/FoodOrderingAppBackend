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

@Service

public class ItemService {

    @Autowired
    RestaurantDao restaurantDao;

    @Autowired
    CategoryDao categoryDao;

    public List<ItemEntity> getItemByRestaurantId(UUID restaurantId) throws RestaurantNotFoundException {

        if(restaurantId.toString().isEmpty()){
            throw new RestaurantNotFoundException(
                    GenericExceptionCode.RNF_001.getCode(),
                    GenericExceptionCode.RNF_001.getDescription());
        }
        return restaurantDao.getItemByRestaurant(restaurantId);
    }

    public List<CategoryItem>getItemByCategory(String categoryId){
        return categoryDao.getItemByCategoryId((categoryId));
    }
}

