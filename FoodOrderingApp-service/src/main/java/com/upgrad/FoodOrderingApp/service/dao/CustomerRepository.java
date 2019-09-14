package com.upgrad.FoodOrderingApp.service.dao;


import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Repository
public class CustomerRepository {

    @PersistenceContext
    private EntityManager entityManager;


    public CustomerEntity getCustomer(String accessToken) {
        CustomerEntity customerEntity = null;

        try {
            TypedQuery<CustomerEntity> query = entityManager.createNamedQuery("query", CustomerEntity.class);
            query.setParameter("accessToken", accessToken);

            customerEntity = query.getSingleResult();

        } catch (NoResultException e) {


        }

        return customerEntity;
    }
}
