package com.upgrad.FoodOrderingApp.service.business;

import com.upgrad.FoodOrderingApp.service.entity.PaymentEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import org.springframework.stereotype.Service;

@Service
public class RestaurantService {


    public RestaurantEntity getRestaurentEntityFromUuid(String id) {


        return new RestaurantEntity();
    }
}
