package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.common.GenericExceptionCode;
import com.upgrad.FoodOrderingApp.service.dao.CustomerDao;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAuth;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/** This service class manages all functionalities and business rules of customer management */

@Service
@Transactional(propagation = Propagation.REQUIRED)

public class CustomerService {

    @Autowired
    CustomerDao customerDao;

    /**
     * This method manages business rules for validating accessToken provided by user
     *
     */

    public CustomerAuth getCustomer(String accessToken) throws AuthorizationFailedException {

        CustomerAuth customerAuth = customerDao.getAuthToken(accessToken);
        if(customerAuth == null){
            throw new AuthorizationFailedException(
                    GenericExceptionCode.ATHR_001.getCode(),
                    GenericExceptionCode.ATHR_001.getDescription());
        }

        if(hasCustomerSignedOut(customerAuth.getLogoutTime())){
            throw new AuthorizationFailedException(
                    GenericExceptionCode.ATHR_002.getCode(),
                    GenericExceptionCode.ATHR_002.getDescription());
        }

        if(sessionExpired(customerAuth.getExpiryTime())){
            throw new AuthorizationFailedException(
                    GenericExceptionCode.ATHR_003.getCode(),
                    GenericExceptionCode.ATHR_003.getDescription());
        }

        return customerDao.getAuthToken(accessToken);
    }

    /**
     * This method manages business rules to check if customer has signed out
     *
     */

    public boolean hasCustomerSignedOut(LocalDateTime loggedOutTime) {
        return (loggedOutTime != null && LocalDateTime.now().isAfter(loggedOutTime));
    }

    /**
     * This method manages business rules for to check if the session has expired
     *
     */

    public boolean sessionExpired(LocalDateTime localDateTime){
        return (localDateTime!=null && LocalDateTime.now().isAfter(localDateTime));
    }
}
