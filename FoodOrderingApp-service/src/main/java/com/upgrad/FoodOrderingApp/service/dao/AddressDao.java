package com.upgrad.FoodOrderingApp.service.dao;


import com.upgrad.FoodOrderingApp.service.entity.AddressEntity;
import com.upgrad.FoodOrderingApp.service.entity.StateEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Repository
public class AddressDao {
    @PersistenceContext
    private EntityManager entityManager;

    public StateEntity getStateByUuid(String stateUuid) {
        try {
            return entityManager.createNamedQuery("findStateByUUID", StateEntity.class)
                    .setParameter("uuid",stateUuid)
                    .getSingleResult();
        }
        catch (NoResultException nre) {
            return null;
        }
    }

    public AddressEntity createAddress(AddressEntity address) {
        entityManager.persist(address);
        return address;
    }
}
