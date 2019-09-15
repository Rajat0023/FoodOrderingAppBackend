package com.upgrad.FoodOrderingApp.service.business;

import com.upgrad.FoodOrderingApp.service.dao.AddressRepository;
import com.upgrad.FoodOrderingApp.service.entity.AddressEntity;
import com.upgrad.FoodOrderingApp.service.exception.AddressNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    public AddressEntity getAddressEntityFromUuid(String id) throws AddressNotFoundException {


        AddressEntity addressEntity=addressRepository.getAddress(id);

        if (addressEntity == null) {
throw new AddressNotFoundException("ANF-003","No address by this id");

        }
        return addressEntity;
    }
}
