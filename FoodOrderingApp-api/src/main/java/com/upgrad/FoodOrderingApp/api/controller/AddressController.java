package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.api.model.SaveAddressRequest;
import com.upgrad.FoodOrderingApp.api.model.SaveAddressResponse;
import com.upgrad.FoodOrderingApp.service.businness.AddressService;
import com.upgrad.FoodOrderingApp.service.businness.CustomerService;
import com.upgrad.FoodOrderingApp.service.entity.AddressEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.entity.StateEntity;
import com.upgrad.FoodOrderingApp.service.exception.AddressNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.SaveAddressException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(value = "*", maxAge = 1L)
@RequestMapping("/")
public class AddressController {

  @Autowired private CustomerService customerService;

  @Autowired private AddressService addressService;

  @PostMapping(
      value = "/address",
      consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<SaveAddressResponse> saveCustomerAddress(
      @RequestBody(required = false) final SaveAddressRequest saveAddressRequest,
      @RequestHeader("authorization") String authorization)
      throws AuthorizationFailedException, AddressNotFoundException, SaveAddressException {
    String[] authorizationHeader = authorization.split("Bearer ");
    String accessToken = "";
    for (String i : authorizationHeader) {
      accessToken = i;
    }
    CustomerEntity customer = customerService.getCustomer(accessToken);
    StateEntity state = addressService.getStateByUUID(saveAddressRequest.getStateUuid());
    AddressEntity address = new AddressEntity();
    address.setCity(saveAddressRequest.getCity());
    address.setFlatNumber(saveAddressRequest.getFlatBuildingName());
    address.setLocality(saveAddressRequest.getLocality());
    address.setCity(saveAddressRequest.getCity());
    address.setPinCode(saveAddressRequest.getPincode());
    address.setState(state);

    AddressEntity addressEntity = addressService.saveAddress(address,customer);
    SaveAddressResponse saveAddressResponse =
        new SaveAddressResponse()
            .id(addressEntity.getUuid())
            .status("ADDRESS SUCCESSFULLY REGISTERED");
    return new ResponseEntity<SaveAddressResponse>(saveAddressResponse, HttpStatus.CREATED);
  }
        }
