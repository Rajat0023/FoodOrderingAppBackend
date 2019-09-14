package com.upgrad.FoodOrderingApp.service.business;

import com.upgrad.FoodOrderingApp.service.entity.AddressEntity;
import org.springframework.stereotype.Service;

@Service
public class AddressService {


    public AddressEntity getAddressEntityFromUuid(String id) {


        return new AddressEntity();
    }
}
