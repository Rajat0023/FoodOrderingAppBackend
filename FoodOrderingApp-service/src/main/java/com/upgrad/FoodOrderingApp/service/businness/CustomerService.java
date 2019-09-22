package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.common.GenericErrorCode;
import com.upgrad.FoodOrderingApp.service.common.GenericExceptionCode;
import com.upgrad.FoodOrderingApp.service.dao.CategoryDao;
import com.upgrad.FoodOrderingApp.service.dao.CustomerDao;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAuth;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.SignUpRestrictedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional(propagation = Propagation.REQUIRED)

public class CustomerService {

    @Autowired private PasswordCryptographyProvider passwordCryptographyProvider;

    @Autowired
    CustomerDao customerDao;

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

    public boolean hasCustomerSignedOut(LocalDateTime loggedOutTime) {
        return (loggedOutTime != null && LocalDateTime.now().isAfter(loggedOutTime));
    }

    public boolean sessionExpired(LocalDateTime localDateTime){
        return (localDateTime!=null && LocalDateTime.now().isAfter(localDateTime));
    }
}


