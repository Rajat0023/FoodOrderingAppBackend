package com.upgrad.FoodOrderingApp.service.business;

import com.upgrad.FoodOrderingApp.service.dao.CustomerAuthRepository;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAuthEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CustomerAuthService {
    @Autowired
    private CustomerAuthRepository customerAuthRepository;

    public CustomerAuthEntity getCustomerAuthDetails(String accessToken) throws AuthorizationFailedException {

        CustomerAuthEntity customerAuthEntity = customerAuthRepository.getCustomerAuthenticationDetails(accessToken);

        //user validations
        if (customerAuthEntity == null) {
            throw new AuthorizationFailedException("ATHR-001)", "Customer is not Logged in");

        } else if (customerAuthEntity.getLogoutTime().isAfter(customerAuthEntity.getLoginTime())) {
            throw new AuthorizationFailedException("ATHR-002", "Customer is logged out. Log in again to access this endpoint");
        } else if (customerAuthEntity.getExpiryTime().isBefore(LocalDateTime.now())) {
            throw new AuthorizationFailedException("ATHR-003", "Your session is expired. Log in again to access this endpoint");
        }


        return customerAuthEntity;
    }

}
