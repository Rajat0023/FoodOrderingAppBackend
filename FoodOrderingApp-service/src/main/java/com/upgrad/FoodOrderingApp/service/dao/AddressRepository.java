package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.AddressEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Repository
public class AddressRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public AddressEntity getAddress(String uuid) {
        AddressEntity addressEntity = null;
        try {

            TypedQuery<AddressEntity> query = entityManager.createNamedQuery("query", AddressEntity.class);
            query.setParameter("uuid", uuid);
            addressEntity = query.getSingleResult();
        } catch (NoResultException e) {


        }
        return addressEntity;
    }
}
