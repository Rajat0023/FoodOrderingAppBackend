package com.upgrad.FoodOrderingApp.service.business;

import com.upgrad.FoodOrderingApp.service.dao.RestaurentRepository;
import com.upgrad.FoodOrderingApp.service.entity.PaymentEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import com.upgrad.FoodOrderingApp.service.exception.RestaurantNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RestaurantService {

    @Autowired
    private RestaurentRepository restaurentRepository;

    public RestaurantEntity getRestaurentEntityFromUuid(String id) throws RestaurantNotFoundException{


        RestaurantEntity restaurantEntity= restaurentRepository.getRestaurent(id);
        if(restaurantEntity==null){
            throw new RestaurantNotFoundException("RNF-001","No restaurant by this id");
        }

        return restaurantEntity;
    }
}
