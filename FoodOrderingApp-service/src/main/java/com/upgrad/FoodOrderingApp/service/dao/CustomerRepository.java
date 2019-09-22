package com.upgrad.FoodOrderingApp.service.dao;


import com.upgrad.FoodOrderingApp.service.entity.CustomerAuthEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;

@Repository
public class CustomerRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * @param accessToken
     * @return
     * @throws AuthorizationFailedException
     */
    public CustomerEntity getCustomer(String accessToken) throws AuthorizationFailedException {
        CustomerAuthEntity customerAuthEntity = null;
        CustomerEntity customerEntity = null;
        try {
            TypedQuery<CustomerAuthEntity> query1 = entityManager.createNamedQuery("getCustomerAuthInfo", CustomerAuthEntity.class);
            query1.setParameter("accessToken", accessToken);
            customerAuthEntity = query1.getSingleResult();
            //user validations
            if (customerAuthEntity == null) {
                throw new AuthorizationFailedException("ATHR-001", "Customer is not Logged in");

            } else if (customerAuthEntity.getLogoutTime().isAfter(customerAuthEntity.getLoginTime())) {
                throw new AuthorizationFailedException("ATHR-002", "Customer is logged out. Log in again to access this endpoint");
            } else if (customerAuthEntity.getExpiryTime().isBefore(LocalDateTime.now())) {
                throw new AuthorizationFailedException("ATHR-003", "Your session is expired. Log in again to access this endpoint");
            }

            if (customerAuthEntity != null) {
                TypedQuery<CustomerEntity> query2 = entityManager.createNamedQuery("getCustomerByUuid", CustomerEntity.class);
                query2.setParameter("customerUuid", customerAuthEntity.getCustomerId().getUuid());

                customerEntity = query2.getSingleResult();
            }

        } catch (NoResultException e) {


        }

        return customerEntity;
    }
}
