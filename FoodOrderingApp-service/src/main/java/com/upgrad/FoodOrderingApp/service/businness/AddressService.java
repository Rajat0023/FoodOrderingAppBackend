package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.AddressDao;
import com.upgrad.FoodOrderingApp.service.entity.AddressEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.entity.StateEntity;
import com.upgrad.FoodOrderingApp.service.exception.AddressNotFoundException;
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

    @Autowired
    private CustomerService customerService;

    @Autowired
    private AddressDao addressDao;

  public StateEntity getStateByUUID(String stateUuid) throws AddressNotFoundException {
    StateEntity stateEntity = addressDao.getStateByUuid(stateUuid);
    if (stateEntity == null) {
      throw new AddressNotFoundException("ANF-002", "No state by this id");
    }
    return stateEntity;
        }

  public AddressEntity saveAddress(AddressEntity address, CustomerEntity customer) throws SaveAddressException {
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
    return addressDao.createAddress(address);
        }
}
