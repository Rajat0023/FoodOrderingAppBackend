package com.upgrad.FoodOrderingApp.service.business;

import com.upgrad.FoodOrderingApp.service.dao.AddressRepository;
import com.upgrad.FoodOrderingApp.service.entity.AddressEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAddressEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.exception.AddressNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Business class for functionalities related to address entity
 */
@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    /**
     *
     * @param id
     * @param customerEntity
     * @return
     * @throws AddressNotFoundException
     * @throws AuthorizationFailedException
     */
    public AddressEntity getAddressByUUID(String id, CustomerEntity customerEntity) throws AddressNotFoundException, AuthorizationFailedException {

        CustomerAddressEntity customerAddressEntity ;
        AddressEntity addressEntity = addressRepository.getAddress(id);


        if (addressEntity == null) {
            throw new AddressNotFoundException("ANF-003", "No address by this id");
        } else {
            customerAddressEntity = addressRepository.getCustomer(customerEntity.getId(),addressEntity.getId());
        }


        if (customerAddressEntity == null) {
            throw new AuthorizationFailedException("ATHR-004", "You are not authorized to view/update/delete any one else's address");
        }
        return addressEntity;
    }
}
