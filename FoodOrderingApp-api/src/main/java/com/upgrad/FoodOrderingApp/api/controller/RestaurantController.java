package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.service.businness.CategoryService;
import com.upgrad.FoodOrderingApp.service.businness.CustomerService;
import com.upgrad.FoodOrderingApp.service.businness.RestaurantService;
import com.upgrad.FoodOrderingApp.service.entity.*;
import com.upgrad.FoodOrderingApp.service.exception.*;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.upgrad.FoodOrderingApp.api.model.*;

import java.util.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

/**
 * This is a controller class to handle http request
 * related to restaurant management functionalities
 */

@RestController
public class RestaurantController {

    @Autowired
    RestaurantService restaurantService;

    @Autowired
    CustomerService customerService;

    @Autowired
    CategoryService categoryService;



    @RequestMapping(
            method = GET,
            path = "/restaurant",
            produces = APPLICATION_JSON_UTF8_VALUE)

    public ResponseEntity<List<RestaurantDetailsResponse>> getAllRestaurants() {

        List<RestaurantDetailsResponse> list = new LinkedList<>();
        for (RestaurantEntity restaurantEntity : restaurantService.restaurantsByRating()) {
            RestaurantDetailsResponse response = new RestaurantDetailsResponse();
            response.setId(UUID.fromString(restaurantEntity.getUuid()));
            response.setRestaurantName(restaurantEntity.getRestaurantName());
            response.setPhotoURL(restaurantEntity.getPhotoUrl());
            response.setCustomerRating(restaurantEntity.getCustomerRating());
            response.setAveragePrice(restaurantEntity.getAveragePriceForTwo());
            response.setNumberCustomersRated(restaurantEntity.getNumberOfCustomersRated());

            RestaurantDetailsResponseAddress addressResponse = new RestaurantDetailsResponseAddress();
            addressResponse.setCity(restaurantEntity.getAddress().getCity());
            addressResponse.setFlatBuildingName(restaurantEntity.getAddress().getFlatNumber());
            addressResponse.setId(UUID.fromString(restaurantEntity.getAddress().getUuid()));
            addressResponse.setLocality(restaurantEntity.getAddress().getLocality());
            addressResponse.setPincode(restaurantEntity.getAddress().getPinCode());

            RestaurantDetailsResponseAddressState restaurantDetailsResponseAddressState = new RestaurantDetailsResponseAddressState();
            restaurantDetailsResponseAddressState.setId(UUID.fromString(restaurantEntity.getAddress().getStateId().getUuid()));
            restaurantDetailsResponseAddressState.setStateName(restaurantEntity.getAddress().getStateId().getStateName());
            addressResponse.setState(restaurantDetailsResponseAddressState);

            response.setAddress(addressResponse);

            List<CategoryList> restaurantCategory = new LinkedList<>();
            for (RestaurantCategory category : categoryService.getCategoriesByRestaurant(restaurantEntity.getId())) {
                CategoryList restCategory = new CategoryList();
                restCategory.setCategoryName(category.getCategoryId().getCategoryName());
                restaurantCategory.add(restCategory);
                Collections.sort(restaurantCategory,new CategoriesComparator());
            }

            response.setCategories(restaurantCategory);
            list.add(response);
        }

        Collections.sort(list,new CustomerRatingComparator());

        return new ResponseEntity<List<RestaurantDetailsResponse>>(list, HttpStatus.OK);

    }

    @RequestMapping(
            method = GET,
            path = "/restaurant/name/{restaurant_name}",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)

    public ResponseEntity<List<RestaurantDetailsResponse>> getRestaurantByName(
            @PathVariable("restaurant_name") final String restaurantName)
            throws RestaurantNotFoundException {

        List<RestaurantEntity> restaurants = restaurantService.restaurantsByName(restaurantName);
        List<RestaurantDetailsResponse> restaurantList = new ArrayList<>();
        for (RestaurantEntity restaurantEntity : restaurants) {

            RestaurantDetailsResponseAddressState restaurantDetailsResponseAddressState = new RestaurantDetailsResponseAddressState();
            restaurantDetailsResponseAddressState.setId(UUID.fromString(restaurantEntity.getAddress().getStateId().getUuid()));
            restaurantDetailsResponseAddressState.setStateName(restaurantEntity.getAddress().getStateId().getStateName());

            RestaurantDetailsResponseAddress restaurantDetailsResponseAddress = new RestaurantDetailsResponseAddress();
            restaurantDetailsResponseAddress.setPincode(restaurantEntity.getAddress().getPinCode());
            restaurantDetailsResponseAddress.setLocality(restaurantEntity.getAddress().getLocality());
            restaurantDetailsResponseAddress.setFlatBuildingName(restaurantEntity.getAddress().getFlatNumber());
            restaurantDetailsResponseAddress.setCity(restaurantEntity.getAddress().getCity());
            restaurantDetailsResponseAddress.setId(UUID.fromString(restaurantEntity.getUuid()));
            restaurantDetailsResponseAddress.setState(restaurantDetailsResponseAddressState);

            RestaurantDetailsResponse rList = new RestaurantDetailsResponse();
            rList.setAddress(restaurantDetailsResponseAddress);
            rList.setAveragePrice(restaurantEntity.getAveragePriceForTwo());
            rList.setCustomerRating(restaurantEntity.getCustomerRating());
            rList.setNumberCustomersRated(restaurantEntity.getNumberOfCustomersRated());
            rList.setPhotoURL(restaurantEntity.getPhotoUrl());
            rList.setRestaurantName(restaurantEntity.getRestaurantName());
            rList.setId(UUID.fromString(restaurantEntity.getUuid()));

            List<CategoryList> restaurantCategoryName = new LinkedList<>();
            for(RestaurantCategory restaurantCategory1 : categoryService.getCategoriesByRestaurant(restaurantEntity.getId())){
                CategoryList categoryList = new CategoryList();
                categoryList.setCategoryName(restaurantCategory1.getCategoryId().getCategoryName());
                restaurantCategoryName.add(categoryList);

                Collections.sort(restaurantCategoryName,new CategoriesComparator());
            }

            rList.setCategories(restaurantCategoryName);
            restaurantList.add(rList);
        }

        Collections.sort(restaurantList,new CustomerRatingComparator());
        return new ResponseEntity<List<RestaurantDetailsResponse>>(restaurantList, HttpStatus.OK);
    }

    @RequestMapping(
            method = GET,
            path = "/restaurant/category/{category_id}",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )

    public ResponseEntity<List<RestaurantDetailsResponse>> getRestaurantByCategoryId(
            @PathVariable("category_id") final String categoryId) throws CategoryNotFoundException {

        List<RestaurantCategory> restaurantByCategoryId = restaurantService.restaurantByCategory(categoryId);

        List<RestaurantDetailsResponse> restaurantList = new LinkedList<>();
        for (RestaurantCategory category : restaurantByCategoryId) {
            RestaurantDetailsResponse restaurantDetailsResponse = new RestaurantDetailsResponse();
            restaurantDetailsResponse.setId(UUID.fromString(category.getRestaurantId().getUuid()));
            restaurantDetailsResponse.setRestaurantName(category.getRestaurantId().getRestaurantName());
            restaurantDetailsResponse.setPhotoURL(category.getRestaurantId().getPhotoUrl());
            restaurantDetailsResponse.setAveragePrice(category.getRestaurantId().getAveragePriceForTwo());
            restaurantDetailsResponse.setCustomerRating(category.getRestaurantId().getCustomerRating());
            restaurantDetailsResponse.setNumberCustomersRated(category.getRestaurantId().getNumberOfCustomersRated());

            RestaurantDetailsResponseAddressState restaurantDetailsResponseAddressState = new RestaurantDetailsResponseAddressState();
            restaurantDetailsResponseAddressState.setId(UUID.fromString(category.getRestaurantId().getAddress().getStateId().getUuid()));
            restaurantDetailsResponseAddressState.setStateName(category.getRestaurantId().getAddress().getStateId().getStateName());

            RestaurantDetailsResponseAddress addressResponse = new RestaurantDetailsResponseAddress();
            addressResponse.setCity(category.getRestaurantId().getAddress().getCity());
            addressResponse.setFlatBuildingName(category.getRestaurantId().getAddress().getFlatNumber());
            addressResponse.setId(UUID.fromString(category.getRestaurantId().getAddress().getUuid()));
            addressResponse.setLocality(category.getRestaurantId().getAddress().getLocality());
            addressResponse.setPincode(category.getRestaurantId().getAddress().getPinCode());
            addressResponse.setState(restaurantDetailsResponseAddressState);
            restaurantDetailsResponse.setAddress(addressResponse);

            List<CategoryList> nameResponses = new ArrayList<>();
            for (RestaurantCategory categoryName : categoryService.getCategoriesByRestaurant(category.getRestaurantId().getId())) {
                CategoryList categoryNameResponse = new CategoryList();
                categoryNameResponse.setCategoryName(categoryName.getCategoryId().getCategoryName());
                nameResponses.add(categoryNameResponse);

                Collections.sort(nameResponses,new CategoriesComparator());
            }
            restaurantDetailsResponse.setCategories(nameResponses);
            restaurantList.add(restaurantDetailsResponse);
        }

        Collections.sort(restaurantList,new CustomerRatingComparator());

        return new ResponseEntity<List<RestaurantDetailsResponse>>(restaurantList, HttpStatus.OK);
    }

    @RequestMapping(
            method = GET,
            path = "api/restaurant/{restaurant_id}",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)

    public ResponseEntity<List<RestaurantDetailsResponse>> getRestaurantByUuid(
            @PathVariable("restaurant_id") final String uuid) throws RestaurantNotFoundException {

        List<RestaurantEntity> restaurants = restaurantService.restaurantByUUID(uuid);

        List<RestaurantDetailsResponse> restaurantLists = new ArrayList<>();

        for (RestaurantEntity restaurantEntity : restaurants) {

            RestaurantDetailsResponseAddressState restaurantDetailsResponseAddressState = new RestaurantDetailsResponseAddressState();
            restaurantDetailsResponseAddressState.setId(UUID.fromString(restaurantEntity.getAddress().getStateId().getUuid()));
            restaurantDetailsResponseAddressState.setStateName(restaurantEntity.getAddress().getStateId().getStateName());

            RestaurantDetailsResponseAddress restaurantDetailsResponseAddress = new RestaurantDetailsResponseAddress();
            restaurantDetailsResponseAddress.setPincode(restaurantEntity.getAddress().getPinCode());
            restaurantDetailsResponseAddress.setLocality(restaurantEntity.getAddress().getLocality());
            restaurantDetailsResponseAddress.setFlatBuildingName(restaurantEntity.getAddress().getFlatNumber());
            restaurantDetailsResponseAddress.setCity(restaurantEntity.getAddress().getCity());
            restaurantDetailsResponseAddress.setId(UUID.fromString(restaurantEntity.getUuid()));
            restaurantDetailsResponseAddress.setState(restaurantDetailsResponseAddressState);

            RestaurantDetailsResponse restList = new RestaurantDetailsResponse();

            restList.setAddress(restaurantDetailsResponseAddress);
            restList.setAveragePrice(restaurantEntity.getAveragePriceForTwo());
            restList.setCustomerRating(restaurantEntity.getCustomerRating());
            restList.setNumberCustomersRated(restaurantEntity.getNumberOfCustomersRated());
            restList.setPhotoURL(restaurantEntity.getPhotoUrl());
            restList.setRestaurantName(restaurantEntity.getRestaurantName());
            restList.setId((UUID.fromString(restaurantEntity.getUuid())));

            List<CategoryList> categoryLists = new ArrayList<>();
            for(RestaurantCategory restaurantCategory : categoryService.getCategoriesByRestaurant(restaurantEntity.getId())){
                CategoryList categoryList = new CategoryList();
                categoryList.setCategoryName(restaurantCategory.getCategoryId().getCategoryName());
                categoryLists.add(categoryList);

                Collections.sort(categoryLists,new CategoriesComparator());
            }

            restList.setCategories(categoryLists);
            restaurantLists.add(restList);
        }

        Collections.sort(restaurantLists,new CustomerRatingComparator());

        return new ResponseEntity<List<RestaurantDetailsResponse>>(restaurantLists, HttpStatus.OK);
    }


    @RequestMapping(
            method = PUT,
            path = "/api/restaurant/{restaurant_id}",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)

    public ResponseEntity<RestaurantUpdatedResponse> updateRestaurantRatings(
            @PathVariable("restaurant_id") final String restaurantId,
            @RequestParam("customer_rating") final Double customerRating,
            @RequestHeader("authorization") final String accessToken)
            throws AuthorizationFailedException,RestaurantNotFoundException, InvalidRatingException {

        RestaurantEntity restaurant = new RestaurantEntity();
        restaurant.setUuid(restaurant.getUuid());
        restaurant.setCustomerRating(restaurant.getCustomerRating());

        customerService.getCustomer(accessToken);

        restaurantService.updateRestaurantRating(restaurantId,customerRating);

        RestaurantUpdatedResponse restaurantUpdatedResponse =
                new RestaurantUpdatedResponse().id(UUID.fromString(restaurantId)).status("RESTAURANT RATING UPDATED SUCCESSFULLY");

        return new ResponseEntity<RestaurantUpdatedResponse>(restaurantUpdatedResponse,HttpStatus.OK);
    }

    /**to sort restaurants by ratings*/
    static class CustomerRatingComparator implements Comparator<RestaurantDetailsResponse> {

        @Override
        public int compare(RestaurantDetailsResponse rating1, RestaurantDetailsResponse rating2) {

            return rating1.getCustomerRating().compareTo(rating2.getCustomerRating());
        }
    }

    /**to sort categories based on alphabetical order */
    static class CategoriesComparator implements Comparator<CategoryList>{

        @Override
        public int compare(CategoryList category1, CategoryList category2){

            return category1.getCategoryName().compareTo(category2.getCategoryName());
        }
    }

}



