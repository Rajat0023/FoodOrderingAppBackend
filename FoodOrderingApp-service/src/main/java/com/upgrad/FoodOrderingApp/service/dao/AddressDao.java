package com.upgrad.FoodOrderingApp.service.dao;


import com.upgrad.FoodOrderingApp.service.entity.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

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

    public AddressEntity getAddressByUuid(String addressUuid) {
        try {
            return entityManager.createNamedQuery("findAddressByUUID", AddressEntity.class)
                    .setParameter("uuid",addressUuid)
                    .getSingleResult();
        }
        catch (NoResultException nre) {
            return null;
        }
    }

    public void createCustomerAddress(CustomerAddressEntity customerAddress) {
        entityManager.persist(customerAddress);
    }

    public AddressEntity deleteAddress(AddressEntity addressEntity) {
       entityManager.remove(addressEntity);
        return addressEntity;
    }

    public OrderEntity getOrderByAddressId(AddressEntity addressEntity) {
        try {
            return entityManager.createNamedQuery("findOrderByAddressId", OrderEntity.class)
                    .setParameter("addressId",addressEntity.getId())
                    .getSingleResult();
        }
        catch (NoResultException nre) {
            return null;
        }
    }

    public List<CustomerAddressEntity> getAllAddressByCustomerId(CustomerEntity customerEntity) {
        try {
            return entityManager.createNamedQuery("findAllAddressByCustomerId", CustomerAddressEntity.class)
                    .setParameter("customerId",customerEntity.getId())
                    .getResultList();
        }
        catch (NoResultException nre) {
            return null;
        }
    }


    public  List<StateEntity> findAllStates() {
        try {
            return entityManager.createNamedQuery("findAllStates", StateEntity.class)
                    .getResultList();
        }
        catch (NoResultException nre) {
            return null;
        }
    }

}
