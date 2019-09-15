package com.upgrad.FoodOrderingApp.api.controller;


import com.upgrad.FoodOrderingApp.api.model.*;
import com.upgrad.FoodOrderingApp.service.businness.CustomerService;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAuthEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthenticationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.SignUpRestrictedException;
import com.upgrad.FoodOrderingApp.service.exception.UpdateCustomerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@RestController
@CrossOrigin(value = "*", maxAge = 1L)
@RequestMapping("/")
public class CustomerController {

  @Autowired private CustomerService customerService;

  @PostMapping(
      value = "/customer/signup",
      consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<SignupCustomerResponse> customerSignUp(
      @RequestBody(required = false) final SignupCustomerRequest signupCustomerRequest)
      throws SignUpRestrictedException {

    CustomerEntity customer = new CustomerEntity();
    customer.setFirstName(signupCustomerRequest.getFirstName());
    customer.setLastName(signupCustomerRequest.getLastName());
    customer.setEmail(signupCustomerRequest.getEmailAddress());
    customer.setContactNumber(signupCustomerRequest.getContactNumber());
    customer.setPassword(signupCustomerRequest.getPassword());

    CustomerEntity createdCustomer = customerService.saveCustomer(customer);

    SignupCustomerResponse signupCustomerResponse =
        new SignupCustomerResponse()
            .id(createdCustomer.getUuid())
            .status("CUSTOMER SUCCESSFULLY REGISTERED");

    return new ResponseEntity<SignupCustomerResponse>(signupCustomerResponse, HttpStatus.CREATED);
  }

  @PostMapping(
      value = "/customer/login",
      consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<LoginResponse> customerLogin(
      @RequestHeader("authorization") String authorizationHeader)
      throws AuthenticationFailedException {

    byte[] decode = Base64.getDecoder().decode(authorizationHeader.split("Basic ")[1]);
    String decodedText = new String(decode);
    String[] credentials = decodedText.split(":");
    String userName = credentials[0];
    String password = credentials[1];
    CustomerAuthEntity customerAuth = customerService.authenticate(userName, password);
    LoginResponse loginResponse = new LoginResponse();
    loginResponse.setId(customerAuth.getCustomer().getUuid());
    loginResponse.setMessage("LOGGED IN SUCCESSFULLY");
    loginResponse.setFirstName(customerAuth.getCustomer().getFirstName());
    loginResponse.setLastName(customerAuth.getCustomer().getLastName());
    loginResponse.setEmailAddress(customerAuth.getCustomer().getEmail());
    loginResponse.setContactNumber(customerAuth.getCustomer().getContactNumber());
    HttpHeaders headers = new HttpHeaders();
    headers.set("access-token", customerAuth.getAccessToken());
    List<String> header = new ArrayList<>();
    header.add("access-token");
    headers.setAccessControlExposeHeaders(header);

    return new ResponseEntity<LoginResponse>(loginResponse, headers, HttpStatus.OK);
  }

  @PostMapping(
      value = "/customer/logout",
      consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<LogoutResponse> customerLogout(
      @RequestHeader("authorization") String authorization) throws AuthorizationFailedException {
      String[] authorizationHeader = authorization.split("Bearer ");
      String accessToken = "";
      for (String i : authorizationHeader) {
          accessToken = i;
      }
    CustomerAuthEntity customerAuth = customerService.logout(accessToken);
    LogoutResponse logoutResponse =
        new LogoutResponse()
            .id(customerAuth.getCustomer().getUuid())
            .message("LOGGED OUT SUCCESSFULLY");
    return new ResponseEntity<LogoutResponse>(logoutResponse, HttpStatus.OK);
  }

  @PutMapping(
      value = "/customer",
      consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<UpdateCustomerResponse> customerUpdate(
      @RequestBody final UpdateCustomerRequest updateCustomerRequest,
      @RequestHeader("authorization") String authorization)
      throws AuthorizationFailedException, UpdateCustomerException {
    CustomerEntity customer = new CustomerEntity();
    customer.setFirstName(updateCustomerRequest.getFirstName());
    customer.setLastName(updateCustomerRequest.getLastName());
    CustomerEntity updatedCustomer = customerService.updateCustomer(authorization, customer);
    UpdateCustomerResponse updateCustomerResponse =
        new UpdateCustomerResponse()
            .id(updatedCustomer.getUuid())
            .status("CUSTOMER DETAILS UPDATED SUCCESSFULLY")
            .firstName(updatedCustomer.getFirstName())
            .lastName(updatedCustomer.getLastName());
    return new ResponseEntity<UpdateCustomerResponse>(updateCustomerResponse, HttpStatus.OK);
  }

  @PutMapping(
      value = "/customer/password",
      consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<UpdatePasswordResponse> customerPasswordUpdate(
      @RequestBody final UpdatePasswordRequest updatePasswordRequest,
      @RequestHeader("authorization") String authorization)
      throws UpdateCustomerException, AuthorizationFailedException {
      String[] authorizationHeader = authorization.split("Bearer ");
      String accessToken = "";
      for (String i : authorizationHeader) {
          accessToken = i;
      }
    String oldPassword = updatePasswordRequest.getOldPassword();
    String newPassword = updatePasswordRequest.getNewPassword();
    if (oldPassword.equals("") || newPassword.equals("")) {
      throw new UpdateCustomerException("UCR-003","No field should be empty");
    }
    CustomerEntity customer = customerService.getCustomer(accessToken);
    CustomerEntity updatedCustomer =
        customerService.updateCustomerPassword(oldPassword, newPassword, customer);
    customerService.updateCustomer(updatedCustomer);
    UpdatePasswordResponse updatePasswordResponse =
        new UpdatePasswordResponse()
            .id(updatedCustomer.getUuid())
            .status("CUSTOMER PASSWORD UPDATED SUCCESSFULLY");
    return new ResponseEntity<UpdatePasswordResponse>(updatePasswordResponse,HttpStatus.OK);
  }

    }
