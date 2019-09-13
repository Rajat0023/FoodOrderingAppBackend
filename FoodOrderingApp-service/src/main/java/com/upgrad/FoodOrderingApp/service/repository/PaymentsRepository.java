package com.upgrad.FoodOrderingApp.service.repository;

import com.upgrad.FoodOrderingApp.service.entity.PaymentEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class PaymentsRepository {

    @PersistenceContext
    EntityManager entityManager;


    public List<PaymentEntity> getPaymentMediums() {

        List<PaymentEntity> paymentMediumList = null;
        try {
            TypedQuery<PaymentEntity> query = entityManager.createNamedQuery("query", PaymentEntity.class);
            paymentMediumList = query.getResultList();
        } catch (NoResultException e) {

        }
        return paymentMediumList;
    }
}
