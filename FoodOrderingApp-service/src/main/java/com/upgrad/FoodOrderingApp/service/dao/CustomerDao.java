package com.upgrad.FoodOrderingApp.service.dao;

//import com.upgrad.FoodOrderingApp.service.entity.Customer;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAuth;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Repository

public class CustomerDao {

    @PersistenceContext
    private EntityManager entityManager;

    public CustomerAuth getAuthToken(String accessToken)
    {
        try{
            return entityManager.
                    createNamedQuery("customerAuthTokenByAccessToken", CustomerAuth.class)
                    .setParameter("accessToken",accessToken)
                    .getSingleResult();
        }
        catch(NoResultException nre){
            return null;
        }
    }
}

