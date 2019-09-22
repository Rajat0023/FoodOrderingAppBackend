package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.api.model.*;
import com.upgrad.FoodOrderingApp.service.businness.AddressService;
import com.upgrad.FoodOrderingApp.service.businness.CustomerService;
import com.upgrad.FoodOrderingApp.service.entity.AddressEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAddress;
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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
      @RequestHeader("authorization") final String authorization)
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
    address.setFlatBuilNo(saveAddressRequest.getFlatBuildingName());
    address.setLocality(saveAddressRequest.getLocality());
    address.setCity(saveAddressRequest.getCity());
    address.setPinCode(saveAddressRequest.getPincode());
    address.setState(state);

    AddressEntity addressEntity = addressService.saveAddress(address, customer);
    SaveAddressResponse saveAddressResponse =
        new SaveAddressResponse()
            .id(addressEntity.getUuid())
            .status("ADDRESS SUCCESSFULLY REGISTERED");
    return new ResponseEntity<SaveAddressResponse>(saveAddressResponse, HttpStatus.CREATED);
  }

  @GetMapping(value = "/address/customer", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<AddressListResponse> getCustomerAddress(
      @RequestHeader("authorization") final String authorization)
      throws AuthorizationFailedException {
    String[] authorizationHeader = authorization.split("Bearer ");
    String accessToken = "";
    for (String i : authorizationHeader) {
      accessToken = i;
    }
    CustomerEntity customer = customerService.getCustomer(accessToken);
      List<AddressEntity> allAddress = addressService.getAllAddress(customer);
      List<AddressList> lists = new ArrayList<>();
    for (AddressEntity addressEntity : allAddress) {
      AddressList addressList = new AddressList();
      addressList.setId(UUID.fromString(addressEntity.getUuid()));
      addressList.setFlatBuildingName(addressEntity.getFlatBuilNo());
      addressList.setLocality(addressEntity.getLocality());
      addressList.setCity(addressEntity.getCity());
      addressList.setPincode(addressEntity.getPinCode());
      addressList.setState(
          new AddressListState()
              .id(UUID.fromString(addressEntity.getState().getUuid()))
              .stateName(addressEntity.getState().getStateName()));
      lists.add(addressList);
          }
      AddressListResponse addressListResponse = new AddressListResponse();
      addressListResponse.setAddresses(lists);
      return new ResponseEntity<AddressListResponse>(addressListResponse,HttpStatus.OK);
  }

  @DeleteMapping(
      value = "/address/{address_id}",
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<DeleteAddressResponse> deleteCustomerAddress(
      @RequestHeader("authorization") final String authorization,
      @PathVariable("address_id") final String addressUuid)
      throws AuthorizationFailedException, AddressNotFoundException {
    String[] authorizationHeader = authorization.split("Bearer ");
    String accessToken = "";
    for (String i : authorizationHeader) {
      accessToken = i;
    }
    CustomerEntity customer = customerService.getCustomer(accessToken);
    AddressEntity addressEntity = addressService.getAddressByUUID(addressUuid, customer);
    AddressEntity deletedAddressEntity = addressService.deleteAddress(addressEntity);

    DeleteAddressResponse deleteAddressResponse =
        new DeleteAddressResponse()
            .id(UUID.fromString(deletedAddressEntity.getUuid()))
            .status("ADDRESS DELETED SUCCESSFULLY");
    return new ResponseEntity<DeleteAddressResponse>(deleteAddressResponse, HttpStatus.OK);
    }
      }
