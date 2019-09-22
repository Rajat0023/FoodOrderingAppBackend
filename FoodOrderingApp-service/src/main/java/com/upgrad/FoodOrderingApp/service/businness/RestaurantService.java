package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.common.GenericExceptionCode;
//import com.upgrad.FoodOrderingApp.service.dao.CustomerDao;
import com.upgrad.FoodOrderingApp.service.dao.CategoryDao;
import com.upgrad.FoodOrderingApp.service.dao.CustomerDao;
import com.upgrad.FoodOrderingApp.service.dao.RestaurantDao;
import com.upgrad.FoodOrderingApp.service.entity.*;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.CategoryNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.InvalidRatingException;
import com.upgrad.FoodOrderingApp.service.exception.RestaurantNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.support.HttpRequestHandlerServlet;
import sun.net.httpserver.HttpServerImpl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(propagation = Propagation.REQUIRED)

public class RestaurantService {

    @Autowired
    private RestaurantDao restaurantDao;

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private CategoryDao categoryDao;


    public List<RestaurantEntity> restaurantsByRating() {
        return restaurantDao.getAllRestaurants();
    }

    public List<CategoryEntity> getCategoryName(String categoryName){
        return categoryDao.getCategoryNameById(categoryName);
    }

    public List<RestaurantEntity> restaurantsByName(String restaurantName)
            throws RestaurantNotFoundException {
        if (restaurantName.isEmpty()) {
            throw new RestaurantNotFoundException(
                    GenericExceptionCode.RNF_003.getCode(),
                    GenericExceptionCode.RNF_003.getDescription());
        }
        return restaurantDao.getRestaurantByName(restaurantName);

    }

    public List<RestaurantCategory> restaurantByCategory(String categoryId) throws CategoryNotFoundException {
        if (categoryId.isEmpty()) {
            throw new CategoryNotFoundException(
                    GenericExceptionCode.CNF_001.getCode(),
                    GenericExceptionCode.CNF_001.getDescription());
        } else if (categoryId.equals(" ")) {
            throw new CategoryNotFoundException(
                    GenericExceptionCode.CNF_002.getCode(),
                    GenericExceptionCode.CNF_002.getDescription());
        }

        return restaurantDao.getRestaurantByCategoryId(categoryId);
    }

    public List<RestaurantEntity> restaurantByUUID(String someRestaurantId) throws RestaurantNotFoundException {

        if (someRestaurantId.isEmpty()) {
            throw new RestaurantNotFoundException(
                    GenericExceptionCode.RNF_002.getCode(),
                    GenericExceptionCode.RNF_002.getDescription());
        } else if (someRestaurantId.equals(" ")) {
            throw new RestaurantNotFoundException(
                    GenericExceptionCode.RNF_001.getCode(),
                    GenericExceptionCode.RNF_001.getDescription());
        }
        return restaurantDao.restaurantByUUID(someRestaurantId);
    }

    public RestaurantEntity updateRestaurantRating(String restaurantId, Double customerRating)
            throws RestaurantNotFoundException,InvalidRatingException {


        if (restaurantId.isEmpty()) {
            throw new RestaurantNotFoundException(
                    GenericExceptionCode.RNF_002.getCode(),
                    GenericExceptionCode.RNF_002.getDescription());
        } else if (restaurantId.equals(" ")) {
            throw new RestaurantNotFoundException(
                    GenericExceptionCode.RNF_001.getCode(),
                    GenericExceptionCode.RNF_001.getDescription());
        } else if (customerRating == null || (customerRating < 1 && customerRating > 5)) {
            throw new InvalidRatingException(
                    GenericExceptionCode.IRE_001.getCode(),
                    GenericExceptionCode.IRE_001.getDescription());
        } else {

            return restaurantDao.updateRestaurantRating(restaurantId, BigDecimal.valueOf(customerRating));
        }

    }

}


