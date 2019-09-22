package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.common.GenericExceptionCode;
import com.upgrad.FoodOrderingApp.service.dao.CategoryDao;
//import com.upgrad.FoodOrderingApp.service.entity.Category;
import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantCategory;
import com.upgrad.FoodOrderingApp.service.exception.CategoryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class CategoryService {

    @Autowired
    CategoryDao categoryDao;

    public List<CategoryEntity> getCategoriesList() {
        return categoryDao.getAllCategories();
    }

    public List<CategoryEntity> getCategoryById(String categoryId) throws CategoryNotFoundException {

        if(categoryId.isEmpty()){
            throw new CategoryNotFoundException(
                    GenericExceptionCode.CNF_001.getCode(),
                    GenericExceptionCode.CNF_001.getDescription());
        }

        else if(categoryId == " "){
            throw new CategoryNotFoundException(
                    GenericExceptionCode.CNF_002.getCode(),
                    GenericExceptionCode.CNF_002.getDescription());
        }

        return categoryDao.getCategoryById(categoryId);
    }

    public List<RestaurantCategory> getCategoriesByRestaurant(Integer restaurantId) {
        return categoryDao.getCategoryByRestaurant(restaurantId);
    }

}


