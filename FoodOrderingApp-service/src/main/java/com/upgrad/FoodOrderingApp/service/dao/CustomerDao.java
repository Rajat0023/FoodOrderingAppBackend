package com.upgrad.FoodOrderingApp.service.dao;


import com.upgrad.FoodOrderingApp.service.entity.Customer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;


@Repository
public class CustomerDao {
    @PersistenceContext
    private EntityManager entityManager;

    public Customer getContactNumber(String contactNumber){

        try {
            return entityManager.createNamedQuery("findContactNumber", Customer.class)
                    .setParameter("contactNumber",contactNumber)
                    .getSingleResult();
        }
        catch (NoResultException nre) {
            return null;
        }

    }
}
