package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.CustomerAuthEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Repository
public class CustomerAuthRepository {

    @PersistenceContext
    private EntityManager entityManager;


    public CustomerAuthEntity getCustomerAuthenticationDetails(String accessToken) {

        CustomerAuthEntity customerAuthEntity = null;
        try {
            TypedQuery<CustomerAuthEntity> query = entityManager.createNamedQuery("getCustomerAuthInfo", CustomerAuthEntity.class);
            query.setParameter("accessToken", accessToken);
            customerAuthEntity = query.getSingleResult();
        } catch (NoResultException e) {

        }
        return customerAuthEntity;
    }

}
