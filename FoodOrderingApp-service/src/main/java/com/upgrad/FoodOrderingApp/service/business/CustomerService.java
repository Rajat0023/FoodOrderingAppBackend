package com.upgrad.FoodOrderingApp.service.business;

import com.upgrad.FoodOrderingApp.service.dao.CustomerRepository;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * business service class for functionalities related to Customer entity
 */
@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    /**
     *
     * @param accessToken
     * @return
     * @throws AuthorizationFailedException
     */

    public CustomerEntity getCustomer(String accessToken) throws AuthorizationFailedException {

        CustomerEntity customerEntity = customerRepository.getCustomer(accessToken);

        return customerEntity;
    }
}
