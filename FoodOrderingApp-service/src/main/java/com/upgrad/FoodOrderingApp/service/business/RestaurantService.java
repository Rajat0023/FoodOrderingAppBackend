package com.upgrad.FoodOrderingApp.service.business;

import com.upgrad.FoodOrderingApp.service.dao.RestaurentRepository;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import com.upgrad.FoodOrderingApp.service.exception.RestaurantNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *Business service class for functionalities related to Restaurent entity
 */
@Service
public class RestaurantService {

    @Autowired
    private RestaurentRepository restaurantRepository;

    /**
     * @param id
     * @return
     * @throws RestaurantNotFoundException
     */
    public RestaurantEntity restaurantByUUID(String id) throws RestaurantNotFoundException {


        RestaurantEntity restaurantEntity = restaurantRepository.getRestaurent(id);
        if (restaurantEntity == null) {
            throw new RestaurantNotFoundException("RNF-001", "No restaurant by this id");
        }

        return restaurantEntity;
    }
}
