package com.upgrad.FoodOrderingApp.service.business;

import com.upgrad.FoodOrderingApp.service.entity.PaymentEntity;
import com.upgrad.FoodOrderingApp.service.dao.PaymentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PaymentService {

    @Autowired
    private PaymentsRepository paymentsRepository;


    public List<PaymentEntity> getAllPaymentMethods() {

        return paymentsRepository.getPaymentMediums();
    }


    public PaymentEntity getPaymentEntityFromUuid(String id) {


        return new PaymentEntity();
    }
}
