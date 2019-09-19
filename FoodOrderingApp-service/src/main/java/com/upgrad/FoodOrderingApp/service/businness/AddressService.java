package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.AddressDao;
import com.upgrad.FoodOrderingApp.service.entity.*;
import com.upgrad.FoodOrderingApp.service.exception.AddressNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.SaveAddressException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.regex.Pattern;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class AddressService {

  @Autowired private AddressDao addressDao;

  public StateEntity getStateByUUID(String stateUuid) throws AddressNotFoundException {
    StateEntity stateEntity = addressDao.getStateByUuid(stateUuid);
    if (stateEntity == null) {
      throw new AddressNotFoundException("ANF-002", "No state by this id");
    }
    return stateEntity;
  }

  public AddressEntity saveAddress(AddressEntity address, CustomerEntity customer)
      throws SaveAddressException {
    if (address.getFlatNumber().equals("")
        || address.getLocality().equals("")
        || address.getCity().equals("")
        || address.getPinCode().equals("")
        || address.getState().getUuid().equals("")) {
      throw new SaveAddressException("SAR-001", "No field can be empty");
    }

    if (!address.getPinCode().equals("")) {
      String pinCode = address.getPinCode();
      String pinCodeRegex = "^[0-9]{6}$";
      Pattern pattern = Pattern.compile(pinCodeRegex);
      if (!pattern.matcher(pinCode).matches()) {
        throw new SaveAddressException("SAR-002", "Invalid pincode");
      }
    }
    address.setUuid(UUID.randomUUID().toString());
    address.setActive(1);
    AddressEntity createdAddress = addressDao.createAddress(address);
    CustomerAddress customerAddress = new CustomerAddress();
    customerAddress.setCustomer(customer);
    customerAddress.setAddress(address);
    addressDao.createCustomerAddress(customerAddress);
    return createdAddress;
  }

  public AddressEntity getAddressByUUID(String addressUuid, CustomerEntity customerEntity)
      throws AddressNotFoundException, AuthorizationFailedException {
    AddressEntity addressEntity = addressDao.getAddressByUuid(addressUuid);
    if (addressEntity == null) {
      throw new AddressNotFoundException("ANF-003", "No address by this id");
    }
    if (!addressEntity.getCustomerAddress().getCustomer().getId().equals(customerEntity.getId())) {
      throw new AuthorizationFailedException(
          "ATHR-004", "You are not authorized to view/update/delete any one else's address");
    }
    return addressEntity;
  }

  public AddressEntity deleteAddress(AddressEntity addressEntity) {
    Orders orders = addressDao.getOrderByAddressId(addressEntity);
    if (orders != null) {
      addressEntity.setActive(0);
      return addressEntity;
    } else {
      return addressDao.deleteAddress(addressEntity);
    }
  }
}
